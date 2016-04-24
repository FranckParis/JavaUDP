/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package javaudp;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author p1303175
 */
public class Communication extends Thread {
    
    private DatagramSocket comSocket;
    private InetAddress clientAddress;
    private int clientPort;
    private boolean status;
    
    public Communication(DatagramPacket dp){
        
        ScannerDePorts scanP = new ScannerDePorts();
        Random rand = new Random();
        
        try {
            this.comSocket = new DatagramSocket(rand.nextInt((6000 - 2000) + 1) + 2000);
            this.clientAddress =  dp.getAddress();
            this.clientPort = dp.getPort();
            this.status = true;
        } catch (SocketException ex) {
            Logger.getLogger(Communication.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void run(){
        
        System.out.println("Nouveau client :" + this.clientAddress + " : "+ this.clientPort);
        Scanner sc = new Scanner(System.in);
        String s;
        
        connect();
        
        while(status){
            System.out.println("Entrer un message à destination du client");
            s = sc.nextLine();
            send(s); 
            receive();
        }     
    }
    
    public void connect(){
        send("Connecte au serveur sur le port : " + comSocket.getPort());
        receive();
    }
        
    public void send(String s){
        
        try {
          
            //byte[] data
            byte[] data;
            data = s.getBytes("ascii");
            
            //DatagramPacket
            DatagramPacket dprep = new DatagramPacket(data, data.length, this.clientAddress, this.clientPort);
            
            //send
            comSocket.send(dprep);
            
            
            } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            } catch (UnknownHostException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    
    public void receive(){
 
        System.out.println("En attente de réception.");
        
        //Datagram packet creation
        DatagramPacket dp = new DatagramPacket(new byte[128], 128);
        
        //Reception
        try {
            comSocket.receive(dp);
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }

        byte [] receivedData;
        
        receivedData = dp.getData();
        System.out.println(new String(receivedData, 0 , 4) + " patate");
        
        if (new String(receivedData, 0, 4).equals("stop")){
            send("Fermeture de la connexion");
            this.status = false;
        }
            
    }
}

