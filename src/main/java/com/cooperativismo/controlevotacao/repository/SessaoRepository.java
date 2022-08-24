package com.cooperativismo.controlevotacao.repository;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cooperativismo.controlevotacao.entity.Sessao;

@Repository
public interface SessaoRepository extends JpaRepository<Sessao, Long> {
	
	Optional<Sessao> findByIdPautaAndDhInicioAfterAndDhFimBefore(Long idPauta, LocalDateTime data1, LocalDateTime data2);

}
