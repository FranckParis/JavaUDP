/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package javaudp;

/**
 *
 * @author p1303175
 */
public class MainClient {
    
    public static void main(String[] args) {
        Client cli = new Client();
        cli.run();
        cli.stop();
    }
}
