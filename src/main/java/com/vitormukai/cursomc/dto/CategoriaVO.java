package com.vitormukai.cursomc.dto;

import com.vitormukai.cursomc.domain.Categoria;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

public class CategoriaVO implements Serializable {
    private static final long serialVersionUID = 1L;

    public Integer id;

    @NotEmpty(message = "Preenchimento obrigatório")
    @Length(min = 5, max = 80, message = "Deve ter entre 5 e 80 caracteres")
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
