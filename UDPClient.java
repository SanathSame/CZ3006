/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.ncc;

/**
 *
 * @author SURA0007
 */
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Scanner;

public class UDPClient {
    static DatagramSocket socket;
    static final int PORT = 17;
    
    public static void main(String[] args) {   
        while (true) {
            Scanner sc = new Scanner(System.in);
            System.out.println("Server address: ");
            String input = sc.nextLine();
            System.out.println("Port: ");
            int port = sc.nextInt();

            try {
                socket = new DatagramSocket();
                InetAddress serverAddress = InetAddress.getByName(input);
                // InetAddress serverAddress = InetAddress.getByName("localhost");
                socket.connect(serverAddress, port);
            } catch (UnknownHostException | SocketException e) {
                System.out.println("Error: " + e.getMessage());            
            }

            try {
                String clientAddress = InetAddress.getLocalHost().getHostAddress();
                // byte[] requestBuffer = ("Request from " + clientAddress).getBytes();
                byte[] requestBuffer = ("Surawar Sanath Sachin, SSP1 " + clientAddress).getBytes();
                DatagramPacket requestPacket = new DatagramPacket(requestBuffer, requestBuffer.length);
                socket.send(requestPacket);
            } catch (IOException e) {           
                System.out.println("IO Error: " + e.getMessage());
            }

            try {
                byte[] replyBuffer = new byte[1024];
                DatagramPacket replyPacket = new DatagramPacket(replyBuffer, replyBuffer.length);
                socket.receive(replyPacket);            
                String quote = new String(replyPacket.getData());
                System.out.println("Quote of the day: " + quote.trim());
            } catch (IOException e) {           
                System.out.println("IO Error: " + e.getMessage());
            }

            socket.disconnect();
            System.out.println();
        }
    }

}
