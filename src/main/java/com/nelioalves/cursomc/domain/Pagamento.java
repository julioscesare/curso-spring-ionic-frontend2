package com.nelioalves.cursomc.domain;

import java.io.Serializable;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nelioalves.cursomc.domain.enums.EstadoPagamento;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;


/**
	Explicação sobre a notação @Inheritance(strategy=InheritanceType.JOINED) 
	A anotação @Inheritance(strategy=InheritanceType.JOINED) do Java Persistence API (JPA) 
	é usada para definir a estratégia de herança para uma hierarquia de classes mapeadas para 
	um banco de dados. Essa anotação é colocada na classe pai de uma hierarquia de entidades.
	Quando você usa a estratégia InheritanceType.JOINED, o JPA cria uma tabela separada para 
	cada classe na hierarquia de herança. Cada tabela contém as colunas para os atributos 
	definidos especificamente naquela classe. Todas as tabelas estão ligadas através de chaves 
	estrangeiras que correspondem à chave primária da tabela da superclasse. Isso significa que 
	para cada instância de uma classe derivada, haverá uma entrada correspondente na tabela da 
	superclasse e uma entrada na tabela específica da subclasse.
*/

@Entity
@Inheritance(strategy=InheritanceType.JOINED)  
public abstract class Pagamento  implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	private Integer id;
	private Integer estado;
	
	@JsonIgnore
	@OneToOne
	@JoinColumn(name="pedido_id")
	@MapsId  //Essa anotação garante que o Id do Pedido seja o mesmo do pagamento
 	private Pedido pedido;
	
	public Pagamento() {
		
	}

	public Pagamento(Integer id, EstadoPagamento estado, Pedido pedido) {
		super();
		this.id = id;
		this.estado = estado.getCod();
		this.pedido = pedido;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public EstadoPagamento getEstado() {
		return EstadoPagamento.toEnum(estado);
	}

	public void setEstado(EstadoPagamento estado) {
		this.estado = estado.getCod();
	}

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
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
		Pagamento other = (Pagamento) obj;
		return Objects.equals(id, other.id);
	}
	
	
	
}
