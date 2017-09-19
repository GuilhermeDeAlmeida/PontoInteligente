package com.kazale.pontointeligente.api.services.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kazale.pontointeligente.api.entities.Funcionario;
import com.kazale.pontointeligente.api.repositories.FuncionarioRepository;
import com.kazale.pontointeligente.api.services.FuncionarioService;

@Service
public class FuncionarioServiceImpl implements FuncionarioService{

	private static final Logger log = LoggerFactory.getLogger(EmpresaServiceImpl.class);
	@Autowired
	private FuncionarioRepository funcionarioRepository;
	
	
	@Override
	public Funcionario persistir(Funcionario funcionario) {
		log.info("Persistindo o funcionário {}" , funcionario);
		return this.funcionarioRepository.save(funcionario);
	}

	@Override
	public Optional<Funcionario> buscarPorCpf(String cpf) {
		log.info("Buscando o funcionario com o CPF {}" , cpf);
		return Optional.ofNullable(this.funcionarioRepository.findByCpf(cpf));
	}

	@Override
	public Optional<Funcionario> buscarPorEmail(String email) {
		log.info("Buscando o funcionario com o email {}" , email);
		return Optional.ofNullable(this.funcionarioRepository.findByEmail(email));
	}

	@Override
	public Optional<Funcionario> buscarPorId(Long id) {
		log.info("Buscando o funcionario com o id {}" , id);
		return Optional.ofNullable(this.funcionarioRepository.findOne(id));
	}

	
}
