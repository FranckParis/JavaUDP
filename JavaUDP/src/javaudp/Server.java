/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaudp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

/**
 *
 * @author Francky
 */
public class Server {
    private DatagramSocket ds;
    private DatagramPacket dp;

    Server(int port) {
        try {
            this.ds = new DatagramSocket(port);
        } catch (SocketException ex) {
            System.out.println("Erreur lors de la création du socket");
        }

        this.dp = new DatagramPacket(new byte[128], 128);
    }

    public void run(){
        byte[] data;
        InetAddress address;
        int port;
        
        System.out.println ("Serveur RX302 démarré.");
        System.out.println ("En attente de connexion du client.");
        
        while(true) {
            try {
                
                //Reception
                this.ds.receive(this.dp);

                data = this.dp.getData();
                address = this.dp.getAddress();
                port = this.dp.getPort();
                
                
                //Opening thread
                if (new String(data, 0, 19).equals("hello serveur RX302")){
                    Communication com = new Communication(address, port);
                    (new Thread(){
                        public void run(){
                            com.run();
                        }}).start();
                }
            } catch (IOException e) {
            }
        }
    }

    public void stop(){
        this.ds.close();
    }
  
}
