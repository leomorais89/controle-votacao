package com.cooperativismo.controlevotacao.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cooperativismo.controlevotacao.entity.Associado;

@Repository
public interface AssociadoRepository extends JpaRepository<Associado, Long> {
	
	Optional<Associado> findByCpf(String cpf);

}
