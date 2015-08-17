package models;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.Model;
import play.data.validation.Constraints.Required;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Pessoa extends Model {


    @Id
    private Long id;

    @Basic
    @Required
    private String nome;

    @Basic
    @Required
    private String senha;

    @Required
    private String token;

    @Required
    private String secret;

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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

/* ACCESS TO DB */

    public Long authLogin(String nome, String senha) {

        System.out.println("/*****Received*****/\n Nome: " + nome + "\n Senha: " + senha);

        // fluid API style with find()
        Pessoa p =
                Ebean.find(Pessoa.class)
                        .select("id")
                        .where().eq("nome", nome)
                        .eq("senha", senha)
                        .findUnique();

        if (p != null) {
            if (p.getId() != null) {
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

}