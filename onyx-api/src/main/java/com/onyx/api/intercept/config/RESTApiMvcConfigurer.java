package com.onyx.api.intercept.config;

import com.onyx.api.intercept.DynamicDataSourceRoutingInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class RESTApiMvcConfigurer implements WebMvcConfigurer {
    private final DynamicDataSourceRoutingInterceptor dynamicDataSourceRoutingInterceptor;

    /**
     * Add Spring MVC lifecycle interceptors for pre- and post-processing of
     * controller method invocations and resource handler requests.
     * Interceptors can be registered to apply to all requests or be limited
     * to a subset of URL patterns.
     *
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(dynamicDataSourceRoutingInterceptor)
                .excludePathPatterns("/*")
                .addPathPatterns("/", "/upload");
    }
}
