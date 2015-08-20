package models;

import com.avaje.ebean.Model;
import play.data.validation.Constraints.Required;

import javax.persistence.*;
import java.util.List;

@Entity
public class Grupo extends Model{

    @Id
    @Required
    private Long id;

    @Required
    private String nome;
    @Required
    private Boolean ativo;
    @Required
    private Boolean publico;

    @ManyToOne
    @JoinColumn(nullable = false)
    Pessoa criador;

    @ManyToMany
    @JoinTable(
            name = "grupo_participantes",
            joinColumns = {@JoinColumn(name = "idGrupo", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "idParticipante", referencedColumnName = "id")}
    )
    List<Pessoa> participantes;

    public Grupo() {
    }


}
