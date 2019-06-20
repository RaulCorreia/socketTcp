package Itens;

import modelo.Produto;

public class Alimento extends Produto{
	
	private String kilo;
	
	public Alimento(String codigo, String nome, String preco, String kilo) {
		this.setCodigo(codigo);
		this.setNome(nome);
		this.setTipo("Alimento");
		this.setPreco(preco);
		this.setKilo(kilo);
	}

	public Alimento() {
		this.setTipo("Alimento");
	}

	public String getKilo() {
		return kilo;
	}

	public void setKilo(String kilo) {
		this.kilo = kilo;
	}
	
	public String toString() {
		String objeto = getCodigo() + ";" + getNome() + ";" + getTipo() + ";" + getPreco() + ";" + getKilo();
		return objeto;
	}
	
}
