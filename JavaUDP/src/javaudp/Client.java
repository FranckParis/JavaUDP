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
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author p1303175
 */
public class Client {
     //Attributes
    private DatagramSocket clientSocket;
    private boolean status;
    private int comPort;
    
    //Constructors
    public Client (){
        this.clientSocket = null;
        this.status = true;
        this.comPort = 125;
    }
    
    //Methods
    
    public void initialise(){
        try {
            this.clientSocket = new DatagramSocket();
            System.out.println("Client RX302 v0.12");
            
        } catch (SocketException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void connect (){
        System.out.println("Connexion au serveur en cours");
        send("Connexion au serveur", comPort);
        receive();
    }
    
    public void run(){
        
        Scanner sc = new Scanner(System.in);
        String s;
        
        connect();
        
        while(status){
            System.out.println("Entrer un message à destination du serveur : ");
            s = sc.nextLine();
            send(s, comPort);
            receive();
        }     
    }
    
    public void send (String s, int port){
        
        try {
        
            //byte[] data
            byte[] data;
            data = s.getBytes("ascii");
            
            //DatagramPacket
            InetAddress servAddress = InetAddress.getByName("localhost");
            DatagramPacket dp = new DatagramPacket(data, data.length, servAddress,port);
            
            //send
            clientSocket.send(dp);
            
            
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnknownHostException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }   
    }
    
    public void receive(){
        //Datagram packet creation
        DatagramPacket dp = new DatagramPacket(new byte[128], 128);
        
        //Reception
        try {
            System.out.println("En attente de réponse du serveur.");
            clientSocket.receive(dp);
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        this.comPort = dp.getPort();
        String receivedData;
        
        try {
            receivedData = new String (dp.getData(), "ascii");
            System.out.println(receivedData);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
