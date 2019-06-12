package sockets.tcp.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class SimpleClient {
	
	private static Scanner sc;

	public static void main(String args []) throws IOException{ 

		int k = 0;
        String s = "";

		// Conectar ao servidor 
		Socket client = new Socket("localhost", 5000);

		// Cria canal para receber dados 
		DataInputStream in = new DataInputStream(client.getInputStream()); 

		// Cria canal para enviar dados 
		DataOutputStream out = new DataOutputStream(client.getOutputStream()); 

		sc = new Scanner(System.in);
		System.out.println("Digite um valor entre 0 e 9");
		
		k = sc.nextInt();
		 
		//k = Integer.parseInt(JOptionPane.showInputDialog(null, "Digite um valor entre 0 e 9.")); 

		System.out.println("\n\n <-- Valor enviado ao servidor: "+ k); 

		out.writeInt( k ); //Envia o inteiro 3. 

		s = in.readUTF(); //Aguarda o recebimento de uma string. 

		System.out.println("\n\n --> Mensagem recebida \nValor por extenso é: "+ s + "\n\n"); 

		//Fecha os canais de entrada e saída. 
		in.close(); 

		out.close(); 

		//Fecha o socket. 
		client.close(); 
	}

}
