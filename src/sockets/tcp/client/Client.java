package sockets.tcp.client;


import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class Client implements Runnable{

    private Socket cliente;
    private String option;
    private Scanner teclado;
    private boolean menu;
    private boolean subMenu;

    public Client(Socket c){
        this.cliente = c;
        this.teclado = new Scanner(System.in);
        this.menu = true;
        this.subMenu = true;
    }

    public void run() {
        try {
            PrintStream saida;
            System.out.println("O cliente conectou ao servidor");

            //Cria  objeto para enviar a mensagem ao servidor
            saida = new PrintStream(this.cliente.getOutputStream());

            
            
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
	            		System.out.println("Opção invalida...");
	            	}
	            	
            	
            	} else {
            		this.menu = false;
            	}
         
            	
            	//saida.println(this.option);          
            }

            saida.close();
            this.teclado.close();
            this.cliente.close();
            System.out.println("Fim do cliente!");
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
    private void menuFunc() {
    	
    	do {
	    	System.out.println("Digite o numero do menu");
	    	System.out.println("Opções:");
	    	System.out.println("1- Adicionar Produto");
	    	System.out.println("2- Apagar Produto");
	    	System.out.println("3- Listar Produtos");
	    	System.out.println("4- Pesquisar por nome");
	    	System.out.println("5- Alterar Produto");
	    	System.out.println("6- Exibir Quantidade de produtos");
	    	System.out.println("7- Sair");
    	
	    	this.option = teclado.nextLine();
	    	int digito;
	    	
	    	try {
	    		
	    		digito = Integer.parseInt(this.option);
	    		
	    		switch(digito) {
	    			case 1:
	    				System.out.println("Opção 1");
	    				break;
	    			case 2:
	    				System.out.println("Opção 2");
	    				break;
	    			case 3:
	    				System.out.println("Opção 3");
	    				break;
	    			case 4:
	    				System.out.println("Opção 4");
	    				break;
	    			case 5:
	    				System.out.println("Opção 5");
	    				break;
	    			case 6:
	    				System.out.println("Opção 6");
	    				break;
	    			case 7:
	    				this.subMenu = false;
	    				break;
	        	}
	        	
	    		
	    	}catch(NumberFormatException e) {
	    		System.out.println("Opção Invalida...");
	    	}
    	
    	}while(this.subMenu);
    	
    }

}
