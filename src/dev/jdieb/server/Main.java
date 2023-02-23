package dev.jdieb.server;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class Main {
	
	// SERVIDOR HTTP SIMPLES :D
	public static void main(String[] args) throws IOException {
		
		ServerSocket server = new ServerSocket(8000);
		Socket handleServer = server.accept();
		
		if(handleServer.isConnected()) {
			
			System.out.println(handleServer.getInetAddress() + " connected!");
			BufferedReader buffer = new BufferedReader(new InputStreamReader(handleServer.getInputStream()));

			String data = buffer.readLine();
			String[] requestData = data.split(" ");
			
			String method = requestData[0];
			String filePath = requestData[1];
			String protocol = requestData[2];

			// criar um match depois para procurar por arquivos que possam existir apos a conexao estabelecida
			// em determinado caminho!
			if(filePath.equalsIgnoreCase("/"))
				filePath = "index.html";
			
			// criar uma melhoria para implementar todos os status code da especificacao do protocolo http 2.0
			File index = new File(filePath.replaceFirst("/", ""));
			String status = protocol + " 200 OK\r\n";
			if(!index.exists()) {
				status = protocol + " 404 Not Found\r\n";
				index = new File("404.html");
			}
			
			byte[] content = Files.readAllBytes(index.toPath());
			
			SimpleDateFormat dateFormat = new SimpleDateFormat("E, DD MMM yyyy hh:mm:ss", Locale.ENGLISH);
			dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
			
			Date date = new Date();
			String dateFormated = dateFormat.format(date) + " GMT";
			
			// header response
			
			 String header = status
	                    + "Location: http://localhost:8000/\r\n"
	                    + "Date: " + dateFormated + "\r\n"
	                    + "Server: MeuServidor/1.0\r\n"
	                    + "Content-Type: text/html\r\n"
	                    + "Content-Length: " + content.length + "\r\n"
	                    + "Connection: close\r\n"
	                    + "\r\n";

			 OutputStream response = handleServer.getOutputStream();
			 response.write(header.getBytes());
			 response.write(content);
			 response.flush();
			
			
			
			
			
		}
		
		
	}

}
