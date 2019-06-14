package sockets.tcp.server;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import Itens.Eletronico;
import modelo.ListProduto;
import modelo.Produto;
import modelo.Usuario;
import constants.Const;

public class RunServer {

	public static void main(String[] args) throws Exception{ 
		

        
        ServerSocket socketServidor = new ServerSocket(12345);
        System.out.println("Servidor rodando na porta "+socketServidor.getLocalPort());
        System.out.println("HostAddress = "+ InetAddress.getLocalHost().getHostAddress());
        System.out.println("HostName = "+ InetAddress.getLocalHost().getHostName());
        
        System.out.println("Aguardando conexão do cliente...");   
        
        //----------------
        
        Map<String, Usuario> usuarios = new HashMap<>();
        Usuario func = new Usuario(Const.FUNCIONARIO, "func", "123");
        Usuario cliente = new Usuario(Const.CLIENTE, "cliente", "123");
        
        usuarios.put(func.getUsuario(), func);
        usuarios.put(cliente.getUsuario(), cliente);
        
        //----------------
        
        
        
        ListProduto lista = new ListProduto();
        
        // ID, Nome, Tipo, Preco
        Eletronico e1 = new Eletronico("1", "Tv", "1000,00");
        Eletronico e2 = new Eletronico("2", "Celular", "1000,00");
        Eletronico e3 = new Eletronico("3", "Som", "250,00");
        Eletronico e4 = new Eletronico("4", "Computador", "1500,00");
        
        
        lista.setItem(e1);
        lista.setItem(e2);
        lista.setItem(e3);
        lista.setItem(e4);

        while (true) {
          Socket socket = socketServidor.accept();
          
          // Cria uma thread do servidor para tratar a conexão
          Server servidor = new Server(socket, lista, usuarios);
          Thread t = new Thread(servidor);
          
          t.start();
        }
        
    }


}
