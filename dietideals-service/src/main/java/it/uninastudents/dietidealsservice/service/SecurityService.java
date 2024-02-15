package it.uninastudents.dietidealsservice.service;

import it.uninastudents.dietidealsservice.config.security.CookieUtils;
import it.uninastudents.dietidealsservice.config.security.SecurityProperties;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class SecurityService {

    private final HttpServletRequest httpServletRequest;

    private final CookieUtils cookieUtils;

    private final SecurityProperties securityProps;

    public boolean isPublic() {
        return securityProps.getAllowedPublicApis().contains(httpServletRequest.getRequestURI());
    }

    public String getBearerToken(HttpServletRequest request) {
        String bearerToken = null;
        String authorization = request.getHeader("Authorization");
        if (StringUtils.hasText(authorization) && authorization.startsWith("Bearer ")) {
            bearerToken = authorization.substring(7);
        }
        return bearerToken;
    }
}
