package models;

import com.avaje.ebean.Model;
import org.joda.time.DateTime;
import play.data.validation.Constraints.Required;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Amigo extends Model{

    @Id
    private Long id;
    private Long idPessoa;
    private Long idAmigo;

    private DateTime hora;

    @Required
    private Boolean ativo;
}
