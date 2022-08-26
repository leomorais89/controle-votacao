package com.cooperativismo.controlevotacao.repository;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cooperativismo.controlevotacao.entity.Sessao;

@Repository
public interface SessaoRepository extends JpaRepository<Sessao, Long> {
	
	@Query(value = "select * "
			+ "from public.tb_sessao ts "
			+ "where ts.id_pauta = :idPauta "
			+ "	and ts.dh_inicio < :agora "
			+ "	and ts.dh_fim > :agora", nativeQuery = true)
	Optional<Sessao> findByPautaIdAndDhInicioAfterAndDhFimBefore(Long idPauta, LocalDateTime agora);

}
