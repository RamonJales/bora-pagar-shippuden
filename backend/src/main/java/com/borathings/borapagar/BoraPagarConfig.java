package com.borathings.borapagar;

import com.borathings.borapagar.course.subject.interceptors.ValidateCourseIdInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class BoraPagarConfig implements WebMvcConfigurer {

    @Bean
    public ValidateCourseIdInterceptor validateCourseIdInterceptor() {
        return new ValidateCourseIdInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(validateCourseIdInterceptor())
                .addPathPatterns("/course/{courseId}/**");
    }
}
