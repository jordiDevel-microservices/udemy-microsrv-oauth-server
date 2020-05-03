package org.myself.udemy.springboot.app.oauth.service;

import org.myself.udemy.springboot.app.oauth.model.Usuario;

public interface UsuarioService {

	public Usuario findByLogin(String login);
	
	public Usuario incLoginAttempts(Long id);
	
	public Usuario resetLoginAttempts(Long id);
	
	public Usuario disableUser(Long id);
	
}
