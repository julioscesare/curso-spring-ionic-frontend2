package com.nelioalves.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.nelioalves.cursomc.domain.Cidade;
import com.nelioalves.cursomc.domain.Cliente;
import com.nelioalves.cursomc.domain.Endereco;
import com.nelioalves.cursomc.domain.enums.TipoCliente;
import com.nelioalves.cursomc.dto.ClienteDTO;
import com.nelioalves.cursomc.dto.ClienteNewDTO;
import com.nelioalves.cursomc.repositories.ClienteRepository;
import com.nelioalves.cursomc.repositories.EnderecoRepository;
import com.nelioalves.cursomc.services.exceptions.DataIntegrityException;
import com.nelioalves.cursomc.services.exceptions.ObjectNotFoundException;

//Verifcar qual o mais atualizado. O Curso orienta a adicionar o importe abaixo que está comentado. Porém, para efeito de aprendizado 
//estou adicionando a Classe do pacote jakarta por me parecer ser a mais atualizada.

//import org.springframework.transaction.annotation.Transactional;
import jakarta.transaction.Transactional;


@Service
public class ClienteService {

	
	@Autowired 
	private ClienteRepository repo;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	public Cliente find(Integer id) {
		
		Optional<Cliente> obj = repo.findById(id);
				
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
	
	}


	@Transactional
	public Cliente insert(Cliente obj) {
		obj.setId(null);
		obj = repo.save(obj);
		enderecoRepository.saveAll(obj.getEnderecos());
		return obj;
	}
	
	
	/**
	 * Método utilizado para atualizar uma Cliente existente. 
	 * @param obj
	 * @return
	 */
	public Cliente update(Cliente obj) {
		Cliente newObj = find(obj.getId());
		updateData(newObj, obj);
		return repo.save(newObj);
	}
	
	
	private void updateData(Cliente newObj, Cliente obj) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
		
	}

	/**
	 * Método utilizado para deletar uma Cliente a partir do seu Id
	 * @param id
	 */
	public void deleteById(Integer id) {
		try {
			repo.deleteById(id);
		}catch(DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir uma Cliente porque há entidades relacionadas.");
		}
	}
	
	
	public List<Cliente> findAll(){
		return repo.findAll();
	}
	
	
	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}
	

	public Cliente fromDTO(ClienteDTO objDto) {
		return new Cliente(objDto.getId(), objDto.getNome(), objDto.getEmail(), null, null);
	}
	
	
	public Cliente fromDTO(ClienteNewDTO objNewDto) {
		Cliente  cli = new Cliente(null, objNewDto.getNome(), objNewDto.getEmail(), objNewDto.getCpfOuCnpj(), TipoCliente.toEnum(objNewDto.getTipo()));
		Cidade   cid = new Cidade(objNewDto.getCidadeId(), null, null);
		Endereco end = new Endereco(null, objNewDto.getLogradouro(), objNewDto.getNumero(), objNewDto.getComplemento(), objNewDto.getBairro(), objNewDto.getCep(), cli, cid);
		cli.getEnderecos().add(end);
		cli.getTelefones().add(objNewDto.getTelefone1());
		if (objNewDto.getTelefone2()!=null) {
			cli.getTelefones().add(objNewDto.getTelefone2());
		}
		if (objNewDto.getTelefone3()!=null) {
			cli.getTelefones().add(objNewDto.getTelefone3());
		}
		return cli;
	}

	
	
}