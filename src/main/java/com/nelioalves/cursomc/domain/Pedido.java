package com.nelioalves.cursomc.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class Pedido implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@JsonFormat(pattern="dd/MM/yyyy HH:mm")
	private Date data;
	
	//(cascade=CascadeType.ALL) - Essa anotação é necessária porque se não ocorre um erro de Entidade Transiente
	@OneToOne(cascade=CascadeType.ALL, mappedBy="pedido") 
	private Pagamento pagamento;
	
	
	@ManyToOne
	@JoinColumn(name="cliente_id")
	private Cliente cliente;
	
	
	@ManyToOne
	@JoinColumn(name="endereco_de_entrega_id")
	private Endereco enderecoDeEntrega;
	
	
	
	/**
	Utilizamos um tipo de coleção da classe "Set" para garantir que não tenhamos itens duplicados. Pois, essa classe não permite itens duplicados 
	**/
	@OneToMany(mappedBy="id.pedido")
	private Set<ItemPedido> itens = new HashSet<>();
	
	
	public Pedido() {
		
	}
		
	
	public Pedido(Integer id, Date data,  Cliente cliente, Endereco enderecoDeEntrega) {
		super();
		this.id = id;
		this.data = data;
		this.setCliente(cliente);
		this.setEnderecoDeEntrega(enderecoDeEntrega);
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}public Pagamento getPagamento() {
		return pagamento;
	}


	public void setPagamento(Pagamento pagamento) {
		this.pagamento = pagamento;
	}


	public Cliente getCliente() {
		return cliente;
	}


	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}


	public Endereco getEnderecoDeEntrega() {
		return enderecoDeEntrega;
	}


	public void setEnderecoDeEntrega(Endereco enderecoDeEntrega) {
		this.enderecoDeEntrega = enderecoDeEntrega;
	}
	
	
	public Set<ItemPedido> getItens() {
		return itens;
	}


	public void setItens(Set<ItemPedido> itens) {
		this.itens = itens;
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
		Pedido other = (Pedido) obj;
		return Objects.equals(id, other.id);
	}


	
	
}
