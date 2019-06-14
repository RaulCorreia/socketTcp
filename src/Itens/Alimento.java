package Itens;

import modelo.Produto;

public class Alimento extends Produto{
	
	public Alimento(String codigo, String nome, String preco) {
		this.setCodigo(codigo);
		this.setNome(nome);
		this.setTipo("Alimento");
		this.setPreco(preco);
	}

	public Alimento() {
		this.setTipo("Alimento");
	}
}
