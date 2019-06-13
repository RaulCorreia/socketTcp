package sockets.tcp.server;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

import modelo.ListProduto;
import modelo.Produto;

public class Server implements Runnable{
    
	public Socket socketCliente;
    public static int cont = 0;
    private ListProduto lista;

    private DataInputStream in; 
    private DataOutputStream out;

    public Server(Socket cliente) throws IOException{
        this.socketCliente = cliente;
        this.lista = new ListProduto();

        this.in = new DataInputStream(cliente.getInputStream()); 
        this.out = new DataOutputStream(cliente.getOutputStream());
    }

   
    public void run(){
        
        try {
        	
        	// Servidor apenas recebendo a opção 1 de criar produto
        	
        	// Espera um request do cliente
        	String rcv = this.in.readUTF();

            String objeto[] = rcv.split(";");
        	System.out.println(objeto[0]);
        	System.out.println(objeto[1]);
        	System.out.println(objeto[2]);
        	System.out.println(objeto[3]);
        	
        	// Cria objeto e add na lista de produtos
        	Produto novo = new Produto(objeto[0], objeto[1], objeto[2], objeto[3]);
        	this.lista.setItem(novo);
        	
        	// Envia a resposta para o cliente
			this.out.writeUTF("Recebido");
            
            
            //Finaliza scanner e socket
        	this.in.close();
        	this.out.close();
            this.socketCliente.close();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
