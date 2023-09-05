//package kuit.subway.auth;
//
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.http.HttpMethod;
//import org.springframework.stereotype.Component;
//import org.springframework.web.servlet.HandlerInterceptor;
//
//// 인증 즉, Auth
//@Component
//public class LoginInterceptor implements HandlerInterceptor {
//    private final JwtTokenProvider jwtTokenProvider;
//
//    public LoginInterceptor(final JwtTokenProvider jwtTokenProvider) {
//        this.jwtTokenProvider = jwtTokenProvider;
//    }
//
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
//        if (isPreflight(request)) {
//            return true;
//        }
//
//        String token = AuthorizationExtractor.extractAccessToken(request);
//        jwtTokenProvider.validateToken(token);
//        return true;
//    }
//
//    private boolean isPreflight(HttpServletRequest request) {
//        return request.getMethod().equals(HttpMethod.OPTIONS.toString());
//    }
//}