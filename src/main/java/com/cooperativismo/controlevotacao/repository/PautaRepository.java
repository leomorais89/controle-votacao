package com.cooperativismo.controlevotacao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cooperativismo.controlevotacao.entity.Pauta;

@Repository
public interface PautaRepository extends JpaRepository<Pauta, Long> {

}
