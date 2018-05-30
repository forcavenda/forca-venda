package br.com.logistica.forcavenda.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.logistica.forcavenda.service.impl.UsuarioServiceImpl;

public class FiltroAutenticacao extends OncePerRequestFilter {

  private static final Logger logger = LoggerFactory.getLogger(FiltroAutenticacao.class);
  public static final String HEADER_PREFIX = "Bearer ";

  @Autowired
  private ProvedorToken tokenProvider;
  @Autowired
  private UsuarioServiceImpl usuarioService;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {
    try {
      String jwt = getJwtFromRequest(request);

      if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
        String userId = tokenProvider.getUserIdFromJWT(jwt);

        UserDetails userDetails = usuarioService.loadUserById(userId);
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
          userDetails, null, userDetails.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(authentication);
      }
    } catch (Exception ex) {
      SecurityContextHolder.clearContext();
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      logger.error("Não foi possível definir a autenticação do usuário no contexto de segurança.",
        ex);
    }

    filterChain.doFilter(request, response);
  }

  private String getJwtFromRequest(HttpServletRequest request) {
    String bearerToken = request.getHeader(WebSecurityConfiguration.JWT_TOKEN_HEADER_PARAM);

    if (bearerToken.length() < HEADER_PREFIX.length()) {
      throw new AuthenticationServiceException("Tamanho do cabeçalho de autorização inválido.");
    }

    if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(HEADER_PREFIX)) {
      return bearerToken.substring(HEADER_PREFIX.length(), bearerToken.length());
    }

    return null;
  }
}
