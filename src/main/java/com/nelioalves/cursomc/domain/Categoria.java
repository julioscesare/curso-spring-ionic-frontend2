package com.nelioalves.cursomc.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;



@Entity
public class Categoria implements Serializable{


	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	private String nome;
	
	
	
	// para evitar problemas de referência circular, onde Categorias carregam os produtos e Produtos carregam Categorias,
	// é necessário adicinoar a anotação @JsonManagerReference aqui para informar que a Classe Categoria deve carregar os
	// seus produtos. Já na Classe de Produtos, deve ser adicionada a anotação @JsonBackReference para informar que as
	// os produtos já foram carregados a partir de outra Classe, nesse caso a classe Categoria, e não deve ser mais buscados.
	
	@JsonManagedReference	
	@ManyToMany(mappedBy="categorias")
	private List<Produto> produtos = new ArrayList<>();
	
	
	public Categoria() {
		
	}

	public Categoria(Integer id, String nome) {
		super();
		this.id = id;
		this.nome = nome;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<Produto> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}
	
	
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Categoria other = (Categoria) obj;
		return Objects.equals(id, other.id);
	}

	
	
	
	
	
	
}
