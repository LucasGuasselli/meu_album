
package meualbum;

import java.util.Scanner;
import javafx.scene.control.Alert;

/**
 *
 * @author Lucas Guasselli de Moraes
 * @version 1.0
 * @since 08/04/2017
 * 
 */
public class Digita {
    int limit;
    
    public boolean validaPorta(String textfield){
        int porta;
        
            porta = Integer.parseInt(textfield);

            if(porta > 0 || porta < 99999){
                return true;
            }else{
                return false;
            }       
    }//fecha Digita
    
    public String digita(String texto){
        Scanner ler = new Scanner(System.in);    
            System.out.print(texto);
                  return ler.nextLine();
            }//fecha Digita
    
    public String digitaRg(String textfield){
        String rg = "";
        limit = 10;           
                rg = textfield;                        
            if(rg.length() > limit || rg.length() <= 3){
                return null;
            }
        return rg;
    }//fecha Digita
    
    
    public boolean validaRg(String textfield){
        String rg = "";
        limit = 10;           
                rg = textfield;                        
            if(rg.length() > limit || rg.length() <= 3){
                return false;
            }
        return true;
    }//fecha validaRg
    
    public int digitaCodigo(String texto){
        Scanner ler = new Scanner(System.in);    
        int codigo;
            do{
                System.out.print(texto);
                   codigo = ler.nextInt();                         
            }while(codigo < 0 || codigo > 999);
                return codigo;
    }//fecha Digita
    
    public boolean validaCodigo(String textfield){
        int codigo;
        
            codigo = Integer.parseInt(textfield);

            if(codigo > 0 || codigo < 999){
                return true;
            }else{
                return false;
            }       
    }//fecha Digita
    
    public boolean validaAssento(String textfield){
        int numAssento;
        
            numAssento = Integer.parseInt(textfield);

            if(numAssento > 80 || numAssento < 200){
                return true;
            }else{
                return false;
            }       
    }//fecha Digita

    public String digitaNome(String texto) {
        Scanner ler = new Scanner(System.in);    
        String nome = "";
        limit = 30;            
            do{
                System.out.print(texto);
                   nome = ler.nextLine();                         
            }while(nome.length() > limit || nome.length() == 0);
                    nome = nome.toLowerCase();
                    nome = nome.substring(0,1).toUpperCase().concat(nome.substring(1));
                return nome;   
    }//fecha metodo

    public boolean validaNome(String textfield){
        String nome = "";
        limit = 30;            
            nome = textfield;
            if(nome.length() > limit || nome.length() == 1){
                return false;
            }
        return true;          
    }//fecha validaNome
    
    public String digitaData(String texto) {
        Scanner ler = new Scanner(System.in);    
        String data = "00/00/0000";
        limit = 10;            
            do{
                System.out.print(texto);
                    data = ler.nextLine();                         
            }while(data.length() > limit || data.length() < limit);
                return data;   
    }//fecha metodo
    
    public String digitaTelefone(String texto){
        Scanner ler = new Scanner(System.in);    
        String telefone = "(00)00000-0000";
        limit = 14;            
            do{
                System.out.print(texto);
                    telefone = ler.nextLine();                         
            }while(telefone.length() > limit || telefone.length() < limit);
                return telefone;  
    }//fecha metodo    
   
    public boolean validaTelefone(String textfield){
        String telefone = "(00)00000-0000";
        limit = 14;            
            telefone = textfield;
            if(telefone.length() > limit || telefone.length() <= 7){
                return false;
            }
        return true;          
    }//fecha validaTelefone
    
    public boolean validaQtdeAssentos(String textfield){
        int qtdeAssentos;
    
            qtdeAssentos = Integer.parseInt(textfield);

            if(qtdeAssentos > 80 || qtdeAssentos < 200){
                return true;
            }else{
                return false;
            }  //fecha if-else     
        
       
    }//fecha validaQtdeAssentos
    
}//fecha classe
