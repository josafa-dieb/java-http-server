package dev.jdieb.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

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
			String pathFile = requestData[1];
			
			
			
		}
		
		
	}

}
