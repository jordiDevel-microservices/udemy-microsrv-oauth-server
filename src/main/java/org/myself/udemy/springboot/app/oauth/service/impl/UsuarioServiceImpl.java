package org.myself.udemy.springboot.app.oauth.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.myself.udemy.springboot.app.oauth.client.UsuarioClienteRest;
import org.myself.udemy.springboot.app.oauth.model.Usuario;
import org.myself.udemy.springboot.app.oauth.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import brave.Tracer;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UsuarioServiceImpl implements UserDetailsService, UsuarioService {

	@Autowired
	private UsuarioClienteRest usuarioClienteRest;
	
	@Autowired
	private Tracer tracer;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		try {
			Usuario user = this.usuarioClienteRest.buscarPorLogin(username);
			
			log.info("User {} found", username);
			
			List<GrantedAuthority> authorties = user.getRoles().stream()
				.map(role -> new SimpleGrantedAuthority(role.getNombre()))
				.peek(authority -> log.info("With role: {}" + authority.getAuthority()))
				.collect(Collectors.toList());
			
			return new User(user.getLogin(), user.getPassword(), user.getEnabled(), true, true, true, authorties);
		}
		catch (Exception e) {
			String message = "User " + username + " not found";
			
			log.info(message);
			
			this.tracer.currentSpan().tag("error.msg", message);
			
			throw new UsernameNotFoundException(message);
		}
	}

	@Override
	public Usuario findByLogin(String login) {
		return this.usuarioClienteRest.buscarPorLogin(login);
	}

	@Override
	public Usuario incLoginAttempts(Long id) {
		return this.usuarioClienteRest.incrementLoginAttempt(id);
	}

	@Override
	public Usuario resetLoginAttempts(Long id) {
		return this.usuarioClienteRest.resetLoginAttempts(id);
	}
	
	@Override
	public Usuario disableUser(Long id) {
		return this.usuarioClienteRest.disableUser(id);
	}

}
