package com.codewithpcodes.skillbridge.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Service class for handling JSON Web Token (JWT) generation, validation,
 * and claim extraction using the modern jjwt API (v0.12.x and later).
 */
@Service
public class JwtService {

    @Value("${application.security.jwt.secret-key}")
    private String secretKey;
    @Value("${application.security.jwt.expiration}")
    private long jwtExpiration;
    @Value("${application.security.jwt.refresh-token.expiration}")
    private long refreshExpiration;

    /**
     * Extracts the username (Subject claim) from a given JWT.
     * @param token The JWT string.
     * @return The username.
     */
    public String extractionUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Extracts a specific claim from the JWT using a resolver function.
     * @param token The JWT string.
     * @param claimsResolver Function to resolve the desired claim (e.g., Claims::getExpiration).
     * @return The resolved claim value.
     * @param <T> The type of the claim value.
     */
    public <T> T  extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Generates a standard access token with no extra claims.
     */
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    /**
     * Generates a standard access token with custom extra claims.
     */
    public String generateToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails
    ) {
        return buildToken(extraClaims, userDetails, jwtExpiration);
    }

    /**
     * Generates a refresh token.
     */
    public String generateRefreshToken(
            UserDetails userDetails
    ) {
        return  buildToken(new HashMap<>(), userDetails, refreshExpiration);
    }

    /**
     * The core method to build and sign a JWT string.
     * It uses the non-deprecated .signWith(Key) method.
     */
    private String buildToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails,
            long expiration
    ) {
        return Jwts
                .builder()
                .claims(extraClaims) // Modern method for setting claims
                .subject(userDetails.getUsername()) // Modern method for setting subject
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiration))
                // NON-DEPRECATED: Using the simplified signWith(Key)
                .signWith(getSignInKey())
                .compact();
    }

    /**
     * Validates if the token is valid for the given user (username match and not expired).
     */
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractionUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    /**
     * Checks if the token's expiration date is before the current date.
     */
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Parses the JWT, verifies the signature, and extracts the claims payload.
     * It uses the non-deprecated .verifyWith(Key) method.
     */
    private Claims extractAllClaims(String token) {
        return Jwts
                .parser()
                // NON-DEPRECATED: Using verifyWith(Key) for signature verification
                .verifyWith(getSignInKey())
                .build()
                // parseSignedClaims() replaces parseClaimsJws()
                .parseSignedClaims(token)
                // getPayload() replaces getBody()
                .getPayload();
    }

    /**
     * Decodes the Base64 secret key and creates an HMAC-SHA SecretKey for signing/verification.
     * @return The SecretKey.
     */
    private SecretKey getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}