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
    private Socket socket = null;
    private OutputStream outputStream;
    private Writer writer;
    private BufferedWriter bufferWriter;
    
    private JTextField jtfFigDesej;
    private JTextField jtfFigDisp;
    private JTextField jtfNome;
    private JLabel lblMessage;
    private JLabel lblDisp;
    private JLabel lblDesej;

    //array que armazena as figurinhas disponíveis
    private ArrayList<String> arrayFigDisp = new ArrayList();

    //array que armazena as figurinhas desejáveis
    private ArrayList<String> arrayFigDesej = new ArrayList();

    /**
     * Construtor da class Colec
     * @throws IOException
     */
    public Colecionador() throws IOException {
        //janela de interacao com o usuario

        lblMessage = new JLabel("Informe seu nome:");
        jtfNome = new JTextField("Colecionador");
        lblDisp = new JLabel("Figurinhas disponiveis:");
        jtfFigDisp = new JTextField("");
        lblDesej = new JLabel("Figurinhas desejaveis:");
        jtfFigDesej = new JTextField("");
       
        Object[] objeto = {lblMessage, jtfNome, lblDisp, jtfFigDisp, lblDesej, jtfFigDesej};
        JOptionPane.showMessageDialog(null, objeto);
        
        //separando as cartas do usuario (desejadas e disponiveis)
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
     * @throws java.io.IOException
     */
    public void conectar() throws IOException {
        jtaDashboard.append("Olá, você já trocou suas figurinhas hoje? \r\n");

        socket = new Socket("localhost", 6789);
        outputStream = socket.getOutputStream();
        writer = new OutputStreamWriter(outputStream);
        bufferWriter = new BufferedWriter(writer);
        bufferWriter.write(jtfNome.getText() + "\r\n");        
        
        bufferWriter.flush();
    }//fecha conectar

    /**
     * Método usado para enviar mensagem para o server socket
     * @param msg
     * @throws java.io.IOException
     */
    public void enviarMensagem(String msg) throws IOException {

        switch (msg) {
            case "Sair":
                bufferWriter.write("Desconectado \r\n");
                jtaDashboard.append("Desconectado \r\n");
                break;
            case "troca":
                jtaDashboard.append("Bot: troca \r\n");
                jtaDashboard.append("Eu: Minhas figurinhas para troca são: \r\n" );
                
                // For que lê o array de figurinhas disponíveis
                jtaDashboard.append("Bot: ");
                for(int i = 0; i<arrayFigDisp.size(); i++){
                    bufferWriter.write(arrayFigDisp.get(i));
                    jtaDashboard.append('[' + arrayFigDisp.get(i) + ']');
                }
                jtaDashboard.append("\r\n");
                break;
            case "preciso":
                jtaDashboard.append("Bot: preciso \r\n");
                jtaDashboard.append("Eu: Figurinhas que necessito: \r\n" );
                
                // For que lê o array de figurinhas disponíveis
                jtaDashboard.append("Bot: ");
                for(int i = 0; i<arrayFigDesej.size(); i++){
                    bufferWriter.write(arrayFigDesej.get(i));
                    jtaDashboard.append('[' + arrayFigDesej.get(i) + ']');
                }
                jtaDashboard.append("\r\n");
                break;
            default:
                bufferWriter.write(msg + "\r\n");
                jtaDashboard.append("Eu: " + jtfMsg.getText() + "\r\n");
                break;
        }      
        bufferWriter.flush();
        //parte do codigo que limpa a barra de digitacao do usuario
        jtfMsg.setText("");
    }//fecha conectar

    /**
     * Método usado para receber mensagem do servidor
     * @throws java.io.IOException
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

    //nao está sendo usado agora
    /**
     * Método de troca de figurinha
     * @param codigo
     * @throws IOException
     */
    public void trocarFigurinha(int codigo) throws IOException {

        InputStream in = socket.getInputStream();       
        InputStreamReader inr = new InputStreamReader(in);
        BufferedReader bfr = new BufferedReader(inr);       
       
        String msg = "";
        msg = bfr.readLine();       
    }

    /**
     * metodo que monta a User Interface
     */
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

    /**
     * Usado quando a tecla enter e pressionada
     * @param e
     */
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
    
    /**
     * Main da class Colecionador
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {

        Colecionador col;
            col = new Colecionador();
        col.conectar();
        col.escutar();
    }

    /**
     * Método não utilizado
     * @param arg0
     */
    @Override
    public void keyReleased(KeyEvent arg0) {
        // TODO Auto-generated method stub               
    }

    /**
     * Método não utilizado
     * @param arg0
     */
    @Override
    public void keyTyped(KeyEvent arg0) {
        // TODO Auto-generated method stub               
    }
}//fecha classe
