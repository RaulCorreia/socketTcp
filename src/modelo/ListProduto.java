package modelo;

import java.util.HashMap;
import java.util.Map;

public class ListProduto {
	
	
	Map<String, Produto> lista;
	
	
	public ListProduto() {
		lista = new HashMap<>();
	}
	
	
	public void setItem(Produto novo) {
		this.lista.put(novo.getNome(), novo);
		
	}
	
	
	public Produto getItem(String nome) {
		Produto produto = lista.get(nome);
		return produto;
	}
	
}
