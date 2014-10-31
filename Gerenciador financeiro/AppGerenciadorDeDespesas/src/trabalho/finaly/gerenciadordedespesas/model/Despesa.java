package trabalho.finaly.gerenciadordedespesas.model;

import java.util.Date;

public class Despesa {

	private String descricao = " ";
	private Double valor = 0.0;
	private Date data;
	private String status = " ";

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

	public void setData(Date data) {
		this.data = data;
	}

	public Date getData() {
		return data;
	}

}
