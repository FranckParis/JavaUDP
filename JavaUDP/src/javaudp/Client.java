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
        Scanner reader = new Scanner(System.in);
        String text;
        
        byte[] data = (new String("hello serveur RX302")).getBytes();
        byte[] host = { (byte)134, (byte)214, (byte)117, (byte)127};
        byte[] dataTemp = data;
        
        try {
            //InetAddress ias = InetAddress.getByAddress(host);
            InetAddress ias = InetAddress.getLocalHost();
            this.dp = new DatagramPacket(data, data.length, ias, this.port);
            this.ds.send(dp);
            data = new byte[data.length];
            this.dp = new DatagramPacket(data, data.length, ias, this.port);

            this.ds.receive(this.dp);

            if (new String(data, 0, 19).equals("Server RX302 ready")){
                port = this.dp.getPort();
                System.out.println(new String(data));
            }
            
            // Running 
            
            while (status){
                if(reader.hasNextLine()){
                    text = reader.nextLine();
                    data = text.getBytes();
                    dataTemp = data;
                    
                    this.dp = new DatagramPacket(data, data.length, ias, this.port);
                    this.ds.send(dp);
                    
                    data = new byte[data.length];
                    this.dp = new DatagramPacket(data, data.length, ias, this.port);
                    
                    this.ds.receive(this.dp);

                    //Stop thread
                    if(text.equals(new String("stop"))){
                        if (new String(dataTemp).equals(new String(data))){
                            System.out.println(new String("Deconnecte"));
                        }
                        this.status = false;
                    }
                    else {
                        if (new String(dataTemp).equals(new String(data))){
                            System.out.println(new String("Message envoye au serveur"));
                        }
                    }
                }
            }

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stop(){
        this.ds.close();
    }


}
