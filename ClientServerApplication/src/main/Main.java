package main;

import java.util.Scanner;

import client.Client;
import server.Server;

public class Main {
	public static void main(String[] args){
		
		Scanner in = new Scanner(System.in);
		System.out.println("Run this program as a server or a client? ('S' - server / 'C' - client)");
		while(true){
			char answer = Character.toLowerCase(in.nextLine().charAt(0));
			switch(answer){
				case 's' : new Server();break;
				case 'c' : new Client();break;
				default:System.out.println("Invalid input. Repeat please");
			}
		}
	}
}
