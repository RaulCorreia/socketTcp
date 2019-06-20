package Itens;

import modelo.Produto;

public class Eletronico extends Produto{

	private String voltagem;
	
	public Eletronico(String codigo, String nome, String preco, String voltagem) {
		this.setCodigo(codigo);
		this.setNome(nome);
		this.setTipo("Eletronico");
		this.setPreco(preco);
		this.setVoltagem(voltagem);
	}
	
	public Eletronico() {
		this.setTipo("Eletronico");
	}

	public String getVoltagem() {
		return voltagem;
	}

	public void setVoltagem(String voltagem) {
		this.voltagem = voltagem;
	}
	
	public String toString() {
		String objeto = getCodigo() + ";" + getNome() + ";" + getTipo() + ";" + getPreco() + ";" + getVoltagem();
		return objeto;
	}
	
}
