package com.iago.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.iago.Model.Conta;
import com.iago.Model.Usuario;
import com.iago.Repository.ContaRepository;
import com.iago.Repository.UsuarioRepository;

@Component
public class UsuarioService {

    @Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private ContaRepository contaRepository;

	@Autowired
	private PasswordEncoder encoder;

    
    public Usuario create( Usuario usuario){

		Usuario usuarioValid = usuarioRepository.findByCpf(usuario.getCpf());

		if(usuarioValid != null){
			return null;
		}
		
		usuario.setSenha(encoder.encode(usuario.getSenha()));
        usuarioRepository.save(usuario);

		Conta conta = new Conta();
		conta.setCpf(usuario.getCpf());
		conta.setUsuario(usuario);
		conta.setSaldo(0.00);
		contaRepository.save(conta);


        return usuario;
    }

    public Usuario login(Usuario usuario) {
		Usuario usuarioLogin = usuarioRepository.findByCpf(usuario.getCpf());
		
		if(usuarioLogin != null && encoder.matches(usuario.getSenha(), usuarioLogin.getSenha()) == true) {
			return usuarioLogin;
		}

		return null;
		
	}
}
