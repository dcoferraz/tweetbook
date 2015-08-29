package models;

import com.avaje.ebean.Model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import play.data.validation.Constraints.Required;

import javax.persistence.*;
import java.util.ArrayList;
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

    @OneToMany
    List<Post> postagens;

    public Grupo() {
        this.participantes = new ArrayList<>();
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

    public List<Post> getPostagens() {
        return postagens;
    }

    public List<Post> addPostagem(Post p)
    {
        this.postagens.add(p);
        return this.postagens;
    }

    public void setPostagens(List<Post> postagens) {
        this.postagens = postagens;
    }

    @JsonProperty("participantesId")
    public List<Long> getParticipantesId()
    {
        List<Long> partIds = new ArrayList<>();

        if (this.getParticipantes() != null) {
            for(Pessoa g : this.getParticipantes()) {
                partIds.add(g.getId());
            }
        }
        return partIds;
    }
}
