package kuit.subway.auth;

import jakarta.servlet.http.HttpServletRequest;
import kuit.subway.exception.badrequest.auth.BlankTokenException;
import kuit.subway.exception.unauthorized.InvalidBearerException;
import lombok.NoArgsConstructor;
import org.apache.http.auth.AUTH;

import java.util.Enumeration;

@NoArgsConstructor
public class AuthorizationExtractor {

    private static final String AUTHENTICATION_TYPE = "Bearer";
    private static final String AUTHORIZATION_HEADER_KEY = "Authorization";

    public static String extractAccessToken(HttpServletRequest request) {
        Enumeration<String> headers = request.getHeaders(AUTHORIZATION_HEADER_KEY);
        while (headers.hasMoreElements()) {
            String value = headers.nextElement();
            if (value.toLowerCase().startsWith(AUTHENTICATION_TYPE.toLowerCase())) {
                String extractToken = value.trim().substring(AUTHENTICATION_TYPE.length());
                validateExtractToken(extractToken);
                return extractToken;
            }
        }
        throw new InvalidBearerException();
    }

    private static void validateExtractToken(String extractToken) {
        if (extractToken.isBlank()) {
            throw new BlankTokenException();
        }
    }
}