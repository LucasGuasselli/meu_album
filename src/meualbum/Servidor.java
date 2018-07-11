package meualbum;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author Lucas Guasselli
 * @since 22/07/2018
 */
public class Servidor extends Thread {

    private static ArrayList<BufferedWriter> colecionadores;
    private static ServerSocket servidor;
    private String nome;
    private Socket listenSocket;
    private InputStream inputSream;
    private InputStreamReader inputStreamReader;
    private BufferedReader bufferReader;
    
    //construtos com @param do tipo Socket    
    public Servidor(Socket socket) {
        this.listenSocket = socket;
        try {
            inputSream = socket.getInputStream();
            inputStreamReader = new InputStreamReader(inputSream);
            bufferReader = new BufferedReader(inputStreamReader);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        try {            
            servidor = new ServerSocket(6789);
            colecionadores = new ArrayList<BufferedWriter>();
            JOptionPane.showMessageDialog(null, "Servidor conectado! ");

            while (true) {
                System.out.println("Aguardando conexão...");
                Socket con = servidor.accept();
                //Socket con = new Socket("localhost", 6789);
                System.out.println("Colecionador conectado...");
                Thread t = new Servidor(con);
                t.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }// Fecha main             
   
    public void run() {
        try {
            String msg;
            OutputStream ou = this.listenSocket.getOutputStream();
            Writer ouw = new OutputStreamWriter(ou);
            BufferedWriter bfw = new BufferedWriter(ouw);
            colecionadores.add(bfw);
            
            nome = msg = bufferReader.readLine();
            System.out.println(nome);
                              
            while (!"Sair".equalsIgnoreCase(msg) && msg != null) {
                msg = bufferReader.readLine();
                sendToAll(bfw, msg);                
            }            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//fecha run

    /*@param buffer e string
     * Método usado para enviar mensagem para todos os clients
     */
    public void sendToAll(BufferedWriter bwSaida, String msg) throws IOException {
        BufferedWriter bwS;

        for (BufferedWriter bfw : colecionadores) {
            bwS = (BufferedWriter) bfw;
            if (!(bwSaida == bwS)) {
                bfw.write(nome + " diz: " + msg + "\r\n");
                bfw.flush();
            }
        }
    }
}//fecha classe
