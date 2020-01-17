/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author halim
 */
public class ChatServer {

    ServerSocket ss;
    Socket is;

    public ChatServer() {
        try {
            ss = new ServerSocket(5005);
            while (true) {
                is = ss.accept();
                new ChatHandeler(is);
            }
        } catch (IOException ex) {
            Logger.getLogger(ChatServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        new ChatServer();
    }

}
