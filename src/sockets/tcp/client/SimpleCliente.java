package sockets.tcp.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.Socket;
import java.util.Random;
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
 	
 	
 	private MulticastSocket multiSocket;
 	private InetAddress group;
 	private MulticastReceiver multicast = null;
 	
 	
 	private String sign = "";

    public SimpleCliente(Socket c, MulticastSocket multiSocket, InetAddress group) throws IOException{
    	
        this.cliente = c;
        this.teclado = new Scanner(System.in);
        this.menu = true;
        this.subMenu = true;
        
        // Cria objeto para receber a mensagem ao servidor
		this.in = new DataInputStream(this.cliente.getInputStream()); 
		// Cria objeto para enviar a mensagem ao servidor
        this.out = new DataOutputStream(this.cliente.getOutputStream());
        
        
        // Multicast
        this.multiSocket = multiSocket;
        this.group = group;
        
        
        // Assinatura do cliente
        Random r = new Random();
        this.sign = String.valueOf(r.nextInt());
    }
    
    
    public void init() throws IOException {
        try {
           
            while(this.menu){
            	
            	System.out.println("Login ou Sair?");
            	this.option = teclado.nextLine();
            	
            	if(this.option.equalsIgnoreCase("login")) {
            		
            		            	
	            	System.out.println("Por Favor insira suas credenciais:");
	            	System.out.print("Usuario: ");
	            	this.option = teclado.nextLine();
	            	
	            	// Obtem o usuario e envia para o servidor
	            	String operacao = "login@"+this.option;
    				String response = sendMessage(operacao);
    				
    				// Se usuario existir
    				if(response.equalsIgnoreCase("true")) {
    					
    					System.out.print("Senha: ");
    					this.option = teclado.nextLine();
    					
    					// Obtem a senha e envia para o servidor
    					String op = "pass@"+this.option;
        				String res = sendMessage(op);
        				
        				// Se não existir
        				if(res.equalsIgnoreCase("false")) {
        					
        					System.out.println("Senha invalida");
        					
        				} else {
        					
        					
        			        // Registra o cliente no servidor
        			        String sign = "sign@"+this.sign;
            				sendMessage(sign);
            				
            				
            				// Se fez o login corretamente inicia o ouvinte do multicast
        					MulticastReceiver multicast = new MulticastReceiver(this.multiSocket, this.group, this.sign);
        			        Thread t = new Thread(multicast);
        			        t.start();
        			        
        					
        					// Se existir, identifica na resposta do servidor sua Role
        					if(res.equalsIgnoreCase("funcionario")) {
        						
        						// Menu do funcionario
        						menuFunc();
        						
        					} else {
        						
        						// Menu do cliente
        						menuCliente();
        						
        					}
        					
        					
        				}
        				
    				} else {
    					// Se não existir Usuario
    					System.out.println("Usuario invalido");
    				}
    				
    				
    
            	} else if (this.option.equalsIgnoreCase("sair")){
            		// Finalizar o cliente e conexão
            		this.menu = false;
            		String operacao = "close@close";
    				sendMessage(operacao);
    				
    				if(multicast != null) {
    					multicast.desativar();
    				}
            	} else {
            		System.out.println("Opção invalida");
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
    
    
    // Menu do funcionario
    private void menuFunc() throws IOException {
    	
  
    	do {
    		
	    	System.out.println("Digite o numero do menu");
	    	System.out.println("Opções:");
	    	System.out.println("1- Adicionar Produto");
	    	System.out.println("2- Apagar Produto");
	    	System.out.println("3- Listar Produtos");
	    	System.out.println("4- Pesquisar por nome");
	    	System.out.println("5- Pesquisar por codigo");
	    	System.out.println("6- Alterar Produto");
	    	System.out.println("7- Exibir Quantidade de produtos");
	    	System.out.println("8- Comprar produto");
	    	System.out.println("9- Sair");
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
	    					System.out.println("Digite a quantidade de KG");
    						String kg = teclado.nextLine();
    						((Alimento) p).setKilo(kg);
	    				}else if(this.option.equalsIgnoreCase("Eletronico")) {
	    					p = new Eletronico();
	    					tipo = "2";
	    					System.out.println("Digite a voltagem");
    						String volt = teclado.nextLine();
    						((Eletronico) p).setVoltagem(volt);
	    				}else {
	    					p = new Roupa();
	    					tipo = "3";
	    					System.out.println("Digite o tamanho");
    						String tamanho = teclado.nextLine();
    						((Roupa) p).setTamanho(tamanho);
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
    					
    					// Envia para o servidor e exibe a resposta
    					System.out.println(sendMessage(operacao));
    					
    					System.out.println("\nEnter para continuar");
	    				teclado.nextLine();
    					
    				}
	    				break;
	    				
	    			case 2: { // Delete
	    				
	    				System.out.println("Digite o nome do produto para deletar");
	    				String nome = teclado.nextLine();
	    				
	    				String operacao = "delete@"+nome.toString();
	    				
	    				// Envia para o servidor e exibe a resposta
	    				System.out.println(sendMessage(operacao));
	    				
	    				System.out.println("\nEnter para continuar");
	    				teclado.nextLine();
	    				
	    			}
	    				break;
	    				
	    			case 3: { // List
	    				
	    				String operacao = "list@list";
	    				
	    				// Envia para o servidor e obtem a resposta
	    				String list = sendMessage(operacao);
	    				String objetos[] = list.split("!");
	    				
	    				// Exibe a lista de produtos
	    				System.out.println("Lista de Produtos:");
	    				for(int i = 0; i < objetos.length; i++) {
	    					
	    					String objeto[] = objetos[i].split(";");
	    					System.out.print("ID: "+ objeto[0] +" - Nome: " + objeto[1] + " - Tipo: " + objeto[2] + " - Preço: " + objeto[3]);
	    					
	    					if(objeto[2].equalsIgnoreCase("Alimento")) {
	    						System.out.println(" - Kg: " + objeto[4]);
		    				}else if(objeto[2].equalsIgnoreCase("Eletronico")) {
		    					System.out.println(" - Voltagem: " + objeto[4]);
		    				}else {
		    					System.out.println(" - Tamanho: " + objeto[4]);
		    				}
	    					
	    					
	    				}
	    				
	    				System.out.println("\nEnter para continuar");
	    				teclado.nextLine();
	    				
	    			}
	    				break;
	    				
	    			case 4:{ // Search nome
	    				
	    				System.out.println("Digite o nome do produto");
	    				String nome = teclado.nextLine();
	    				
	    				String operacao = "search@"+nome.toString();
	    				
	    				// Envia para o servidor e obtem a resposta
	    				String response = sendMessage(operacao);
	    				
	    				String objeto[] = response.split(";");
	    				
	    				// Exibe a resposta formatada
	    				if(objeto.length > 2) {
		    				System.out.println("Resultado:");
	    					System.out.print("ID: "+ objeto[0] +" - Nome: " + objeto[1] + " - Tipo: " + objeto[2] + " - Preço: " + objeto[3] );
	    					if(objeto[2].equalsIgnoreCase("Alimento")) {
	    						System.out.println("Kg: " + objeto[4]);
		    				}else if(objeto[2].equalsIgnoreCase("Eletronico")) {
		    					System.out.println("Voltagem: " + objeto[4]);
		    				}else {
		    					System.out.println("Tamanho: " + objeto[4]);
		    				}
	    				} else {
	    					System.out.println(response);
	    				}
    					
    					System.out.println("\nEnter para continuar");
	    				teclado.nextLine();
	    				
	    			}
	    				break;
	    				
	    			case 5:{ // Search codigo
	    				
	    				System.out.println("Digite o codigo do produto");
	    				String nome = teclado.nextLine();
	    				
	    				String operacao = "searchCod@"+nome.toString();
	    				
	    				// Envia para o servidor e obtem a resposta
	    				String response = sendMessage(operacao);
	    				
	    				String objeto[] = response.split(";");
	    				
	    				// Exibe a resposta formatada
	    				if(objeto.length > 2) {
		    				System.out.println("Resultado:");
	    					System.out.print("ID: "+ objeto[0] +" - Nome: " + objeto[1] + " - Tipo: " + objeto[2] + " - Preço: " + objeto[3] );
	    					if(objeto[2].equalsIgnoreCase("Alimento")) {
	    						System.out.println("Kg: " + objeto[4]);
		    				}else if(objeto[2].equalsIgnoreCase("Eletronico")) {
		    					System.out.println("Voltagem: " + objeto[4]);
		    				}else {
		    					System.out.println("Tamanho: " + objeto[4]);
		    				}
	    				} else {
	    					System.out.println(response);
	    				}
    					
    					System.out.println("\nEnter para continuar");
	    				teclado.nextLine();
	    				
	    			}
	    				break;
	    				
	    			case 6:{ //Update
	    				
	    				System.out.println("Digite o nome do produto que deseja editar");
	    				String nome = teclado.nextLine();
	    				
    					String operacao = "search@"+nome;
	    				String response = sendMessage(operacao);
	    				
	    				if(!response.equalsIgnoreCase("Produto não encontrado")) {
	    					
	    					String objeto[] = response.split(";");
	    					Produto p;
	    					
	    					if(objeto[2].equalsIgnoreCase("Alimento")) {
	    						p = new Alimento();
	    						p.setTipo(objeto[2]);
	    						System.out.println("Digite a quantidade de KG");
	    						String kg = teclado.nextLine();
	    						((Alimento) p).setKilo(kg);
		    				}else if(objeto[2].equalsIgnoreCase("Eletronico")) {
		    					p = new Eletronico();
		    					p.setTipo(objeto[2]);
		    					System.out.println("Digite a voltagem");
								String volt = teclado.nextLine();
								((Eletronico) p).setVoltagem(volt);
		    				}else {
		    					p = new Roupa();
		    					p.setTipo(objeto[2]);
		    					System.out.println("Digite o tamanho");
								String tamanho = teclado.nextLine();
								((Roupa) p).setTamanho(tamanho);
		    				}
	    					
		    				
		    				System.out.println("Digite o novo id do produto");
		    				String id = teclado.nextLine();
	    					p.setCodigo(id);
		    				
		    				System.out.println("Digite o nome do produto");
		    				String novoNome = teclado.nextLine();
	    					p.setNome(novoNome);
	    					
	    					System.out.println("Digite o preço do produto");
		    				String preco = teclado.nextLine();
	    					p.setPreco(preco);
	    					
	    					
	    					
		    				String operacao2 = "update@"+nome.toString()+"@"+p.toString();
		    				
		    				// Envia para o servidor e exibe a resposta
		    				System.out.println(sendMessage(operacao2));
	    				} else {
	    					System.out.println("Objeto não existe para ser editado.");
	    				}
	    				
	    				System.out.println("\nEnter para continuar");
	    				teclado.nextLine();
	    				
	    			}
	    				break;
	    				
	    			case 7:{ // Show qtd
	    				
	    				String operacao = "show@qtd";
	    				
	    				// Envia para o servidor e exibe a resposta
	    				System.out.println(sendMessage(operacao));
    					
    					System.out.println("\nEnter para continuar");
	    				teclado.nextLine();
	    				
	    			}
	    				break;
	    				
	    			case 8:{ // Buy
	    				
	    				System.out.println("Digite o nome do produto");
	    				String nome = teclado.nextLine();
	    				
	    				String operacao = "buy@"+nome.toString();
	    				
	    				// Envia para o servidor e exibe a resposta
	    				System.out.println(sendMessage(operacao));
	    				
	    				System.out.println("\nEnter para continuar");
	    				teclado.nextLine();
	    			}
	    				break;
	    				
	    			case 9: // Exit
	    				this.subMenu = false;
	    				break;
	        	}
	    		
	    		

	    		
	    	}catch(NumberFormatException e) {
	    		System.out.println("Opção Invalida...");
	    	}
    	
    	}while(this.subMenu);
    	
    	this.subMenu = true;
    }
    
    
    // Menu do cliente
    private void menuCliente() throws IOException {
    	do {
    		
	    	System.out.println("Digite o numero do menu");
	    	System.out.println("Opções:");
	    	System.out.println("1- Listar Produtos");
	    	System.out.println("2- Pesquisar por nome");
	    	System.out.println("3- Pesquisar por id");
	    	System.out.println("4- Exibir Quantidade de produtos");
	    	System.out.println("5- Comprar produto");
	    	System.out.println("6- Sair");
	    	System.out.print(">> ");
    	
	    	this.option = teclado.nextLine();
	    	int digito;
	    	
	    	try {
	    		
	    		digito = Integer.parseInt(this.option);
	    		
	    		switch(digito) {
	    		
	    			
	    			case 1: { // List
	    				
    					String operacao = "list@list";
	    				
	    				// Envia para o servidor e obtem a resposta
	    				String list = sendMessage(operacao);
	    				String objetos[] = list.split("!");
	    				
	    				// Exibe a lista de produtos
	    				System.out.println("Lista de Produtos:");
	    				for(int i = 0; i < objetos.length; i++) {
	    					
	    					String objeto[] = objetos[i].split(";");
	    					System.out.print("ID: "+ objeto[0] +" - Nome: " + objeto[1] + " - Tipo: " + objeto[2] + " - Preço: " + objeto[3]);
	    					
	    					if(objeto[2].equalsIgnoreCase("Alimento")) {
	    						System.out.println(" - Kg: " + objeto[4]);
		    				}else if(objeto[2].equalsIgnoreCase("Eletronico")) {
		    					System.out.println(" - Voltagem: " + objeto[4]);
		    				}else {
		    					System.out.println(" - Tamanho: " + objeto[4]);
		    				}
	    					
	    					
	    				}
	    				
	    				System.out.println("\nEnter para continuar");
	    				teclado.nextLine();
	    				
	    			}
	    				break;
	    				
	    			case 2:{ // Search
	    				
    					System.out.println("\nEnter para continuar");
	    				teclado.nextLine();
	    				
	    				System.out.println("Digite o nome do produto");
	    				String nome = teclado.nextLine();
	    				
	    				String operacao = "search@"+nome.toString();
	    				
	    				// Envia para o servidor e obtem a resposta
	    				String response = sendMessage(operacao);
	    				
	    				String objeto[] = response.split(";");
	    				
	    				// Exibe a resposta formatada
	    				if(objeto.length > 2) {
		    				System.out.println("Resultado:");
	    					System.out.print("ID: "+ objeto[0] +" - Nome: " + objeto[1] + " - Tipo: " + objeto[2] + " - Preço: " + objeto[3] );
	    					if(objeto[2].equalsIgnoreCase("Alimento")) {
	    						System.out.println("Kg: " + objeto[4]);
		    				}else if(objeto[2].equalsIgnoreCase("Eletronico")) {
		    					System.out.println("Voltagem: " + objeto[4]);
		    				}else {
		    					System.out.println("Tamanho: " + objeto[4]);
		    				}
	    				} else {
	    					System.out.println(response);
	    				}
    					
    					System.out.println("\nEnter para continuar");
	    				teclado.nextLine();
	    				
	    			}
	    				break;
	    				
	    			case 3:{ // Search codigo
	    				
	    				System.out.println("Digite o codigo do produto");
	    				String nome = teclado.nextLine();
	    				
	    				String operacao = "searchCod@"+nome.toString();
	    				
	    				// Envia para o servidor e obtem a resposta
	    				String response = sendMessage(operacao);
	    				
	    				String objeto[] = response.split(";");
	    				
	    				// Exibe a resposta formatada
	    				if(objeto.length > 2) {
		    				System.out.println("Resultado:");
	    					System.out.print("ID: "+ objeto[0] +" - Nome: " + objeto[1] + " - Tipo: " + objeto[2] + " - Preço: " + objeto[3] );
	    					if(objeto[2].equalsIgnoreCase("Alimento")) {
	    						System.out.println("Kg: " + objeto[4]);
		    				}else if(objeto[2].equalsIgnoreCase("Eletronico")) {
		    					System.out.println("Voltagem: " + objeto[4]);
		    				}else {
		    					System.out.println("Tamanho: " + objeto[4]);
		    				}
	    				} else {
	    					System.out.println(response);
	    				}
    					
    					System.out.println("\nEnter para continuar");
	    				teclado.nextLine();
	    				
	    			}
	    				
	    			case 4:{ // Show qtd
	    				
	    				String operacao = "show@qtd";
	    				
	    				System.out.println(sendMessage(operacao));
    					
    					System.out.println("\nEnter para continuar");
	    				teclado.nextLine();
	    				
	    			}
	    				break;
	    				
	    			case 5:{ // Buy
	    				
	    				System.out.println("Digite o nome do produto");
	    				String nome = teclado.nextLine();
	    				
	    				String operacao = "buy@"+nome.toString();
	    				
	    				System.out.println(sendMessage(operacao));
	    				
	    				System.out.println("\nEnter para continuar");
	    				teclado.nextLine();
	    				
	    			}
	    				break;
	    				
	    			case 6: // Exit
	    				this.subMenu = false;
	    				break;
	        	}
	    		
	    		

	    		
	    	}catch(NumberFormatException e) {
	    		System.out.println("Opção Invalida...");
	    	}
    	
    	}while(this.subMenu);
    	
    	this.subMenu = true;
    }
    
    
    // Comunicação com o servidor
    // envia a operação e recebe a resposta passando adiante
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
    
    
    
    // Classe responsavel por ficar ouvindo o multicast
    public class MulticastReceiver extends Thread {
    	
        protected MulticastSocket multiSocket = null;
        protected byte[] buf = new byte[256];
        protected InetAddress group;
        protected boolean run = true;
        protected String sign;
        
        
        public MulticastReceiver(MulticastSocket multiSocket, InetAddress group, String sign) {
        	this.multiSocket = multiSocket;
        	this.group = group;
        	this.sign = sign;
        }
     
        public void run() {
        	try {
        		
        		while (this.run) {
            	
            		DatagramPacket packet = new DatagramPacket(buf, buf.length);
                
					multiSocket.receive(packet);
				
	                String received = new String(packet.getData(), 0, packet.getLength());
	                if ("end".equals(received)) {
	                    break;
	                } else {
	                	
	                	String split[] = received.split("!");
	                	
	                	
	                	// Se a mensagem originol do cliente ele nao ira exibir
	                	// Somente para o restante do grupo
	                	if(!split[0].equals(sign)) {
		                	System.err.println(" ** "+split[1]+ " ** ");
	                	}
	                	
	                }
               
	            }
        		
	            multiSocket.leaveGroup(group);
	            multiSocket.close();
            
        	 } catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        }
        
        
        public void desativar() {
        	this.run = false;
        }
        
    }
}
