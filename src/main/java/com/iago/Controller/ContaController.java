package com.iago.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.iago.Model.Transacao;
import com.iago.Service.ContaService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class ContaController {
    
    private final ContaService contaService;

	@GetMapping("/extrato/{cpf}")
	public @ResponseBody List<Transacao> extrato(@PathVariable String cpf){
		
		return contaService.extrato(cpf);
	}
    
    @GetMapping("/saldo/{cpf}")
	public ResponseEntity<?> verSaldo(@PathVariable String cpf) {
		
		try {
			
			var saldo = contaService.saldo(cpf);

			return ResponseEntity.status(HttpStatus.OK).body(saldo);

		} catch (Exception e) {

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro no processo de requisição");

		}
        
	}

    @PostMapping("/depositar")
	public ResponseEntity<?> depositar(@RequestBody Transacao transacao) {
		
		try {

			var saldo = contaService.deposito(transacao);

			return ResponseEntity.status(HttpStatus.OK).body(saldo);

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro no processo de requisição");
		}
        		
	}

    @PostMapping("/saque")
	public ResponseEntity<?> sacar(@RequestBody Transacao transacao){
		
		try {

			var saldo = contaService.saque(transacao);

			if(saldo == null){
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Valor do saque maior que o saldo!");
			}

			return ResponseEntity.status(HttpStatus.OK).body(saldo);

		} catch (Exception e) {

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro no processo de requisição");

		}
        
		
	}
}    
