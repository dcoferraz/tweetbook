package models;

import com.avaje.ebean.Model;
import play.data.validation.Constraints.Required;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Pessoa extends Model{

    @Id
    private Long id;

    @Basic
    @Required
    private String nome;

    @Required
    private String urlImagem;
    private String sexo;
    private String cidade;
    private String estado;

}