/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package javaudp;
import java.net.*;

/**
 *
 * @author p1303175
 */
public class ScannerDePorts {
    
    public static int scan(int min, int max){
        for(int i=min;i<max;i++){
            try{
                DatagramSocket cli = new DatagramSocket(i);
                cli.close();
                return i;
            }
            catch (SocketException ex) {
            }
        }
        return -1;
    }
}
