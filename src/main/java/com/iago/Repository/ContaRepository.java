package com.iago.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.iago.Model.Conta;

@Repository
public interface ContaRepository extends JpaRepository<Conta, Long> {
    
    public Conta findByCpf(String cpf);

    
}


