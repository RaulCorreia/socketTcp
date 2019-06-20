package sockets.tcp.client;


import java.io.IOException;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.Socket;

public class RunClient {
	
	public static void main(String args[]) throws IOException {

        Socket socket = new Socket("127.0.0.1", 12345);
        InetAddress inet = socket.getInetAddress();
        System.out.println("HostAddress = "+inet.getHostAddress());
        System.out.println("HostName = "+inet.getHostName());
        System.out.println("O cliente conectou ao servidor");
        
        MulticastSocket multiSocket = new MulticastSocket(4446);
        InetAddress group = InetAddress.getByName("230.0.0.0");
        multiSocket.joinGroup(group);
        
      
        SimpleCliente cliente = new SimpleCliente(socket, multiSocket, group);
        cliente.init();
        
        System.out.println("Fim do cliente!");
        System.exit(0);
    }
	
	

}
