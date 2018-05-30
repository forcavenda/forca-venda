package br.com.logistica.forcavenda.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Order(1)
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, jsr250Enabled = true, prePostEnabled = true)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

  public static final String JWT_TOKEN_HEADER_PARAM = "X-Authorization";
  public static final String JWT_TOKEN_QUERY_PARAM = "token";

  @Autowired
  private UserDetailsService userDetailsService;
  @Autowired
  private PontoAutenticacao unauthorizedHandler;

  @Bean
  public FiltroAutenticacao jwtAuthenticationFilter() {
    return new FiltroAutenticacao();
  }

  @Override
  @Bean(BeanIds.AUTHENTICATION_MANAGER)
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @Override
  @Autowired
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
  }

  @Override
  protected UserDetailsService userDetailsService() {
    return userDetailsService;
  }

  @Bean
  public CodificarSenhaSHA256 passwordEncoder() {
    return new CodificarSenhaSHA256();
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
      .csrf().disable()
      .cors().disable()

      .exceptionHandling().authenticationEntryPoint(unauthorizedHandler)

      .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

      .and().authorizeRequests()
      .antMatchers(AUTH_WHITELIST).permitAll()
      .antMatchers("/api/auth/**").permitAll()
      .antMatchers("/empresa/**").hasRole("ADMIN")
      .anyRequest().authenticated()

      .and().exceptionHandling().accessDeniedPage("/api/negado");


    http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
  }

  @Bean
  public AuditorAware<String> auditorProvider() {
    return () -> {
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

      if (authentication == null ||
          !authentication.isAuthenticated() ||
          authentication instanceof AnonymousAuthenticationToken) {
        return Optional.empty();
      }

      String userId = ((UsuarioPrincipal) SecurityContextHolder.getContext()
        .getAuthentication().getPrincipal()).getId().toString();
      return Optional.ofNullable(userId);
    };
  }

  private static final String[] AUTH_WHITELIST = {
      "/",
      "/favicon.ico",
      "/**/*.png",
      "/**/*.gif",
      "/**/*.svg",
      "/**/*.jpg",
      "/**/*.html",
      "/**/*.css",
      "/**/*.js",
      // -- swagger ui
      "/v2/api-docs",
      "/swagger-resources",
      "/swagger-resources/**",
      "/configuration/ui",
      "/configuration/security",
      "/swagger-ui.html",
      "/webjars/**"
  };

  @Bean
  // @Order(Ordered.HIGHEST_PRECEDENCE)
  public CorsFilter corsFilter() {
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    CorsConfiguration config = new CorsConfiguration();
    config.setAllowCredentials(true);
    config.addAllowedOrigin("*");
    config.addAllowedHeader("*");
    config.addAllowedMethod("GET");
    config.addAllowedMethod("PUT");
    config.addAllowedMethod("POST");
    config.addAllowedMethod("OPTIONS");
    config.addAllowedMethod("DELETE");
    source.registerCorsConfiguration("/api/**", config);
    return new CorsFilter(source);
  }
}
