package sockets.tcp.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SimpleServer {
	
	public static void main(String args []) throws IOException{ 
		 
		
        String s = "";

        //Cria um socket servidor na porta 5000 
        ServerSocket serverSocket=new ServerSocket(5000); 
	 
        System.out.println("\n\nIniciando servidor TCP...\n");

        System.out.println("Servidor pronto para receber conexões...\n\n"); 

        // O metodo accept retorna um socket para comunicação com o próximo 
        // cliente a conectar. 
        // A execução do método bloqueia até que algum cliente conecte no  
        // servidor. 
        Socket socket = serverSocket.accept();	 
        
        // imprime o ip do cliente
        System.out.println("Nova conexão com o cliente " + socket.getInetAddress().getHostAddress()); 

        //Cria um canal para receber dados. 
        DataInputStream in = new DataInputStream(socket.getInputStream()); 

        //Cria um canal para enviar dados. 
        DataOutputStream out = new DataOutputStream(socket.getOutputStream()); 

        int k = in.readInt(); //Aguarda o recebimento de um int. 

        switch(k){ 

			case 1: s = "um"; break; 

			case 2: s = "dois"; break; 

			case 3: s = "três"; break; 

			case 4: s = "quatro"; break; 

			case 5: s = "cinco"; break; 

			case 6: s = "seis"; break; 

			case 7: s = "sete"; break; 

			case 8: s = "oito"; break; 

			case 9: s = "nove"; break; 

			case 0: s = "zero"; break; 

			default: s = "Número inexistente. Digite valores entre 0-9."; break; 

        } 

        out.writeUTF(s); //Envia uma string. 

        System.out.println("--> Servidor enviando valor: " + s);	
        //Fecha os canais in e out do socket que estão atendendo ao cliente 
        in.close(); 

        out.close(); 

        //Fecha o socket para o cliente. 
        socket.close(); 

        System.out.println("*****Conexão finalizada*****\n"); 

        //Fechando o servidor. 
        serverSocket.close(); 

	} 

}
