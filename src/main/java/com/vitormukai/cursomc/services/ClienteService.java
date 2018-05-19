package com.vitormukai.cursomc.services;

import com.vitormukai.cursomc.domain.Cidade;
import com.vitormukai.cursomc.domain.Cliente;
import com.vitormukai.cursomc.domain.Endereco;
import com.vitormukai.cursomc.domain.enums.TipoCliente;
import com.vitormukai.cursomc.dto.ClienteNewVO;
import com.vitormukai.cursomc.dto.ClienteVO;
import com.vitormukai.cursomc.repositories.ClienteRepository;
import com.vitormukai.cursomc.repositories.EnderecoRepository;
import com.vitormukai.cursomc.services.exception.DataIntegrityException;
import com.vitormukai.cursomc.services.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repo;
	@Autowired
	private EnderecoRepository enderecoRepository;

	
	public Cliente find(Integer id) {
		Optional<Cliente> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
	}

	@Transactional
	public Cliente insert(Cliente obj) {
		obj.setId(null);
		obj = repo.save(obj);
		enderecoRepository.saveAll(obj.getEnderecos());
		return obj;
	}

	public Cliente update(Cliente obj){
		Cliente newObj = find(obj.getId());
		updateData(newObj, obj);
		return repo.save(newObj);
	}

	public void delete(Integer id){
		find(id);
		try {
			repo.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir porque há entidades relacionadas");
		}
	}

	public List<Cliente> findAll(){
		return repo.findAll();
	}

	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}

	public Cliente fromVO(ClienteVO objVo){
		return new Cliente(objVo.getId(), objVo.getNome(), objVo.getEmail(), null, null);
	}

	public Cliente fromVO(ClienteNewVO objVo){
		Cliente cli = new Cliente(null, objVo.getNome(), objVo.getEmail(), objVo.getCpfOuCnpj(), TipoCliente.toEnum(objVo.getTipo()));
		Cidade cid = new Cidade (objVo.getCidadeId(), null, null);
		Endereco end = new Endereco(null, objVo.getLogradouro(),objVo.getNumero(), objVo.getComplemento(), objVo.getBairro(), objVo.getCep(), cli, cid);
		cli.getEnderecos().add(end);
		cli.getTelefones().add(objVo.getTelefone1());
		if(objVo.getTelefone2()!= null){
			cli.getTelefones().add(objVo.getTelefone2());
		}
		if(objVo.getTelefone3()!= null){
			cli.getTelefones().add(objVo.getTelefone3());
		}
		return cli;
	}

	private void updateData(Cliente newObj, Cliente obj){
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
	}
}
