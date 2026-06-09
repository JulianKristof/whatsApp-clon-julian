package com.postgres.demopg.security.jwt;

import com.postgres.demopg.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Component
public class JwtUtils {

    @Value("${bezkoder.app.jwtSecret}")
    private String jwtSecret;

    @Value("${bezkoder.app.jwtExpirationMs}")
    private int jwtExpirationMs;

    public String generateJwtToken(Authentication authentication) {
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

        long now = System.currentTimeMillis();
        long exp = now + jwtExpirationMs;

        String header = "{\"alg\":\"HS256\",\"typ\":\"JWT\"}";
        String payload = "{\"sub\":\"" + userPrincipal.getUsername() + "\",\"exp\":" + exp + "}";

        String encodedHeader = base64UrlEncode(header);
        String encodedPayload = base64UrlEncode(payload);

        String unsignedToken = encodedHeader + "." + encodedPayload;
        String signature = sign(unsignedToken);

        return unsignedToken + "." + signature;
    }

    public String getUserNameFromJwtToken(String token) {
        try {
            String[] parts = token.split("\\.");

            if (parts.length != 3) {
                return null;
            }

            String payloadJson = new String(
                    Base64.getUrlDecoder().decode(parts[1]),
                    StandardCharsets.UTF_8
            );

            String marker = "\"sub\":\"";
            int start = payloadJson.indexOf(marker);

            if (start == -1) {
                return null;
            }

            start += marker.length();
            int end = payloadJson.indexOf("\"", start);

            if (end == -1) {
                return null;
            }

            return payloadJson.substring(start, end);
        } catch (Exception e) {
            return null;
        }
    }

    public boolean validateJwtToken(String authToken) {
        try {
            String[] parts = authToken.split("\\.");

            if (parts.length != 3) {
                return false;
            }

            String unsignedToken = parts[0] + "." + parts[1];
            String expectedSignature = sign(unsignedToken);

            if (!expectedSignature.equals(parts[2])) {
                return false;
            }

            String payloadJson = new String(
                    Base64.getUrlDecoder().decode(parts[1]),
                    StandardCharsets.UTF_8
            );

            String marker = "\"exp\":";
            int start = payloadJson.indexOf(marker);

            if (start == -1) {
                return false;
            }

            start += marker.length();

            int end = payloadJson.indexOf("}", start);

            if (end == -1) {
                return false;
            }

            long exp = Long.parseLong(payloadJson.substring(start, end).trim());

            return System.currentTimeMillis() <= exp;
        } catch (Exception e) {
            return false;
        }
    }

    private String base64UrlEncode(String value) {
        return Base64.getUrlEncoder()
                .withoutPadding()
                .encodeToString(value.getBytes(StandardCharsets.UTF_8));
    }

    private String sign(String data) {
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKeySpec = new SecretKeySpec(
                    jwtSecret.getBytes(StandardCharsets.UTF_8),
                    "HmacSHA256"
            );

            mac.init(secretKeySpec);

            byte[] signatureBytes = mac.doFinal(data.getBytes(StandardCharsets.UTF_8));

            return Base64.getUrlEncoder()
                    .withoutPadding()
                    .encodeToString(signatureBytes);
        } catch (Exception e) {
            throw new RuntimeException("Error al firmar token", e);
        }
    }
}