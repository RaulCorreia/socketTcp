package sockets.tcp.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

import Itens.Alimento;
import Itens.Eletronico;
import Itens.Roupa;
import modelo.Produto;

public class SimpleCliente {
	
	
	private Socket cliente;
    private String option;
    private Scanner teclado;
    private boolean menu;
    private boolean subMenu;
    
 	private DataInputStream in; 
 	private DataOutputStream out;
 	

    public SimpleCliente(Socket c) throws IOException{
    	
        this.cliente = c;
        this.teclado = new Scanner(System.in);
        this.menu = true;
        this.subMenu = true;
        
        //Cria  objeto para enviar a mensagem ao servidor
		this.in = new DataInputStream(this.cliente.getInputStream()); 
        this.out = new DataOutputStream(this.cliente.getOutputStream());
    }
    
    
    public void init() throws IOException {
        try {
           
            while(this.menu){
            	
            	System.out.println("Iniciar ou Sair?");
            	this.option = teclado.nextLine();
            	
            	if(this.option.equalsIgnoreCase("iniciar")) {
            	
	            	System.out.println("Por Favor insira suas credenciais:");
	            	System.out.print("Usuario: ");
	            	this.option = teclado.nextLine();
	            	
	            	String operacao = "login@"+this.option;
    				String response = sendMessage(operacao);
    				
    				if(response.equalsIgnoreCase("true")) {
    					
    					System.out.print("Senha: ");
    					this.option = teclado.nextLine();
    					
    					String op = "pass@"+this.option;
        				String res = sendMessage(op);
        				
        				if(response.equalsIgnoreCase("false")) {
        					
        					System.out.print("Senha invalida");
        					
        				} else {
        					
        					if(response.equalsIgnoreCase("funcionario")) {
        						
        						menuFunc();
        						
        					} else {
        						System.out.print("Menu Usuario");
        					}
        					
        					
        				}
        				
    				} else {
    					System.out.print("Usuario invalido");
    				}
    				
    				
    				
    				
	            	/*
	            	if(this.option.equalsIgnoreCase("cliente")) {
	            		
	            	} else if(this.option.equalsIgnoreCase("func")) {
	            		
	            		System.out.print("Senha: ");
	            		this.option = teclado.nextLine();
	            		if(this.option.equalsIgnoreCase("123")) {
	            			
	            			menuFunc();
	            			
	            		} else {
	            			System.out.println("Senha invalida...");
	            		}
	            		
	            	} else {
	            		System.out.println("Opção invalida...");
	            	}
	            	*/
            	
            	} else {
            		this.menu = false;
            		String operacao = "close@close";
    				sendMessage(operacao);
            	}
         
            	
            }

            
        } catch (IOException e) {
            e.printStackTrace();
        }
        

        this.teclado.close();
        this.cliente.close();
        this.in.close(); 
        this.out.close();
    }
    
    
    private void menuFunc() throws IOException {
    	
  
    	do {
    		
	    	System.out.println("Digite o numero do menu");
	    	System.out.println("Opções:");
	    	System.out.println("1- Adicionar Produto");
	    	System.out.println("2- Apagar Produto");
	    	System.out.println("3- Listar Produtos");
	    	System.out.println("4- Pesquisar por nome");
	    	System.out.println("5- Alterar Produto");
	    	System.out.println("6- Exibir Quantidade de produtos");
	    	System.out.println("7- Comprar produto");
	    	System.out.println("8- Sair");
	    	System.out.print(">> ");
    	
	    	this.option = teclado.nextLine();
	    	int digito;
	    	
	    	try {
	    		
	    		digito = Integer.parseInt(this.option);
	    		
	    		switch(digito) {
	    		
	    			case 1:{	// Add
	    				System.out.println("Qual o tipo de produto?");
	    				System.out.println("Alimento");
	    				System.out.println("Eletronicos");
	    				System.out.println("Roupa");
	    				
	    				this.option = teclado.nextLine();
	    				Produto p;
	    				String tipo;
	    				if(this.option.equalsIgnoreCase("Alimento")) {
	    					p = new Alimento();
	    					tipo = "1";
	    				}else if(this.option.equalsIgnoreCase("Eletronico")) {
	    					p = new Eletronico();
	    					tipo = "2";
	    				}else {
	    					p = new Roupa();
	    					tipo = "3";
	    				}
	    				
	    				System.out.println("Digite o id do produto");
	    				String id = teclado.nextLine();
    					p.setCodigo(id);
	    				
	    				System.out.println("Digite o nome do produto");
	    				String nome = teclado.nextLine();
    					p.setNome(nome);
    					
    					System.out.println("Digite o preço do produto");
	    				String preco = teclado.nextLine();
    					p.setPreco(preco);
    					
    					String operacao = "add@"+p.toString()+"@"+tipo;
    					
    					
    					System.out.println(sendMessage(operacao));
    					
    					System.out.println("\nEnter para continuar");
	    				teclado.nextLine();
    					
    				}
	    				break;
	    				
	    			case 2: { // Delete
	    				
	    				System.out.flush();
	    				
	    				System.out.println("Digite o nome do produto para deletar");
	    				String nome = teclado.nextLine();
	    				
	    				String operacao = "delete@"+nome.toString();
	    				
	    				System.out.println(sendMessage(operacao));
	    				
	    				System.out.println("\nEnter para continuar");
	    				teclado.nextLine();
	    				
	    			}
	    				break;
	    				
	    			case 3: { // List
	    				
    					System.out.flush();
	    			
	    				String operacao = "list@list";
	    				
	    				String list = sendMessage(operacao);
	    				String objetos[] = list.split("!");
	    				
	    				System.out.println("Lista de Produtos:");
	    				for(int i = 0; i < objetos.length; i++) {
	    					
	    					String objeto[] = objetos[i].split(";");
	    					System.out.println("ID: "+ objeto[0] +" - Nome: " + objeto[1] + " - Tipo: " + objeto[2] + " - Preço: " + objeto[3] );
	    					
	    				}
	    				
	    				System.out.println("\nEnter para continuar");
	    				teclado.nextLine();
	    				
	    			}
	    				break;
	    				
	    			case 4:{ // Search
	    				
	    				System.out.flush();
	    				
	    				System.out.println("Digite o nome do produto");
	    				String nome = teclado.nextLine();
	    				
	    				String operacao = "search@"+nome.toString();
	    				
	    				String response = sendMessage(operacao);
	    				
	    				String objeto[] = response.split(";");
	    				
	    				if(objeto.length > 2) {
		    				System.out.println("Resultado:");
	    					System.out.println("ID: "+ objeto[0] +" - Nome: " + objeto[1] + " - Tipo: " + objeto[2] + " - Preço: " + objeto[3] );
	    				} else {
	    					System.out.println(response);
	    				}
    					
    					System.out.println("\nEnter para continuar");
	    				teclado.nextLine();
	    				
	    			}
	    				break;
	    				
	    			case 5:{ //Update
	    				
    					System.out.flush();
	    				
    					Produto p = new Produto();
	    				System.out.println("Digite o nome do produto que deseja editar");
	    				String nome = teclado.nextLine();
	    				
	    				System.out.println("Digite o novo id do produto");
	    				String id = teclado.nextLine();
    					p.setCodigo(id);
	    				
	    				System.out.println("Digite o nome do produto");
	    				String novoNome = teclado.nextLine();
    					p.setNome(novoNome);
    					
    					System.out.println("Digite o preço do produto");
	    				String preco = teclado.nextLine();
    					p.setPreco(preco);
    					
    					p.setTipo("tipo");
    					
	    				String operacao = "update@"+nome.toString()+"@"+p.toString();
	    				
	    				System.out.println(sendMessage(operacao));
	    				
	    				System.out.println("\nEnter para continuar");
	    				teclado.nextLine();
	    				
	    			}
	    				break;
	    				
	    			case 6:{ // Show qtd
	    				
    					System.out.flush();
	    				
	    				String operacao = "show@qtd";
	    				
	    				System.out.println(sendMessage(operacao));
    					
    					System.out.println("\nEnter para continuar");
	    				teclado.nextLine();
	    				
	    			}
	    				break;
	    				
	    			case 7:{ // Buy
	    				System.out.flush();
	    				
	    				System.out.println("Digite o nome do produto");
	    				String nome = teclado.nextLine();
	    				
	    				String operacao = "buy@"+nome.toString();
	    				
	    				System.out.println(sendMessage(operacao));
	    				
	    				System.out.println("\nEnter para continuar");
	    				teclado.nextLine();
	    			}
	    				break;
	    				
	    			case 8: // Exit
	    				this.subMenu = false;
	    				break;
	        	}
	    		
	    		

	    		
	    	}catch(NumberFormatException e) {
	    		System.out.println("Opção Invalida...");
	    	}
    	
    	}while(this.subMenu);
    	
    }
    
    
    private String sendMessage(String message) {
    	
    	
		try {
			
			// Out envia para o servidor o produto
			this.out.writeUTF(message);
		
			// In aguarda a resposta do servidor
			String response = this.in.readUTF();
			
			return response;
			
		} catch (IOException e) {
			System.out.println("Erro ao tentar enviar mensagem para o servidor");
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		
		return "";
		
    }
}
