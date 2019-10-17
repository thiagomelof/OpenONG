package com.mycompany.apiprodutos.model;

import java.io.Serializable;

public class Categoria implements Serializable{
	private static final long serialVersionUID = -7831833982607327417L;
	private Integer id;
	private String nome;
	
	public Categoria() {
	}



	public Categoria(Integer id, String nome) {
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

	
}
