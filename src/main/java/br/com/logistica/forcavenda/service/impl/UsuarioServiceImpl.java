package br.com.logistica.forcavenda.service.impl;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import br.com.logistica.forcavenda.exception.AppException;
import br.com.logistica.forcavenda.models.Papel;
import br.com.logistica.forcavenda.models.Usuario;
import br.com.logistica.forcavenda.payload.LoginRequest;
import br.com.logistica.forcavenda.repositories.PapelRepository;
import br.com.logistica.forcavenda.repositories.UsuarioRepository;
import br.com.logistica.forcavenda.security.ProvedorToken;
import br.com.logistica.forcavenda.security.CodificarSenhaSHA256;
import br.com.logistica.forcavenda.security.UsuarioPrincipal;
import br.com.logistica.forcavenda.service.UsuarioService;

@Transactional(readOnly = true)
@Service(value = "usuarioService")
public class UsuarioServiceImpl extends IServiceImpl<Usuario, String>
    implements UserDetailsService, UsuarioService {

  private UsuarioRepository usuarioRepository;
  private PapelRepository papelRepository;
  private CodificarSenhaSHA256 passwordEncoder;
  private AuthenticationManager authenticationManager;
  private ProvedorToken tokenProvider;

  @Autowired
  public UsuarioServiceImpl(UsuarioRepository usuarioRepository, PapelRepository papelRepository,
      AuthenticationManager authenticationManager, ProvedorToken tokenProvider,
      CodificarSenhaSHA256 passwordEncoder) {
    super();
    this.usuarioRepository = usuarioRepository;
    this.papelRepository = papelRepository;
    this.authenticationManager = authenticationManager;
    this.tokenProvider = tokenProvider;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  @Transactional
  @SuppressWarnings("unchecked")
  public Usuario insert(Usuario usuario) {
    usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
    Papel papel = papelRepository.findByNome("ROLE_USER").orElseThrow(
      () -> new AppException("Papel do usuário não foi localizado."));
    usuario.setPapeis(Collections.singleton(papel));
    return getRepository().insert(usuario);
  }

  @Override
  public Usuario getByNomeUsuario(String nomeUsuario) {
    return usuarioRepository.getByNomeUsuario(nomeUsuario).orElseThrow(
      () -> new UsernameNotFoundException(String.format("Usuário %s não existe!", nomeUsuario)));
  }

  @Transactional
  public UserDetails loadUserById(String id) {
    Usuario usuario = usuarioRepository.findById(id).orElseThrow(
      () -> new UsernameNotFoundException(String.format("Usuário %s não existe!", id)));

    return new UsuarioPrincipal(usuario);
  }

  @Override
  @Transactional
  public UserDetails loadUserByUsername(String nomeUsuario) throws UsernameNotFoundException {
    if (ObjectUtils.isEmpty(nomeUsuario)) {
      throw new IllegalArgumentException("Nome de usuário não informado.");
    } else {
      Usuario usuario = usuarioRepository.getByNomeUsuario(nomeUsuario).orElseThrow(
        () -> new UsernameNotFoundException(String.format("Usuário %s não existe!", nomeUsuario)));

      return new UsuarioPrincipal(usuario);
    }
  }

  @Override
  public String getTokenProvider(LoginRequest usuario) {
    Authentication authentication = authenticationManager.authenticate(
      new UsernamePasswordAuthenticationToken(
        usuario.getNomeUsuario(),
        usuario.getSenha()));

    SecurityContextHolder.getContext().setAuthentication(authentication);
    return tokenProvider.generateToken(authentication);
  }

  @Override
  public boolean existsByNomeUsuario(String nomeUsuario) {
    return usuarioRepository.existsByNomeUsuario(nomeUsuario);
  }

  @Override
  public boolean existsByEmail(String email) {
    return usuarioRepository.existsByEmail(email);
  }
}
