package sockets.tcp.server;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import Itens.Alimento;
import Itens.Eletronico;
import Itens.Roupa;
import modelo.ListProduto;
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
        Eletronico e1 = new Eletronico("1", "Tv", "1000,00", "220");
        Eletronico e2 = new Eletronico("2", "Celular", "1000,00", "220");
        Eletronico e3 = new Eletronico("3", "Som", "250,00", "220");
        Eletronico e4 = new Eletronico("4", "Computador", "1500,00", "220");
        
        Alimento a1 = new Alimento("5", "Arroz", "5,00", "1");
        Alimento a2 = new Alimento("6", "Feijão", "8,00", "1");
        Alimento a3 = new Alimento("7", "Farinha", "2,00", "1");
        Alimento a4 = new Alimento("8", "Leite", "2,50", "0,5");
        
        Roupa r1 = new Roupa("9", "Camisa", "100,00", "M");
        Roupa r2 = new Roupa("10", "Calça", "150,00", "40");
        Roupa r3 = new Roupa("11", "Meia", "15,00", "M");
        Roupa r4 = new Roupa("12", "Cueca", "25,00", "G");
        
        lista.setItem(e1);
        lista.setItem(e2);
        lista.setItem(e3);
        lista.setItem(e4);
        lista.setItem(a1);
        lista.setItem(a2);
        lista.setItem(a3);
        lista.setItem(a4);
        lista.setItem(r1);
        lista.setItem(r2);
        lista.setItem(r3);
        lista.setItem(r4);
        
        
        while (true) {
          Socket socket = socketServidor.accept();
          
          // Cria uma thread do servidor para tratar a conexão
          Server servidor = new Server(socket, lista, usuarios);
          Thread t = new Thread(servidor);
          
          t.start();
        }
        
    }


}
