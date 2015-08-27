package models;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.Model;
import com.avaje.ebean.SqlRow;
import org.joda.time.DateTime;
import play.data.validation.Constraints;
import play.data.validation.Constraints.Required;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class Post extends Model {

    @Id
    private Long id;

    @Required
    @Constraints.MaxLength(180)
    @Column(length = 180, nullable = false)
    private String conteudo;

    @Required
    @Column(nullable = false)
    private Date postadoEm;

    @Required
    @Column(nullable = false)
    private Boolean ativo;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Pessoa criador;

    @ManyToMany
    @JoinTable(
            name = "posts_curtidas",
            joinColumns = {@JoinColumn(name = "idPost", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "idPessoa", referencedColumnName = "id")})
    private List<Pessoa> curtidores;

    @OneToMany
    private List<Comentario> comentarios;

    public Post() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public Date getPostadoEm() {
        return postadoEm;
    }

    public void setPostadoEm(Date postadoEm) {
        this.postadoEm = postadoEm;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public List<Pessoa> getCurtidores() {
        return curtidores;
    }

    public void setCurtidores(List<Pessoa> curtidores) {
        this.curtidores = curtidores;
    }

    public Pessoa getCriador() {
        return criador;
    }

    public void setCriador(Pessoa criador) {
        this.criador = criador;
    }

    public List<Comentario> getComentarios() {
        return comentarios;
    }

    public void setComentarios(List<Comentario> comentarios) {
        this.comentarios = comentarios;
    }

    /**
     * Check if a user liked this post.
     * @param idPessoa  user id
     * @return Returns true if user has liked. Otherwise, returns false.
     */
    public Boolean hasLiked(String idPessoa)
    {
        // for each user who likes
        for(Pessoa c : this.getCurtidores())
        {
            if (c.getId().toString().equals(idPessoa))
                return true;
        }

        return false;
    }
}
