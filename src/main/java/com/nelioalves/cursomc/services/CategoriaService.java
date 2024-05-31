package com.nelioalves.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nelioalves.cursomc.domain.Categoria;
import com.nelioalves.cursomc.repositories.CategoriaRepository;
import com.nelioalves.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService {

	
	@Autowired 
	private CategoriaRepository repo;
	
	public Categoria find(Integer id) {
		
		Optional<Categoria> obj = repo.findById(id);
				
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName()));
	
	}
	
	/**
	 * Metodo utilizado para inserir uma Categoria nova
	 * @param obj
	 * @return a categoria nova após ser inserida na base
	 */
	public Categoria insert(Categoria obj) {
		obj.setId(null);
		return repo.save(obj);
	}
	
	
	/**
	 * Método utilizado para atualizar uma Categoria existente. 
	 * @param obj
	 * @return
	 */
	public Categoria update(Categoria obj) {
		
		//aqui nós chamamos o método find para garantir que o id exista antes de retorná-lo. Caso ele não exista, o método find
		//lançará uma exceção.
		find(obj.getId());
		
		//Perceba que o metodo "save" tanto serve para Inserir uma categoria nova quanto para editar uma Categoria existente.
		//O que diferencia o que ele deve executar é justamente o id. Caso o id exista, o metodo altera; caso o id não exista, o metodo
		//insere uma Categoria nova.
		return repo.save(obj);
	}
		
}