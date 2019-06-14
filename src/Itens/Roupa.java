package Itens;

import modelo.Produto;

public class Roupa extends Produto{
	
	public Roupa(String codigo, String nome, String preco) {
		this.setCodigo(codigo);
		this.setNome(nome);
		this.setTipo("Roupa");
		this.setPreco(preco);
	}

	public Roupa() {
		this.setTipo("Roupa");
	}
	
}
