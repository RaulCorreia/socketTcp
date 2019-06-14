package sockets.tcp.server;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import Itens.Alimento;
import Itens.Eletronico;
import Itens.Roupa;
import constants.Const;
import modelo.ListProduto;
import modelo.Produto;
import modelo.Usuario;

public class Server implements Runnable{
    
	public Socket socketCliente;
    public static int cont = 0;
    private ListProduto lista;
    private Map<String, Usuario> usuarios;

    private DataInputStream in; 
    private DataOutputStream out;
    
    private boolean close;
    
    private Usuario login;

    public Server(Socket cliente, ListProduto lista, Map<String, Usuario> usuarios) throws IOException{
        this.socketCliente = cliente;
        this.usuarios = usuarios;
        this.lista = lista;

        this.in = new DataInputStream(cliente.getInputStream()); 
        this.out = new DataOutputStream(cliente.getOutputStream());
        
        this.close = true;
        
    }

   
    public void run(){
        
        try {
        	
        	do {
        	
	        	// Espera um request do cliente
	        	String rcv = this.in.readUTF();
	        	String acao[] = rcv.split("@");
	        	String response = "Opção desconhecida";
	            
	        	if(acao[0].equalsIgnoreCase("add")) {
	        		
	        		response = addProduto(acao[1], acao[2]);
	        		
	        	} else if(acao[0].equalsIgnoreCase("delete")) {
	        		
	        		response = deleteProduto(acao[1]);
	        		
	        	} else if(acao[0].equalsIgnoreCase("list")) {
	        		
	        		response = listProdutos();
	        		
	        	} else if(acao[0].equalsIgnoreCase("search")) {
	        		
	        		response = getProduto(acao[1]);
	        		
	        	} else if(acao[0].equalsIgnoreCase("update")) {
	        		
	        		response = update(acao[1], acao[2]);
	        		
	        	} else if(acao[0].equalsIgnoreCase("show")) {
	        		
	        		response = quantidade();
	        		
	        	} else if(acao[0].equalsIgnoreCase("buy")) {
	        	
	        		response = buyProduto(acao[1]);
	        		
	        	} else  if(acao[0].equalsIgnoreCase("close")) {
	        		
	        		this.close = false;
	        		
	        	} else if(acao[0].equalsIgnoreCase("login")) {
	        		
	        		for (Object key : usuarios.keySet()) { 
	        			if(key.toString().equalsIgnoreCase(acao[1])) {
	        				response = "true";
	        				this.login = usuarios.get(acao[1]);
	        			} else
	        				response = "false";
	        		}
	        		
	        	} else if(acao[0].equalsIgnoreCase("pass")) {
	        		
	        		if(this.login.getSenha().equalsIgnoreCase(acao[1])) {
	        			
	        			if(this.login.getRole() == Const.FUNCIONARIO)
	        				response = "funcionario";
	        			else
	        				response = "cliente";
	        			
	        		} else {
	        			response = "false";
	        		}
	        		
	        	}
	        	
	        	this.out.writeUTF(response);
        	
        	}while(this.close);
        	
            //Finaliza scanner e socket
        	this.in.close();
        	this.out.close();
            this.socketCliente.close();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
    private String addProduto(String objeto, String tipo) {
    	
    	String produto[] = objeto.split(";");
    	
    	if(produto.length < 4) {
    		return "Todos os campos devem ser preenchidos";
    	}
    	
    	if(produto[0].isEmpty()) {
    		return "Id não pode ser vazio";
    	}
    	if(produto[1].isEmpty()) {
    		return "Nome não pode ser vazio";
    	}
    	if(produto[2].isEmpty()) {
    		return "Preço não pode ser vazio";
    	}
    	
    	// Cria objeto e add na lista de produto
    	Produto novo;
    	if(tipo.equalsIgnoreCase("1"))
    		novo = new Alimento(produto[0], produto[2], produto[3]);
    	else if(tipo.equalsIgnoreCase("2"))
    		novo = new Eletronico(produto[0], produto[2], produto[3]);
    	else
    		novo = new Roupa(produto[0], produto[2], produto[3]);
    	
    	this.lista.setItem(novo);
    	
    	return "Adicionado";
    }
    
    private String listProdutos() {
    	return this.lista.getList();
    }
    
    private String deleteProduto(String nome) {
    	return this.lista.deleteFromList(nome);
    }

    private String getProduto(String nome) {
    	
    	Produto result = this.lista.getItem(nome);
    	
    	if(result != null)
    		return result.toString();
    	else
    		return "Produto não encontrado";
    }
    
    private String update(String nome, String objeto) {
    	
    	String produto[] = objeto.split(";");
    	
    	if(produto.length < 3) {
    		return "Todos os campos devem ser preenchidos";
    	}
    	
    	if(this.lista.checkExist(nome)) {
    		Produto prod = this.lista.getItem(nome);
    		
    		if(prod.getTipo().equalsIgnoreCase("Alimento")) {
    			Alimento novo = new Alimento(produto[0], produto[2], produto[3]);
    			return this.lista.updateItem(nome, novo);
    		} else if(prod.getTipo().equalsIgnoreCase("Eletronico")) {
    			Eletronico novo = new Eletronico(produto[0], produto[2], produto[3]);
    			return this.lista.updateItem(nome, novo);
    		} else {
    			Roupa novo = new Roupa(produto[0], produto[2], produto[3]);
    			return this.lista.updateItem(nome, novo);
    		}
    		
    	} else {
    		return "Item não existe";
    	}
    	
    }
    
    private String buyProduto(String nome) {
    	if(this.lista.delete(nome)) {
    		return "Produto Comprado Com Sucesso!";
    	} else {
    		return "Produto não existe";
    	}
    }
    
    private String quantidade() {
    	return "Possui atualmente " + this.lista.size() + " produtos.";
    }
}
