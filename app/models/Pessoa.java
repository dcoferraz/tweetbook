package models;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.Model;
import com.avaje.ebean.SqlRow;
import com.avaje.ebean.SqlUpdate;
import com.fasterxml.jackson.annotation.JsonIgnore;
import play.data.validation.Constraints.Required;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"email"})})
public class Pessoa extends Model {

    @Id
    @Required
    private Long id;

    @Required
    @Column(length = 80, nullable = false)
    private String nome;

    @Required
    @Column(length = 50, nullable = false)
    private String email;

    @Required
    @Column(length = 30, nullable = false)
    private String senha;

    private String oauth_provider;
    private String oauth_id;

    private String urlImagem;

    @Column(length = 1, nullable = false)
    private String sexo;

    @Column(length = 80)
    private String cidade;

    @Column(length = 30)
    private String estado;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "amigo",
            joinColumns = {@JoinColumn(name = "idPessoa", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "idAmigo", referencedColumnName = "id")})
    private List<Pessoa> amigos;

    @JsonIgnore
    @OneToMany
    private List<Evento> eventosCriados;

    @ManyToMany
    @JoinTable(
            name = "evento_participantes",
            joinColumns = {@JoinColumn(name = "idParticipante", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "idEvento", referencedColumnName = "id")})
    private List<Evento> eventos;

    @JsonIgnore
    @OneToMany
    private List<Grupo> gruposCriados;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "grupo_participantes",
            joinColumns = {@JoinColumn(name = "idParticipante", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "idGrupo", referencedColumnName = "id")})
    private List<Grupo> grupos;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "posts_curtidas",
            joinColumns = {@JoinColumn(name = "idPessoa", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "idPost", referencedColumnName = "id")})
    private List<Pessoa> curtidas;

    @JsonIgnore
    @OneToMany
    private List<Post> postagens;

    @JsonIgnore
    @OneToMany
    private List<Comentario> comentarios;

    public Pessoa() {
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

    public List<Pessoa> getAmigos() {
        return amigos;
    }

    public void setAmigos(List<Pessoa> amigos) {
        this.amigos = amigos;
    }

    public List<Evento> getEventosCriados() {
        return eventosCriados;
    }

    public void setEventosCriados(List<Evento> eventosCriados) {
        this.eventosCriados = eventosCriados;
    }

    public List<Evento> getEventos() {
        return eventos;
    }

    public void setEventos(List<Evento> eventos) {
        this.eventos = eventos;
    }

    public List<Grupo> getGruposCriados() {
        return gruposCriados;
    }

    public void setGruposCriados(List<Grupo> gruposCriados) {
        this.gruposCriados = gruposCriados;
    }

    public List<Grupo> getGrupos() {
        return grupos;
    }

    public void setGrupos(List<Grupo> grupos) {
        this.grupos = grupos;
    }

    public List<Pessoa> getCurtidas() {
        return curtidas;
    }

    public void setCurtidas(List<Pessoa> curtidas) {
        this.curtidas = curtidas;
    }

    public List<Post> getPostagens() {
        return postagens;
    }

    public void setPostagens(List<Post> postagens) {
        this.postagens = postagens;
    }

    public List<Comentario> getComentarios() {
        return comentarios;
    }

    public void setComentarios(List<Comentario> comentarios) {
        this.comentarios = comentarios;
    }

    /* ACCESS TO DB */

    /**
     * authLogin authenticate the user with the DB
     *
     * @param email
     * @param senha
     * @return Long
     */
    public static Long authLogin(String email, String senha) {

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
                System.out.println("achou! Nome: " + p.getNome() + "| id : " + p.getId());
                return p.getId();
            }
        }
        return null;
    }

    /**
     * Authentication throug OAuth
     *
     * @param provider
     * @param id
     * @return Pessoa
     */
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

    public List<String> getAmigosId(){
        List<String>amigosId = new ArrayList<>();

        for(Pessoa amigo : this.getAmigos()) {
            amigosId.add(amigo.getId().toString());
        }

        return amigosId;
    }

}