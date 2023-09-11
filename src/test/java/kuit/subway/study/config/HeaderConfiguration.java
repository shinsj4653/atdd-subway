package kuit.subway.study.config;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;

public class HeaderConfiguration {
    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            requestTemplate.header("header1", "header value 1");
            requestTemplate.header("header2", "header value 2");
        };
    }
}
