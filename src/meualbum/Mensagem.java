/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package meualbum;

import java.io.Serializable;

/**
 *
 * @author Lucas Guasselli
 * @since 22/07/2018
 */
public class Mensagem implements Serializable{
    
    private String remetente;
    private String destino;
    private int codigo;
    private Figurinha figurinha;
    
    public Mensagem(String destino, Figurinha figurinha){
        this.destino = destino;
        this.figurinha = figurinha;        
        
    }
    
     
    

    public String getRemetente() {
        return remetente;
    }

    public void setRemetente(String remetente) {
        this.remetente = remetente;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public Figurinha getFigurinha() {
        return figurinha;
    }

    public void setFigurinha(Figurinha figurinha) {
        this.figurinha = figurinha;
    }
    
    
}
