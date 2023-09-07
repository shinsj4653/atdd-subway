package kuit.subway.auth;

import jakarta.servlet.http.HttpServletRequest;

import kuit.subway.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
@RequiredArgsConstructor
public class AuthenticationPrincipalArgumentResolver implements HandlerMethodArgumentResolver {

    private final AuthService authService;
    private final JwtTokenProvider jwtTokenProvider;

    // LoginUserId 어노테이션이 붙은 파라미터를 가져온다.
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(LoginUserId.class);
    }

    // request 로부터 AccessToken 을 가져와 token에서 memberId를 추출해 파라미터에 주입
    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        // Hint : Authorization Extractor, JwtTokenProvider 이용
        String token = AuthorizationExtractor.extractAccessToken(request);
        Long memberId = Long.parseLong(jwtTokenProvider.getPayload(token));
        return memberId;
    }
}
