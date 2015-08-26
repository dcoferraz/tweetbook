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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public Boolean getPublico() {
        return publico;
    }

    public void setPublico(Boolean publico) {
        this.publico = publico;
    }

    public Pessoa getCriador() {
        return criador;
    }

    public void setCriador(Pessoa criador) {
        this.criador = criador;
    }

    public List<Pessoa> getParticipantes() {
        return participantes;
    }

    public void setParticipantes(List<Pessoa> participantes) {
        this.participantes = participantes;
    }
}
