/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package javaudp;

import java.net.DatagramPacket;

/**
 *
 * @author p1303175
 */
public class MainServer {
    
    public static void main(String[] args) {
        Server srv = new Server(3142);
        srv.run();
        srv.stop();
    }
    
}
