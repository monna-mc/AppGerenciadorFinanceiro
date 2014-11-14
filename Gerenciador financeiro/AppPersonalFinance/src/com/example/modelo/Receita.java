package com.example.modelo;

import java.io.Serializable;


public class Receita implements Serializable {
    private static final long serialVersionUID = 1L;
	private Long id;
	private String descricao = " ";
	private Double receita = 0.0;
	private String data;
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public void setReceita(Double receita){
		this.receita = receita;
	}
	
	public Double getReceita(){
		return receita;
	}
	
	public String getData(){
		return data;
	}
	
	public void setData(String data) {
		this.data = data;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	// Will be used by the ArrayAdapter in the ListView
    @Override
    public String toString() {
        return descricao;
    }

}


