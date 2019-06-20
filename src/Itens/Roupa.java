package Itens;

import modelo.Produto;

public class Roupa extends Produto{
	
	private String tamanho;
	
	public Roupa(String codigo, String nome, String preco, String tamanho) {
		this.setCodigo(codigo);
		this.setNome(nome);
		this.setTipo("Roupa");
		this.setPreco(preco);
		this.setTamanho(tamanho);
	}

	public Roupa() {
		this.setTipo("Roupa");
	}

	public String getTamanho() {
		return tamanho;
	}

	public void setTamanho(String tamanho) {
		this.tamanho = tamanho;
	}
	
	public String toString() {
		String objeto = getCodigo() + ";" + getNome() + ";" + getTipo() + ";" + getPreco() + ";" + getTamanho();
		return objeto;
	}
	
}
