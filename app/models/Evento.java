package models;

import com.avaje.ebean.Model;
import org.joda.time.DateTime;
import play.data.validation.Constraints;
import play.data.validation.Constraints.Required;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Evento extends Model{

    @Id
    private Long id;

    @Required
    private Long idUsuario;

    private String nome;
    private String descricao;
    private String cidade;
    private String estado;

}
