package com.iago.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iago.Dto.UsuarioDto;
import com.iago.Model.Usuario;
import com.iago.Service.UsuarioService;
import com.iago.utils.JwtUtil;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;


@RestController
@CrossOrigin
@RequestMapping("/api")
@AllArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final JwtUtil jwtUtil;

    @PostMapping("/cadastro")
    public ResponseEntity<?> create(@Valid @RequestBody Usuario usuario) {

        try {

            var result = usuarioService.create(usuario);

            if (result == null) {
                return ResponseEntity.status(HttpStatus.OK).body("CPF já cadastrado!");
            }

            UsuarioDto usuarioDto = new UsuarioDto(usuario, jwtUtil.generateToken(usuario));

            return ResponseEntity.status(HttpStatus.OK).body(usuarioDto);

        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro no processo de requisição");

        }
        
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Usuario usuario) {

        try {

            Usuario usuarioLogin = usuarioService.login(usuario);

            if(usuarioLogin == null){
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("CPF/Senha inválido!");
            }

            UsuarioDto usuarioDto = new UsuarioDto(usuario, jwtUtil.generateToken(usuario));
            
            return ResponseEntity.status(HttpStatus.OK).body(usuarioDto);

        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro no processo de requisição");
            
        }
        
    }
}
