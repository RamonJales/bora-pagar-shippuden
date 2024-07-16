package com.borathings.borapagar.subject_course.interceptors;

import com.borathings.borapagar.course.CourseService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;

/** Middleware para checar se o curso existe */
@Component
public class ValidateCourseIdInterceptor implements HandlerInterceptor {
    @Autowired private CourseService courseService;

    @Override
    public boolean preHandle(
            HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        @SuppressWarnings("unchecked")
        Map<String, String> pathParams =
                (Map<String, String>)
                        request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);

        Long courseId =
                pathParams.get("courseId") == null
                        ? null
                        : Long.parseLong(pathParams.get("courseId"));
        if (courseId == null) {
            throw new IllegalArgumentException("Id do curso é obrigatório");
        }

        // Ensure that course exists
        courseService.findByIdOrError(courseId);
        return true;
    }
}
