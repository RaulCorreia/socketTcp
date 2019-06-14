package modelo;

import java.util.HashMap;
import java.util.Map;

public class ListProduto {
	
	
	private Map<String, Produto> lista;
	
	
	public ListProduto() {
		lista = new HashMap<>();
	}
	
	
	public void setItem(Produto novo) {
		this.lista.put(novo.getNome(), novo);
		
	}
	
	
	public Produto getItem(String nome) {
		if(checkExist(nome)) {
			Produto item = lista.get(nome);
			return item;
		} else {
			return null;
		}
	}
	
	
	public String getList() {
		
		String listString = "";
		
		for (Object key : lista.keySet()) { 
			Produto objeto = lista.get(key); 
			listString += objeto.toString() + "!";
		}
		
		return listString;
	}
	
	public boolean delete(String nome) {
		if(checkExist(nome)) {
			this.lista.remove(nome);
			return true;
		} else {
			return false;
		}
	}
	
	public String deleteFromList(String nome) {
		
		if(delete(nome)){
			return "Produto Deletado";
		} else {
			return "Item não existe";
		}
	}
	
	public String updateItem(String nome, Produto novo) {
		
		if(checkExist(nome)) {
			Produto old = getItem(nome);
			
			if(!novo.getCodigo().isEmpty()) {
				old.setCodigo(novo.getCodigo());
			}
			
			if(!novo.getNome().isEmpty()) {
				old.setNome(novo.getNome());
			}
			
			if(!novo.getTipo().isEmpty()) {
				old.setTipo(novo.getTipo());
			}
			
			if(!novo.getPreco().isEmpty()) {
				old.setPreco(novo.getPreco());
			}
			
			return "Objeto Atualizado";
			
		} else {
			return "Item não existe";
		}
	}
	
	public boolean checkExist(String nome) {
		
		boolean exist = false;
		
		for (Object key : lista.keySet()) { 
			
			if(key.toString().equalsIgnoreCase(nome))
				exist = true;
			
		}
		
		return exist;
	}
	
	public int size() {
		return this.lista.size();
	}
}
