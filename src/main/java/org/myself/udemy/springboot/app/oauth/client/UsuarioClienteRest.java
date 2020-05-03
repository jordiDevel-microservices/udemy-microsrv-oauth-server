package org.myself.udemy.springboot.app.oauth.client;

import java.util.List;

import org.myself.udemy.springboot.app.oauth.model.Usuario;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "servicio-usuarios")
public interface UsuarioClienteRest {

	@GetMapping("/listar")
	public List<Usuario> listar();
	
	@GetMapping("/ver/{id}")
	public Usuario detalle(@PathVariable Long id);
	
	@GetMapping("/buscar/login/{login}")
	public Usuario buscarPorLogin(@PathVariable String login);
	
	@GetMapping("/buscar/email/{email}")
	public Usuario buscarPorEmail(@PathVariable String email);
	
	@PostMapping("/crear")
	public Usuario crear(@RequestBody Usuario producto);
	
	@PutMapping("/editar/{id}")
	public Usuario editar(@PathVariable Long id, @RequestBody Usuario producto);
	
	@PutMapping("/incLoginAttempts/{id}")
	public Usuario incrementLoginAttempt(@PathVariable Long id);
	
	@PutMapping("/resetLoginAttempts/{id}")
	public Usuario resetLoginAttempts(@PathVariable Long id);
	
	@PutMapping("/disable/{id}")
	public Usuario disableUser(@PathVariable Long id);
	
	@PutMapping("/enable/{id}")
	public Usuario enableUser(@PathVariable Long id);
	
	@DeleteMapping("/eliminar/{id}")
	public void eliminar(@PathVariable Long id);
	
}
