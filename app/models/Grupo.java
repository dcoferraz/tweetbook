package models;

import com.avaje.ebean.Model;
import play.data.validation.Constraints.Required;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Grupo extends Model{

    @Id
    private Long id;

    @Basic
    @Required
    private String nome;
    private String descricao;
    private String urlImagem;
    private Boolean ativo;
    private Boolean publico;
}
