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
public class Figurinha implements Serializable{
    
    private int codigo;
    private String nome;
    private String descricao;
    
    
    public Figurinha(int Codigo, String nome, String descricao){
        this.nome = nome;
        this.descricao = descricao;
    }
    
    public void setNome(String nome){
        this.nome = nome;
    }
    public void setDescricao(String descricao){
        this.descricao = descricao;
    }
    public void setCodigo(int codigo){
        this.codigo = codigo;
    }
    
    public String getNome(){
        return this.nome;
    }
    public String getDescricao(){
        return this.descricao;
    }
    public int getCodigo(){
        return this.codigo;
    }
    
    public Figurinha retornaFigurinha(){
        return new Figurinha(getCodigo(),getNome(),getDescricao());
    }
    
    public String toString(){
        return "\nCodigo da figurinha: " + getCodigo()
             + "\nNome: " + getNome()
             + "\nDescricao:" + getDescricao() + "\n";
    }
    
}//fecha classe
