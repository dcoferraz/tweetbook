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

    public List<Post> timelinePosts() {

        List<Post> lp = Ebean.find(Post.class)
                .where().eq("ativo", true)
                .findList();

        for (Post p : lp) {
            System.out.println("Post id: " + p.getId().toString());
        }

        return lp;

    }

    public Boolean didHeLike(String idPessoa, Long idPost) {

        System.out.println("//parameters: ("+idPessoa+","+idPost+")");
        String sql = "select l.idPost, l.idPessoa from posts_curtidas l join post p on p.id = l.idPost where l.idPost = :id and l.idPessoa = :idPessoa";
        SqlRow bug = Ebean.createSqlQuery(sql)
                .setParameter("id", idPost)
                .setParameter("idPessoa", idPessoa)
                .findUnique();

        boolean alreadyLiked = false;
        if(bug != null && !bug.equals("")){
            String post = bug.getString("idPost") != null ? bug.getString("idPost") : "";
            String pessoa   = bug.getString("idPessoa");


            if(pessoa != null && pessoa != ""){//TODO: make appropriate check
                alreadyLiked = true;
            }
        }
        return alreadyLiked;
    }

    public List<Post> timelinePostsById(Long criadorId) {

        List<Post> lp = Ebean.find(Post.class)
                .where().eq("ativo", true)
                .eq("criador_id", criadorId)
                .findList();

        for (Post p : lp) {
            System.out.println("User: " + criadorId + " | Post id: " + p.getId().toString());
        }

        return lp;

    }
}
