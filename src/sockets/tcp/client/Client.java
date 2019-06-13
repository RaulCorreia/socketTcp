package sockets.tcp.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

import modelo.Produto;

public class Client implements Runnable{

    private Socket cliente;
    private String option;
    private Scanner teclado;
    private boolean menu;
    private boolean subMenu;
    
    private PrintStream saida; 
 	private DataInputStream in; 
 	private DataOutputStream out;

    public Client(Socket c) throws IOException{
        this.cliente = c;
        this.teclado = new Scanner(System.in);
        this.menu = true;
        this.subMenu = true;
        
        this.in = new DataInputStream(c.getInputStream()); 
        this.out = new DataOutputStream(c.getOutputStream());
    }

    public void run() {
        try {
           
            System.out.println("O cliente conectou ao servidor");

            //Cria  objeto para enviar a mensagem ao servidor
            this.saida = new PrintStream(this.cliente.getOutputStream());

            
            while(this.menu){
            	
            	System.out.println("Iniciar ou Sair?");
            	this.option = teclado.nextLine();
            	
            	if(this.option.equalsIgnoreCase("iniciar")) {
            	
	            	System.out.println("Por Favor insira suas credenciais:");
	            	System.out.print("Usuario: ");
	            	this.option = teclado.nextLine();
	            	
	            	if(this.option.equalsIgnoreCase("cliente")) {
	            		
	            	} else if(this.option.equalsIgnoreCase("funcionario")) {
	            		
	            		System.out.print("Senha: ");
	            		this.option = teclado.nextLine();
	            		if(this.option.equalsIgnoreCase("1234")) {
	            			
	            			menuFunc();
	            			
	            		} else {
	            			System.out.println("Senha invalida...");
	            		}
	            		
	            	} else {
	            		System.out.println("Op��o invalida...");
	            	}
	            	
            	
            	} else {
            		this.menu = false;
            	}
         
            	
            }

            this.saida.close();
            this.teclado.close();
            this.cliente.close();
            System.out.println("Fim do cliente!");
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
    private void menuFunc() throws IOException {
    	
    	do {
	    	System.out.println("Digite o numero do menu");
	    	System.out.println("Op��es:");
	    	System.out.println("1- Adicionar Produto");
	    	System.out.println("2- Apagar Produto");
	    	System.out.println("3- Listar Produtos");
	    	System.out.println("4- Pesquisar por nome");
	    	System.out.println("5- Alterar Produto");
	    	System.out.println("6- Exibir Quantidade de produtos");
	    	System.out.println("7- Comprar produto");
	    	System.out.println("8- Sair");
    	
	    	this.option = teclado.nextLine();
	    	int digito;
	    	
	    	try {
	    		
	    		digito = Integer.parseInt(this.option);
	    		
	    		switch(digito) {
	    			case 1:{
	    				System.out.flush();
	    				Produto p = new Produto();
	    				
	    				System.out.println("Digite o id do produto");
	    				String id = teclado.nextLine();
    					p.setCodigo(id);
	    				
	    				System.out.println("Digite o nome do produto");
	    				String nome = teclado.nextLine();
    					p.setNome(nome);
    					
    					System.out.println("Digite o tipo do produto");
	    				String tipo = teclado.nextLine();
    					p.setTipo(tipo);
    					
    					System.out.println("Digite o pre�o do produto");
	    				String preco = teclado.nextLine();
    					p.setPreco(preco);
    					
    					// Out envia para o servidor o produto
	    				this.out.writeUTF(p.toString());
	    				
	    				// In aguarda a resposta do servidor
	    				String response = this.in.readUTF();
	    				
	    				System.out.println(response);
    				}
	    				break;
	    			case 2:
	    				this.out.writeUTF("Op��o2");
	    				System.out.println("Op��o 2");
	    				break;
	    			case 3:
	    				this.out.writeUTF("Op��o3");
	    				System.out.println("Op��o 3");
	    				break;
	    			case 4:
	    				this.out.writeUTF("Op��o4");
	    				System.out.println("Op��o 4");
	    				break;
	    			case 5:
	    				this.out.writeUTF("Op��o5");
	    				System.out.println("Op��o 5");
	    				break;
	    			case 6:
	    				System.out.println("Op��o 6");
	    				break;
	    			case 7:
	    				System.out.println("Op��o 7");
	    				break;
	    			case 8:
	    				this.subMenu = false;
	    				break;
	        	}
	        	
	    		
	    	}catch(NumberFormatException e) {
	    		System.out.println("Op��o Invalida...");
	    	}
    	
    	}while(this.subMenu);
    	
    }

}
