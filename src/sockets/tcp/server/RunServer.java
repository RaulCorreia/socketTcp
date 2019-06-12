package sockets.tcp.server;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class RunServer {

	public static void main(String[] args) throws Exception{     

        
        ServerSocket socketServidor = new ServerSocket(12345);
        System.out.println("Servidor rodando na porta "+socketServidor.getLocalPort());
        System.out.println("HostAddress = "+ InetAddress.getLocalHost().getHostAddress());
        System.out.println("HostName = "+ InetAddress.getLocalHost().getHostName());

       
        
        System.out.println("Aguardando conexão do cliente...");   

        while (true) {
          Socket cliente = socketServidor.accept();
          
          // Cria uma thread do servidor para tratar a conexão
          Server servidor = new Server(cliente);
          Thread t = new Thread(servidor);
          
          // Inicia a thread para o cliente conectado
          Server.cont++;
          t.start();
        }
        
    }


}
