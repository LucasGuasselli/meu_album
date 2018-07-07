package meualbum;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.Socket;
import java.util.ArrayList;
import javax.swing.*;

/**
 *
 * @author Lucas Guasselli
 * @since 22/07/2018
 */
public class Colecionador extends JFrame implements ActionListener, KeyListener {

    //private static final long serialVersionUID = 1L;
    private JTextArea jtaDashboard;
    private JTextField jtfMsg;
    private JButton btnEnviar;
    private JButton btnSair;
    private JLabel lbChat;
    private JLabel lbMsg;
    private JPanel pnlPainel;
    private Socket socket;
    private OutputStream outputStream;
    private Writer writer;
    private BufferedWriter bufferWriter;
    
    private JTextField jtfFigDesej;
    private JTextField jtfFigDisp;
    private JTextField jtfNome;

    
    private ArrayList<String> arrayFigDisp = new ArrayList();
    private ArrayList<String> arrayFigDesej = new ArrayList();

    //construtor
    public Colecionador() throws IOException {
        //janela de interacao com o usuario

        JLabel lblMessage = new JLabel("Informe seu nome!");
        jtfNome = new JTextField("Colecionador");
        jtfFigDesej = new JTextField("");
        //jtfPorta = new JTextField("6789");
       
        Object[] objeto = {lblMessage, jtfNome,jtfFigDesej};
        JOptionPane.showMessageDialog(null, objeto);
        
        //separando aas cartas do usuario (desejadas e disponiveis
        String arrayTempDisp[] = jtfFigDisp.getText().split(",");
        String arrayTempDesej[] = jtfFigDesej.getText().split(",");
         
        for(int i = 0; i<arrayTempDisp.length; i++){
                   arrayFigDisp.add(arrayTempDisp[i]);
                   //System.out.println(arrayFigDisp.get(i));
        }       
        for(int i = 0; i<arrayTempDesej.length; i++){
                   arrayFigDesej.add(arrayTempDesej[i]);
                   //System.out.println(arrayFigDesej.get(i));
        }
        
        //metodo que monta a user interface
        montarPainel();        
    }//fecha construtor

    /**
     * *
     * Método usado para conectar no server socket
     */
    public void conectar() throws IOException {
        jtaDashboard.append("olá, já trocou figurinhas hoje!? \r\n");
        //jtaDashboard.append("Menu: \r\n");
        //jtaDashboard.append("Digite  \r\n");

        socket = new Socket("127.0.0.1", 6789);
        outputStream = socket.getOutputStream();
        writer = new OutputStreamWriter(outputStream);
        bufferWriter = new BufferedWriter(writer);
        bufferWriter.write(jtfNome.getText() + "\r\n");        
        
        bufferWriter.flush();
    }//fecha conectar

    /**
     * Método usado para enviar mensagem para o server socket
     * @param recebe uma String mensagem que o usuario digita
     */
    public void enviarMensagem(String msg) throws IOException {

        if (msg.equals("Sair")) {
            bufferWriter.write("Desconectado \r\n");
            jtaDashboard.append("Desconectado \r\n");
        }else if(msg.equals("1")){
                jtaDashboard.append("Eu: minhas figurinhas sao: " );
                bufferWriter.write("Minhas figurinhas sao: ");
           for(int i = 0; i<arrayFigDisp.size(); i++){
                bufferWriter.write(arrayFigDisp.get(i));
                
                jtaDashboard.append(arrayFigDisp.get(i));      
            }//fecha loop
            
        }else if(msg.equals("2")){
                
          
        }else {
            bufferWriter.write(msg + "\r\n");
            jtaDashboard.append("Eu: " + jtfMsg.getText() + "\r\n");
        }      
        bufferWriter.flush();
        //parte do codigo que limpa a barra de digitacao do usuario
        jtfMsg.setText("");
    }//fecha conectar

    /**
     * Método usado para receber mensagem do servidor
     */
    public void escutar() throws IOException {

        InputStream in = socket.getInputStream();       
        InputStreamReader inr = new InputStreamReader(in);
        BufferedReader bfr = new BufferedReader(inr);       
       
        String msg = "";

        while (!"Sair".equalsIgnoreCase(msg)) {

            if (bfr.ready()) {
                msg = bfr.readLine();
                if (msg.equals("Sair")) {
                    jtaDashboard.append("Servidor caiu! \r\n");
                }else {
                    jtaDashboard.append(msg + "\r\n");
                }
            }
        }
    }//fecha metodo ouvir

    //nao está senod usado
    public void trocarFigurinha(int codigo) throws IOException {

        InputStream in = socket.getInputStream();       
        InputStreamReader inr = new InputStreamReader(in);
        BufferedReader bfr = new BufferedReader(inr);       
       
        String msg = "";
        msg = bfr.readLine();       
    }

    //metodo que monta a User Interface
    public void montarPainel(){
        pnlPainel = new JPanel();
        lbChat = new JLabel("CHAT");
            jtaDashboard = new JTextArea(20, 40);
            jtaDashboard.setEditable(false);
            jtaDashboard.setBackground(new Color(240, 240, 240));
        
        jtfMsg = new JTextField(20);
        lbMsg = new JLabel("Mensagem");
            btnEnviar = new JButton("Enviar");
            btnEnviar.setToolTipText("Enviar Mensagem");
            btnSair = new JButton("Sair");
            btnSair.setToolTipText("Sair do Chat");
            btnEnviar.addActionListener(this);
            btnSair.addActionListener(this);
            btnEnviar.addKeyListener(this);
        jtfMsg.addKeyListener(this);
        JScrollPane scroll = new JScrollPane(jtaDashboard);
            pnlPainel.add(lbChat);
        jtaDashboard.setLineWrap(true);
            pnlPainel.add(scroll);        
            pnlPainel.add(lbMsg);
            pnlPainel.add(jtfMsg);
            pnlPainel.add(btnEnviar);
            pnlPainel.add(btnSair);
        
        jtaDashboard.setBorder(BorderFactory.createEtchedBorder(Color.BLACK, Color.BLACK));
        jtfMsg.setBorder(BorderFactory.createEtchedBorder(Color.BLACK, Color.BLACK));
        
        setTitle(jtfNome.getText());
        setContentPane(pnlPainel);
        setLocationRelativeTo(null);
        setResizable(false);
        setSize(490, 430);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    
    public void sair() throws IOException {
        System.exit(0);
        /*enviarMensagem("Sair");
        bfw.close();
        ouw.close();
        ou.close();
        socket.close();
                */
    }

    //@param evento disparado
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getActionCommand().equals(btnEnviar.getActionCommand())) {
                enviarMensagem(jtfMsg.getText());
            } else if (e.getActionCommand().equals(btnSair.getActionCommand())) {
                sair();
            }
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
    }

    //@param recebe evento de chave pressionada
    //usado quando a tecla enter e pressionada
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            try {
                enviarMensagem(jtfMsg.getText());
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    
    @Override
    public void keyReleased(KeyEvent arg0) {
        // TODO Auto-generated method stub               
    }

    @Override
    public void keyTyped(KeyEvent arg0) {
        // TODO Auto-generated method stub               
    }

    public static void main(String[] args) throws IOException {

        Colecionador col = new Colecionador();
        col.conectar();
        col.escutar();
    }//fcha main
}//fecha classe
