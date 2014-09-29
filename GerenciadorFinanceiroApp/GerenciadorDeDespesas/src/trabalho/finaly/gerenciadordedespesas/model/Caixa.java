package trabalho.finaly.gerenciadordedespesas.model;

import java.util.Date;

public class Caixa {
	
	private Long id;
	private Double saldo = 0.0;
	private Double receita = 0.0;
	private Date dataCaixa = new Date();
	
	
	
	public void setId(Long id){
		this.id = id;
	}
	
	public Long getId(){
		return id;
	}
	
	public void setSaldo(Double saldo){
		this.saldo = saldo;
	}
	
	public Double getSaldo(){
		return saldo;
	}
	public void setReceita(Double receita){
		this.receita = receita;
	}
	
	public Double getReceita(){
		return receita;
	}
	
	public void setDataCaixa(Date dataCaixa){
		this.dataCaixa = dataCaixa;
	}
	
	public Date getData(){
		return dataCaixa;
	}
	
}
