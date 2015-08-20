package models;

import com.avaje.ebean.Model;
import play.data.validation.Constraints;
import play.data.validation.Constraints.Required;

import javax.persistence.Id;

public class Comentario extends Model{

    @Id
    private Long id;

    @Required
    private Long idPessoa;

    @Required
    @Constraints.MaxLength(180)
    private String comentario;

    private Boolean ativo;

}
