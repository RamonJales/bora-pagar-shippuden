package com.borathings.borapagar.config;

import com.borathings.borapagar.subject_course.interceptors.ValidateCourseIdInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerTypePredicate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/** WebConfig Classe responsável por configurar coisas relacionadas ao Spring MVC */
@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {
    @Override
    /**
     * Adiciona o prefixo "/api" à todos os controllers que utilizam da annotation @RestController
     */
    public void configurePathMatch(PathMatchConfigurer configurer) {
        configurer.addPathPrefix("api", HandlerTypePredicate.forAnnotation(RestController.class));
    }

    @Autowired ValidateCourseIdInterceptor validateCourseIdInterceptor;

    /**
     * Registra o middleware <code></code>ValidateCourseIdInterceptor</code> para interceptar todas
     * as rotas <code> /api/course/{courseId}/subject/** </code>
     *
     * @return
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(validateCourseIdInterceptor)
                .addPathPatterns("/api/course/{courseId}/subject/**");
    }
}
