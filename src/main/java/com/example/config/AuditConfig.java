package com.example.config;

import java.util.Objects;
import java.util.Optional;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.domain.AuditorAware;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Configuration
public class AuditConfig {

  /**
   * Inject user from a custom request header.
   * <p>
   * This approach retrieves the user identifier from the HTTP request,
   * typically passed via a header such as {@code X-Custom-UserId}.
   * Useful in API-to-API communication or when authentication is handled
   * outside of Spring Security.
   */
  @Bean(name = "auditorProvider")
  @Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
  public AuditorAware<String> auditorProviderNew() {
    ServletRequestAttributes atts =
        (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    if (Objects.nonNull(atts)) {
      String userId = atts.getRequest().getHeader("X-Custom-UserId");
      if (StringUtils.isNoneBlank(userId)) {
        return () -> Optional.of(userId);
      }
    }
    return () -> Optional.of("system-user");
  }


}