package com.example.modelo;

import java.io.Serializable;

public class Despesa implements Serializable {
    private static final long serialVersionUID = 1L;
	private Long id;
	private String descricao = " ";
	private Double valor = 0.0;
	private String data;
	private String status = " ";

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public Double getValor() {
		return valor;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getData() {
		return data;
	}

}
