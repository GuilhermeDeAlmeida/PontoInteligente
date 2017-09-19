package com.kazale.pontointeligente.api.services.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.kazale.pontointeligente.api.entities.Lancamento;
import com.kazale.pontointeligente.api.repositories.LancamentoRepository;
import com.kazale.pontointeligente.api.services.LancamentoService;

public class LancamentoServiceImpl implements LancamentoService {

	private static final Logger log = LoggerFactory.getLogger(EmpresaServiceImpl.class);
	@Autowired
	private LancamentoRepository lancamentoRepository;
	
	
	@Override
	public Page<Lancamento> buscarPorFuncionarioId(Long funcionarioId, PageRequest pageRequest) {
		log.info("Buscando lançamento pelo funcionarioId {}" , funcionarioId);
		return this.lancamentoRepository.findByFuncionarioId(funcionarioId, pageRequest);
	}

	@Override
	public Optional<Lancamento> buscarPorId(Long id) {
		log.info("Buscando lançamento pelo id {}" , id);
		return Optional.ofNullable(this.lancamentoRepository.findOne(id));
		
	}

	@Override
	public Lancamento persistir(Lancamento lancamento) {
		log.info("Persistindo o lançamento {}" , lancamento);
		return this.lancamentoRepository.save(lancamento);
	}

	@Override
	public void remover(Long id) {
		log.info("Removendo o lançamento pelo id{}" , id);
		this.lancamentoRepository.delete(id);
	}

	
}
