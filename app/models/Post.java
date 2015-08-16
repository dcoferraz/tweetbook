package models;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.Model;
import org.joda.time.DateTime;
import play.data.validation.Constraints;
import play.data.validation.Constraints.Required;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.List;

@Entity
public class Post extends Model{

    @Id
    private Long id;

    @Required
    private Long idPessoa;

    @Basic
    @Required
    @Constraints.MaxLength(180)
    private String conteudo;

    private String imageUrl;

    private DateTime hora;

    @Required
    private Boolean ativo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdPessoa() {
        return idPessoa;
    }

    public void setIdPessoa(Long idPessoa) {
        this.idPessoa = idPessoa;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public DateTime getHora() {
        return hora;
    }

    public void setHora(DateTime hora) {
        this.hora = hora;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public List<Post> timelinePosts() {

        List<Post> lp = Ebean.find(Post.class)
                             .where().eq("ativo", true)
                             .findList();

        for(Post p : lp){
            System.out.println("Post id: " + p.getId().toString());
        }

        return lp;


    }
}
