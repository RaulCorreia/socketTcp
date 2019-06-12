package sockets.tcp.server;


import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Server implements Runnable{
    
	public Socket socketCliente;
    public static int cont = 0;

    public Server(Socket cliente){
        this.socketCliente = cliente;
    }

   
    public void run(){
        
        try {
            Scanner s = null;
            s = new Scanner(this.socketCliente.getInputStream());
            String rcv;
            
            //Exibe mensagem no console
            while(s.hasNextLine()){
            	rcv = s.nextLine();
            	
            	if (rcv.equalsIgnoreCase("fim"))
            		break;
            	else
            		System.out.println(rcv);
            }

            //Finaliza scanner e socket
            s.close();
            this.socketCliente.close();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
