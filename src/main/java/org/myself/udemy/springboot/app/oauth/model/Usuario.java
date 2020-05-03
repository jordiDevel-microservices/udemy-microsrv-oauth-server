package org.myself.udemy.springboot.app.oauth.model;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Usuario {

	private Long id;
	private String login;
	private String password;
	private Boolean enabled;
	private String nombre;
	private String apellido;
	private String email;
	private Integer loginAttempts;
	private List<Role> roles;
	
}
