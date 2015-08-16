package models;

import com.avaje.ebean.Model;
import play.data.validation.Constraints;
import play.data.validation.Constraints.Required;

import javax.persistence.Id;

public class Comentario extends Model{

    @Id
    private Long id;

    @Required
    private Long idUsuario;

    @Required
    @Constraints.MaxLength(180)
    private String comentario;

    @Required
    private String email;
    private String cidade;
    private String estado;
    private Boolean ativo;

}
