/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author halim
 */
class ChatHandeler extends Thread{
    
    DataInputStream dis;
    PrintStream ps;
    BufferedReader bufferedReader=null;
    File file = new File("sample.txt");
    
    static Vector<ChatHandeler> clientVector = new Vector<ChatHandeler>();
    Socket toCloseSocect;
    public ChatHandeler(Socket is) {
        try {
            toCloseSocect=is;
            dis = new DataInputStream(is.getInputStream());
            ps = new PrintStream(is.getOutputStream());
            bufferedReader = new BufferedReader(new FileReader(file));
            String st;
            while ((st = bufferedReader.readLine())!=null) {                
                ps.println(st);
            }
            clientVector.add(this);
            start();
        } catch (IOException ex) {
            Logger.getLogger(ChatHandeler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void run(){
        try {
            while (true) {            
            String str;
            str = dis.readLine();
            sendMessageToAll(str);
            }
        }catch (Exception e) {
        }
        finally{
            try {
                ps.close();
                dis.close();
                toCloseSocect.close();
            } catch (IOException ex) {
                Logger.getLogger(ChatHandeler.class.getName()).log(Level.SEVERE, null, ex);
            }
                
        }
    }

    public void sendMessageToAll(String str) {
        FileWriter fileWriter = null;
        PrintWriter printWriter = null;
        BufferedReader bufferedReader=null;
        try{
            //Opening a file in append mode using FileWriter
            fileWriter = new FileWriter("sample.txt", true);
            //Wrapping BufferedWriter object in PrintWriter
            printWriter = new PrintWriter(fileWriter);
            //Bringing cursor to next line
            printWriter.println();
            //Writing text to file
            for (ChatHandeler ch : clientVector) {
                if(!str.isEmpty()){
                    ch.ps.println(str);
                    printWriter.println(str);
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }finally{ //Closing the resources
            try{
                printWriter.close();
                fileWriter.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        

    }
    
}
