package models;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.Model;
import play.data.validation.Constraints.Required;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.Constraint;

@Entity
public class Pessoa extends Model {


    @Id
    private Long id;

    @Basic
    @Required
    private String nome;

    @Basic
    @Required
    private String email;

    @Basic
    @Required
    private String senha;

    private String oauth_provider;
    private String oauth_id;

    @Required
    private String urlImagem;
    private String sexo;
    private String cidade;
    private String estado;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getUrlImagem() {
        return urlImagem;
    }

    public void setUrlImagem(String urlImagem) {
        this.urlImagem = urlImagem;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getOauth_provider() {
        return oauth_provider;
    }

    public void setOauth_provider(String oauth_provider) {
        this.oauth_provider = oauth_provider;
    }

    public String getOauth_id() {
        return oauth_id;
    }

    public void setOauth_id(String oauth_id) {
        this.oauth_id = oauth_id;
    }

    /* ACCESS TO DB */

    public Long authLogin(String email, String senha) {

        System.out.println("/*****Received*****/\n Login: " + email + "\n Senha: " + senha);

        // fluid API style with find()
        Pessoa p =
                Ebean.find(Pessoa.class)
                        .select("id")
                        .where().eq("email", email)
                        .eq("senha", senha)
                        .findUnique();

        if (p != null) {
            if (p.getId() != null) {
                System.out.println("achou! Nome: " + p.getNome());
                return p.getId();
            }
        }
        return null;
    }


    public Pessoa getById(Long id) {

        Pessoa p = Ebean.find(Pessoa.class)
                .where().eq("id", id)
                .findUnique();

        if (p != null) {
            if (p.getId() != null) {
                return p;
            }
        }

        return null;
    }

    public static Pessoa getByOAuth(String provider, String id) {

        Pessoa p = Ebean.find(Pessoa.class)
                .where()
                    .eq("oauth_provider", provider)
                    .eq("oauth_id", id)
                .findUnique();

        if (p != null) {
            if (p.getId() != null) {
                return p;
            }
        }

        return null;
    }


}