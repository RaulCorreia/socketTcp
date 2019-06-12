package sockets.tcp.client;


import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class RunClient {

	public static void main(String args[]) throws UnknownHostException, IOException {

    	
        Socket socket = new Socket("127.0.0.1", 12345);
        InetAddress inet = socket.getInetAddress();
        System.out.println("HostAddress = "+inet.getHostAddress());
        System.out.println("HostName = "+inet.getHostName());

      
        Client c = new Client(socket);
        Thread t = new Thread(c);
        t.start();
    }

}
