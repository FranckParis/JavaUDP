package javaudp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

/**
 * @author Francky
 */
public class Communication implements Runnable {

    private DatagramSocket socket;
    private DatagramPacket packet;
    private InetAddress address;
    private int port;
    private boolean status;

    public Communication(InetAddress address, int port){
        try {
            this.address = address;
            this.port = port;
            this.socket = new DatagramSocket();
            this.packet = new DatagramPacket(new byte[128], 128);
            this.status = true;
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        byte[] data;

        System.out.println("Nouveau client : " + this.address + ":" + this.port);

        data = (new String("serveur RX302 ready")).getBytes();
        this.packet = new DatagramPacket(data, data.length, this.address, this.port);
        
        try {
            
            this.socket.send(this.packet);
        } catch (IOException e) {
        }
        
        data = new byte[data.length];
        this.packet = new DatagramPacket(data, data.length, this.address, this.port);

        while(status){
            try {
                System.out.println("En attente d'un message.");
                
                data = new byte[data.length];
                this.packet = new DatagramPacket(data, data.length, this.address, this.port);
                this.socket.receive(this.packet);

                System.out.println("Message de " + this.address + " : " + this.port);
                
                data = this.packet.getData();
                this.socket.send(this.packet);

                if (new String(data, 0, 4).equals("stop")){
                    System.out.println("Deconnection du client : " + this.address + ":" + this.port);
                    this.status = false;
                }
                else System.out.println(new String(data));
            } catch (IOException e) {
            }
        }
    }
}
