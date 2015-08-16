package models;

import com.avaje.ebean.Model;
import org.joda.time.DateTime;
import play.data.validation.Constraints;
import play.data.validation.Constraints.Required;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Post extends Model{

    @Id
    private Long id;

    @Required
    private Long idUsuario;

    @Basic
    @Required
    @Constraints.MaxLength(180)
    private String conteudo;

    private String imageUrl;

    private DateTime hora;

    @Required
    private Boolean ativo;

}
