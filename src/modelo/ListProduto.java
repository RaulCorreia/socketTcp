package modelo;

import java.util.HashMap;
import java.util.Map;

import Itens.Alimento;
import Itens.Eletronico;
import Itens.Roupa;

public class ListProduto {
	
	
	private Map<String, Produto> lista;
	
	
	public ListProduto() {
		lista = new HashMap<>();
	}
	
	
	public void setItem(Produto novo) {
		this.lista.put(novo.getNome().toLowerCase(), novo);
		
	}
	
	
	public Produto getItem(String nome) {
		if(checkExist(nome)) {
			Produto item = lista.get(nome.toLowerCase());
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
			this.lista.remove(nome.toLowerCase());
			return true;
		} else {
			return false;
		}
	}
	
	public boolean deleteFromList(String nome) {
		
		if(delete(nome)){
			return true;
		} else {
			return false;
		}
	}
	
	public boolean updateItem(String nome, String novoCodigo, String novoNome, String novoPreco, String extra) {
		
		if(checkExist(nome)) {
			
			Produto old = getItem(nome);
//			
			if(old.getTipo().equalsIgnoreCase("Alimento")) {
				
    			Alimento novo = new Alimento(novoCodigo, novoNome, novoPreco, extra);
    			delete(nome);
    			setItem(novo);
    			
    		} else if(old.getTipo().equalsIgnoreCase("Eletronico")) {
    			
    			Eletronico novo = new Eletronico(novoCodigo, novoNome, novoPreco, extra);
    			delete(nome);
    			setItem(novo);
    			
    		} else {
    			
    			Roupa novo = new Roupa(novoCodigo, novoNome, novoPreco, extra);
    			delete(nome);
    			setItem(novo);
    			
    		}
			
		
			return true;
			
		} else {
			return false;
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
	
	public boolean checkId(String id) {
		
		boolean exist = false;
		
		for (Object key : lista.keySet()) { 
			Produto objeto = lista.get(key); 
			if(objeto.getCodigo().equalsIgnoreCase(id))
				exist = true;
			
		}
		
		return exist;
	}
	
	public int size() {
		return this.lista.size();
	}
}
