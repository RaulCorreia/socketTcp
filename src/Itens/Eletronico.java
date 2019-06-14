package Itens;

import modelo.Produto;

public class Eletronico extends Produto{

	public Eletronico(String codigo, String nome, String preco) {
		this.setCodigo(codigo);
		this.setNome(nome);
		this.setTipo("Eletronico");
		this.setPreco(preco);
	}
	
	public Eletronico() {
		this.setTipo("Eletronico");
	}
	
}
