package com.cooperativismo.controlevotacao.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cooperativismo.controlevotacao.entity.Pauta;
import com.cooperativismo.controlevotacao.enuns.PautaStatusEnum;

@Repository
public interface PautaRepository extends JpaRepository<Pauta, Long> {
	
	@Query(value = "select * "
			+ "from public.tb_pauta tp "
			+ "inner join public.tb_sessao ts on ts.id_pauta = tp.id_pauta "
			+ "where ts.dh_inicio < :agora "
			+ "	and ts.dh_fim > :agora", nativeQuery = true)
	List<Pauta> findBySessaoListDhInicioAfterAndSessaoListDhFimBefore(LocalDateTime agora);
	
	boolean existsByIdAndStatus(Long id, PautaStatusEnum status);

}
