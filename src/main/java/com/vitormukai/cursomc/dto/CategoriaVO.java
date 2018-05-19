package com.vitormukai.cursomc.dto;

import com.vitormukai.cursomc.domain.Categoria;

import java.io.Serializable;

public class CategoriaVO implements Serializable {
    private static final long serialVersionUID = 1L;

    public Integer id;
    public String nome;

    public CategoriaVO(){

    }

    public CategoriaVO(Categoria obj){
        id = obj.getId();
        nome = obj.getNome();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
