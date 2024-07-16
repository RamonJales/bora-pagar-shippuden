package com.borathings.borapagar.auth;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.stereotype.Component;

/** CustomOAuth2AuthorizationRequestResolver */
@Component
public class CustomOAuth2AuthorizationRequestResolver
        implements OAuth2AuthorizationRequestResolver {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final OAuth2AuthorizationRequestResolver defaultResolver;

    public CustomOAuth2AuthorizationRequestResolver(
            ClientRegistrationRepository clientRegistrationRepository) {
        this.defaultResolver =
                new DefaultOAuth2AuthorizationRequestResolver(
                        clientRegistrationRepository, "/oauth2/authorization");
    }

    @Override
    public OAuth2AuthorizationRequest resolve(HttpServletRequest originalRequest) {
        OAuth2AuthorizationRequest resolvedRequest = defaultResolver.resolve(originalRequest);
        return requestResolvedWithReferer(resolvedRequest, originalRequest);
    }

    @Override
    public OAuth2AuthorizationRequest resolve(
            HttpServletRequest originalRequest, String clientRegistrationId) {
        OAuth2AuthorizationRequest resolvedRequest =
                defaultResolver.resolve(originalRequest, clientRegistrationId);
        return requestResolvedWithReferer(resolvedRequest, originalRequest);
    }

    private OAuth2AuthorizationRequest requestResolvedWithReferer(
            OAuth2AuthorizationRequest resolvedRequest, HttpServletRequest originalRequest) {
        if (resolvedRequest == null) {
            return null;
        }
        String referer = originalRequest.getHeader("Referer");
        originalRequest.getSession().setAttribute("original_referer", referer);

        logger.info("Autenticação vinda do cliente {} foi resolvida", referer);

        Map<String, Object> additionalParaments =
                Collections.singletonMap("original_referer", referer);
        return OAuth2AuthorizationRequest.from(resolvedRequest)
                .additionalParameters(additionalParaments)
                .build();
    }
}
