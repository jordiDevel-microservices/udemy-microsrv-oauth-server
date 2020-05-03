package org.myself.udemy.springboot.app.oauth.config;

import java.util.HashMap;
import java.util.Map;

import org.myself.udemy.springboot.app.oauth.model.Usuario;
import org.myself.udemy.springboot.app.oauth.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class AdditionalInfoToken implements TokenEnhancer {

	@Autowired
	private UsuarioService usuarioService;
	
	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		Map<String, Object> info = new HashMap<>();
		
		log.info("Trying to add additional information to token of user {}", authentication.getName());
		
		Usuario user = this.usuarioService.findByLogin(authentication.getName());
		
		if (user != null) {
			log.info("Found user with login {}", authentication.getName());
			
			info.put("nombre", user.getNombre());
			info.put("apellido", user.getApellido());
			info.put("email", user.getEmail());
		}
		
		if (!info.isEmpty()) {
			log.info("Setting additional information to token");
			
			((DefaultOAuth2AccessToken)accessToken).setAdditionalInformation(info);
		}
		
		return accessToken;
	}

}
