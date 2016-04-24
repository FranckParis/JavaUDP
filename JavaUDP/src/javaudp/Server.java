/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package javaudp;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.*;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author p1303175
 */
public class Server {
    
    //Attributes
    private int servPort;
    private DatagramSocket servSocket;
    private boolean status;
    
    //Constructors
    public Server (int port){
        this.servPort = port;
        this.servSocket = null;
        this.status = true;
    }
    
    //Methods
    
    public void initialise(){
        try {
            this.servSocket = new DatagramSocket(servPort);
        } catch (SocketException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void run (){
        
        System.out.println("Serveur RX302 v0.12");
        System.out.println("En attente de réception.");
        
        
        
        //Reception
        while(status){
            //Datagram packet creation
            DatagramPacket dp = new DatagramPacket(new byte[128], 128);
            
            try {
                servSocket.receive(dp);
            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }

            Communication com = new Communication(dp);
            com.start();
              
        }
        
    }
    
}
