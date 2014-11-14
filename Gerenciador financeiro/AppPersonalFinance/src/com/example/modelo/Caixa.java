package com.example.modelo;

import java.io.Serializable;

public class Caixa implements Serializable {
    private static final long serialVersionUID = 1L;
	private Long id;
	private Double saldo = 0.0;
	private String dataCaixa;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public void setSaldo(Double saldo){
		this.saldo = saldo;
	}
	
	public Double getSaldo(){
		return saldo;
	}
	
	public void setDataCaixa(String dataCaixa){
		this.dataCaixa = dataCaixa;
	}
	
	public String getData(){
		return dataCaixa;
	}
}
