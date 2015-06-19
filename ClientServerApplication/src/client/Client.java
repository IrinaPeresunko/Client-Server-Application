package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
	
	private final static int PORT = 8080; 
	private BufferedReader in;
	private PrintWriter out;
	private Socket socket;
	
	public Client(){
		Scanner userInput = new Scanner(System.in); 
		
		System.out.println("Enter the IP to connect to the server");
		System.out.println("Format: xxx.xxx.xxx.xxx"); 
	 
		String ip = userInput.nextLine();
		
		try {
			socket = new Socket(ip,PORT);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream(), true);

			System.out.println("Enter your nickname:");
			out.println(userInput.nextLine());

			Resender resend = new Resender();
			resend.start();

			String str = "";
			while (!str.equals("exit")) {
				str = userInput.nextLine();
				out.println(str);
			}
			resend.setStop();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
	}
	private void close() {
		try {
			in.close();
			out.close();
			socket.close();
		} catch (Exception e) {
			System.err.println("Threads were not closed!");
		}
	}
	public static int getPort(){
		return PORT;
	}
	private class Resender extends Thread {

		private boolean stopped;
	
		public void setStop() {
			stopped = true;
		}

		public void run() {
			try {
				while (!stopped) {
					String str = in.readLine();
					System.out.println(str);
				}
			} catch (IOException e) {
				System.err.println("Error getting message");
				e.printStackTrace();
			}
		}
	}

}
