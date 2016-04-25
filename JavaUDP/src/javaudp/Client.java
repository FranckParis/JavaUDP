/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaudp;



import java.io.IOException;
import java.net.*;
import java.util.Scanner;

/**
 *
 * @author Francky
 */
public class Client {
    DatagramSocket ds;
    DatagramPacket dp;
    int port;
    boolean status;

    public Client() {
        try {
            this.ds = new DatagramSocket();
            this.port = 3142;
            this.status = true;
        } catch (SocketException ex) {
            System.out.println("Erreur lors de la création du socket");
        }

        this.dp = new DatagramPacket(new byte[128], 128);
    }

    public void run(){
        
        System.out.println("Client RX302 démarré.");
        
        Scanner reader = new Scanner(System.in);
        String text;
        
        byte[] data = (new String("hello serveur RX302")).getBytes();
        byte[] host = { (byte)134, (byte)214, (byte)117, (byte)127 };
        byte[] dataTemp = data;
        
        try {
            //InetAddress adr = InetAddress.getByAddress(host);
            InetAddress adr = InetAddress.getLocalHost();
            this.dp = new DatagramPacket(data, data.length, adr, this.port);
            this.ds.send(dp);
            
            data = new byte[data.length];
            this.dp = new DatagramPacket(data, data.length, adr, this.port);

            this.ds.receive(this.dp);

            if (new String(data, 0, 19).equals("serveur RX302 ready")){
                port = this.dp.getPort();
                System.out.println(new String(data) + " - Connexion engagée");
            }
            
            // Running 
            
            while (status){
                System.out.println("Entrer un message pour le serveur");
                
                if(reader.hasNextLine()){
                    text = reader.nextLine();
                    data = text.getBytes();
                    dataTemp = data;
                    
                    this.dp = new DatagramPacket(data, data.length, adr, this.port);
                    this.ds.send(dp);
                    
                    data = new byte[data.length];
                    this.dp = new DatagramPacket(data, data.length, adr, this.port);
                    
                    this.ds.receive(this.dp);

                    //Stop thread
                    if(text.equals("stop")){
                        if (new String(dataTemp).equals(new String(data))){
                            System.out.println("Déconnecté du serveur.");
                            this.status = false;
                        }           
                    }
                    else {
                        if (new String(dataTemp).equals(new String(data))){
                            System.out.println("Message envoyé au serveur.");
                        }
                    }
                }
            }

        } catch (UnknownHostException e) {
        } catch (IOException e) {
        }
    }

    public void stop(){
        this.ds.close();
    }


}
