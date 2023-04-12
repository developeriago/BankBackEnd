package com.iago.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.iago.Model.Conta;
import com.iago.Model.Transacao;
import com.iago.Repository.ContaRepository;
import com.iago.Repository.TransacaoRepository;

@Component
public class ContaService {

    @Autowired
	ContaRepository contaRepository;

	@Autowired
	TransacaoRepository transacaoRepository;

   public List<Transacao> extrato(String cpf){
		return transacaoRepository.findByCpf(cpf);
   }

	public Double saldo(String cpf) {

		Conta conta = contaRepository.findByCpf(cpf);

		if( conta != null){
			return conta.getSaldo();
		}
		
		return null;
		
	}

	public Double deposito(Transacao transacao) {

		Conta conta = contaRepository.findByCpf(transacao.getCpf());

		if( conta != null){
			
			conta.setSaldo(conta.getSaldo() + transacao.getValor());
			contaRepository.save(conta);

			Transacao transacaoSave = new Transacao();
			transacaoSave.setCpf(transacao.getCpf());
			transacaoSave.setValor(transacao.getValor());
			transacaoSave.setOperacao(transacao.getOperacao());
			transacaoRepository.save(transacaoSave);

			return conta.getSaldo();
		}
		
		return null;
		
	}

	public Double saque(Transacao transacao) {

		Conta conta = contaRepository.findByCpf(transacao.getCpf());

		if( conta != null){
			
			if(transacao.getValor() > conta.getSaldo()){
				return null;
			}
			
			conta.setSaldo(conta.getSaldo() - transacao.getValor());
			contaRepository.save(conta);

			Transacao transacaoSave = new Transacao();
			transacaoSave.setCpf(transacao.getCpf());
			transacaoSave.setValor(transacao.getValor());
			transacaoSave.setOperacao(transacao.getOperacao());
			transacaoRepository.save(transacaoSave);

			return conta.getSaldo();
		}
		
		return null;
		
	}
    
}
