package org.myself.udemy.springboot.app.oauth.event;

import org.myself.udemy.springboot.app.oauth.model.Usuario;
import org.myself.udemy.springboot.app.oauth.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import brave.Tracer;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class AuthenticationSuccessErrorHandler implements AuthenticationEventPublisher {

	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private Tracer tracer;
	
	@Override
	public void publishAuthenticationSuccess(Authentication authentication) {
		UserDetails user = (UserDetails)authentication.getPrincipal();
		
		log.info("{} successfully logged in", user.getUsername());
		
		Usuario usuario = this.usuarioService.findByLogin(authentication.getName());
		
		this.usuarioService.resetLoginAttempts(usuario.getId());
	}

	@Override
	public void publishAuthenticationFailure(AuthenticationException exception, Authentication authentication) {
		try {
			Usuario usuario = this.usuarioService.findByLogin(authentication.getName());
			
			log.info("User {} fails to log in, attempt {}", usuario.getLogin(), usuario.getLoginAttempts() + 1);
			
			if (usuario.getLoginAttempts() == 2) {
				log.info("User {} disabled because exceeded max. attempts (3)", usuario.getLogin());
				
				this.tracer.currentSpan().tag("user.disabled", "Max. attempts exceeded (3)");
				
				this.usuarioService.disableUser(usuario.getId());
			}
			else {
				this.usuarioService.incLoginAttempts(usuario.getId());
			}
		}
		catch (Exception e) {
			log.info("User {} not exists", authentication.getName());
		}
	}
	
}
