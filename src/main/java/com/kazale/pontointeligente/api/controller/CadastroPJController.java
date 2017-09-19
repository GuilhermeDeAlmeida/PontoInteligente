package com.kazale.pontointeligente.api.controller;

import java.security.NoSuchAlgorithmException;

import javax.naming.spi.DirStateFactory.Result;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.support.Repositories;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kazale.pontointeligente.api.CadastroPJDto;
import com.kazale.pontointeligente.api.entities.Empresa;
import com.kazale.pontointeligente.api.entities.Funcionario;
import com.kazale.pontointeligente.api.enums.PerfilEnum;
import com.kazale.pontointeligente.api.response.Response;
import com.kazale.pontointeligente.api.services.EmpresaService;
import com.kazale.pontointeligente.api.services.FuncionarioService;
import com.kazale.pontointeligente.api.utils.PasswordUtils;

@RestController
@RequestMapping("/api/cadastrar-pj")
@CrossOrigin(origins ="*")
public class CadastroPJController {

	private static final Logger log = LoggerFactory.getLogger(CadastroPJController.class);
	@Autowired
	private FuncionarioService funcionarioService;
	
	@Autowired
	private EmpresaService empresaService; 
	
	public CadastroPJController() {
	}
	
	@PostMapping
	public ResponseEntity<Response<CadastroPJDto>> cadastrar(@Valid @RequestBody CadastroPJDto cadastroPJDto, 
			BindingResult result)throws NoSuchAlgorithmException{
		
		log.info("Cadastrando PJ: {}", cadastroPJDto.toString());
		Response<CadastroPJDto> response = new Response<CadastroPJDto>();
		validarDadosExistentes(cadastroPJDto, result);
		Empresa empresa = this.converterDtoParaEmpresa(cadastroPJDto);
		Funcionario funcionario = this.converterDtoParaFuncionario(cadastroPJDto, result);
		
		if(result.hasErrors()) {
			log.error("Erro validando dados de cadastro PJ: {}", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}
		
		this.empresaService.persistir(empresa);
		funcionario.setEmpresa(empresa);
		this.funcionarioService.persistir(funcionario);
		
		response.setData(this.converterCadastroPJDto(funcionario));
		return ResponseEntity.ok(response);
		
	}
	
	
	private CadastroPJDto converterCadastroPJDto(Funcionario funcionario) {
		CadastroPJDto dto = new CadastroPJDto(); 
		dto.setCnpj(funcionario.getEmpresa().getCnpj());
		dto.setCpf(funcionario.getCpf());
		dto.setEmail(funcionario.getEmail());
		dto.setId(funcionario.getId());
		dto.setNome(funcionario.getNome());
		dto.setRazaoSocial(funcionario.getEmpresa().getRazaoSocial());
		
		return dto;
	}

	private Funcionario converterDtoParaFuncionario(CadastroPJDto cadastroPJDto, BindingResult result) {
		Funcionario funcionario = new Funcionario();
		funcionario.setCpf(cadastroPJDto.getCpf());
		funcionario.setEmail(cadastroPJDto.getEmail());
		funcionario.setNome(cadastroPJDto.getNome());
		funcionario.setPerfil(PerfilEnum.ROLE_ADMIN);
		funcionario.setSenha(PasswordUtils.gerarBCrypt(cadastroPJDto.getSenha()));
		
		return funcionario;
	}

	private Empresa converterDtoParaEmpresa(CadastroPJDto cadastroPJDto) {
		Empresa empresa = new Empresa();
		empresa.setCnpj(cadastroPJDto.getCnpj());
		empresa.setRazaoSocial(cadastroPJDto.getRazaoSocial());
		return empresa;
	}

	public void validarDadosExistentes(CadastroPJDto cadastroPJDto, BindingResult result) {
		this.empresaService.buscarPorCnpj(cadastroPJDto.getCnpj())
		.ifPresent(emp -> result.addError(new ObjectError("empresa", "Empresa já existe!")));
		
		this.funcionarioService.buscarPorCpf(cadastroPJDto.getCpf())
		.ifPresent(fun -> result.addError(new ObjectError("funcionario", "CPF já existe!")));
		
		this.funcionarioService.buscarPorEmail(cadastroPJDto.getEmail())
		.ifPresent(fun -> result.addError(new ObjectError("empresa", "O email já existe!")));
	}
	
}
