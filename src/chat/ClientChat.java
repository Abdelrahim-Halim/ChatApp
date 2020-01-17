/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author halim
 */
public class ClientChat {
    
    Socket clientSocket;
    DataInputStream dis ;
    PrintStream ps;
    ChatGUI ui;
    
    public ClientChat(){
        try {
            //start connections
            clientSocket = new Socket("127.0.0.1", 5005);
            dis = new DataInputStream(clientSocket.getInputStream());
            ps = new PrintStream(clientSocket.getOutputStream());
            
            //creatGUI
            createGui();
            
            ui.getButton().addActionListener(new ActionListener() {
                
                @Override
                public void actionPerformed(ActionEvent ae) {
                    String msg = ui.getTextField().getText();
                    ps.println(msg);
                    ui.getTextField().setText("");
                    //ui.getTextArea().append(msg);
                }
            });
            
            new Thread(new Runnable() {

                @Override
                public void run() {
                    while (true) {                        
                        try {
                            String msg = dis.readLine();
                            if(!msg.isEmpty()){
                                ui.getTextArea().append(msg + "\n");
                            }
                        } catch (IOException ex) {
                            Logger.getLogger(ClientChat.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        
                    }
                }
            }).start();
            
            
        } catch (IOException ex) {
            Logger.getLogger(ClientChat.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void createGui(){
        ui=new ChatGUI();
        ui.setSize(400, 500);
        ui.setVisible(true);
    }
    public static void main(String[] args) {
        // TODO code application logic here
        new ClientChat();
    }
}

