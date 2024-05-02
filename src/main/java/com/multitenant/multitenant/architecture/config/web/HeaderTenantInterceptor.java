package com.multitenant.multitenant.architecture.config.web;

import com.multitenant.multitenant.architecture.config.web.ThreadTenantStorage;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.WebRequestInterceptor;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

@Component
public class HeaderTenantInterceptor implements WebRequestInterceptor {

    public static final String JWT_HEADER = "Authorization";

    @Value("${security.jwt.token.secret-key}")
    private String jwtSecret;
    public static final String TENANT_HEADER = "X-tenant";

    @Override
    public void preHandle(WebRequest request) throws Exception {
        if(request.getHeader(TENANT_HEADER) != null){
            ThreadTenantStorage.setTenantId(request.getHeader(TENANT_HEADER));
        }else{
            String jwtToken = request.getHeader(JWT_HEADER);
            if (jwtToken != null && jwtToken.startsWith("Bearer ")) {
                jwtToken = jwtToken.substring(7);
            }

            try {
                Claims claims = Jwts.parser()
                        .setSigningKey(jwtSecret.getBytes(StandardCharsets.UTF_8))
                        .parseClaimsJws(jwtToken)
                        .getBody();
                String tenantId = (String) claims.get("tenantId");
                ThreadTenantStorage.setTenantId(tenantId);

            } catch (ExpiredJwtException e) {
                // Handle token expiration
            } catch (Exception e) {
                // Handle other JWT parsing errors
            }
        }

    }

    @Override
    public void postHandle(WebRequest request, ModelMap model) throws Exception {
        ThreadTenantStorage.clear();
    }

    @Override
    public void afterCompletion(WebRequest request, Exception ex) throws Exception {

    }


}
