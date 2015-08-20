package models;

import com.avaje.ebean.Model;
import org.joda.time.DateTime;
import play.data.validation.Constraints;
import play.data.validation.Constraints.Required;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class Evento extends Model{

    @Id
    private Long id;

    @Required
    @Column(length = 80, nullable = false)
    private String nome;

    @Lob
    private String descricao;

    @Required
    @Column(nullable = false)
    private Date data;
    private String local;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Pessoa criador;

    @ManyToMany
    @JoinTable(
            name = "evento_participantes",
            joinColumns = {@JoinColumn(name = "idEvento", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "idParticipante", referencedColumnName = "id")})
    private List<Evento> eventos;

    public Evento() {
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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public Pessoa getCriador() {
        return criador;
    }

    public void setCriador(Pessoa criador) {
        this.criador = criador;
    }

    public List<Evento> getEventos() {
        return eventos;
    }

    public void setEventos(List<Evento> eventos) {
        this.eventos = eventos;
    }
}
