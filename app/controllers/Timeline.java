package controllers;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.Expr;
import com.avaje.ebean.ExpressionList;
import com.avaje.ebean.annotation.Transactional;
import com.avaje.ebean.bean.EntityBean;
import com.sun.org.apache.xpath.internal.ExpressionNode;
import javafx.geometry.Pos;
import models.*;
import org.joda.time.DateTime;
import play.data.DynamicForm;
import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class Timeline extends Controller {

    /**
     * index function that render the timeline
     * @return redirect
     */
    public Result index() {
        Long idPessoa = Long.parseLong(session().get("conectedId"));
        Pessoa currentUser = Ebean.find(Pessoa.class, idPessoa);

        List<Post> lp = Ebean.find(Post.class)
                .where()
                .or(Expr.eq("criador", currentUser), Expr.in("criador", currentUser.getAmigos()))
                .order("postado_em DESC")
                .findList();

        return ok(timeline.render(lp, currentUser));
    }

    /**
     * newPost persists a Post
     * @return redirect
     */
    @Transactional
    public Result newPost() {


        DynamicForm form = Form.form().bindFromRequest();

        if (form.data().size() == 0 || form.get("post") == "") {

            return ok(error.render("No data for post... :("));

        } else {

            Post p = new Post();

            Date dt = DateTime.now().toDate();

            Long idPessoa = Long.parseLong(session().get("conectedId"));
            Pessoa currentUser = Ebean.find(Pessoa.class, idPessoa);
            Grupo grupoPost = null;
            if (form.get("post-grupo") != null) {
                grupoPost = Ebean.find(Grupo.class, Long.parseLong(form.get("post-grupo")));
            }

            p.setAtivo(true);
            p.setConteudo(form.get("post"));
            p.setPostadoEm(dt);
            p.setCriador(currentUser);

            if (grupoPost != null)
                p.setGrupo(grupoPost);

            p.save();


            return redirect("/timeline");
        }

    }

    /**
     * like persists a like to a post
     * @param idPost
     * @param idUser
     * @return redirect
     */
    @Transactional
    public Result like(String idPost, String idUser) {
        String msg;

        Long idPessoa = Long.parseLong(idUser);
        Long idPostAjax = Long.parseLong(idPost);

        Pessoa p1 = Ebean.find(Pessoa.class, idPessoa);
        Post postagem = Ebean.find(Post.class, idPostAjax);

        // if the user already liked
        if(postagem.getCurtidores().contains(p1)){
            // dislike
            postagem.getCurtidores().remove(p1);
            msg = "remove";
        } else {
            // like (if the user has not liked)
            postagem.getCurtidores().add(p1);
            msg = "blue-text";
        }

        // save the post to persist this like/dislike
        postagem.save();

        return ok(msg);
    }


    /**
     * get the comments for the ajax call
     *
     * @param idPost
     * @return template render
     */
    @Transactional
    public Result getComments(String idPost){

        Long _idPost = Long.parseLong(idPost);

        List<Comentario> listaComents = Ebean.find(Comentario.class)
                .where()
                    .eq("post_id", _idPost)
                    .eq("ativo", true)
                .findList();

        if (listaComents != null) {

            return ok(Json.toJson(listaComents));
        }

        return ok("erro");

    }

    /**
     * addComment persists a comment to a post
     * @param idPessoa
     * @param comentario
     * @param idPost
     * @return redirect
     */
    @Transactional
    public Result addComment(String idPessoa, String comentario, String idPost) {
        String msg = "erro";

        if (idPessoa != null && comentario != null && idPost != null) {

            Pessoa usuario = Ebean.find(Pessoa.class, Long.valueOf(idPessoa));
            if (usuario == null)
                return ok(msg);

            Post post = Ebean.find(Post.class, Long.valueOf(idPost));
            if (post == null)
                return ok(msg);

            Comentario c = new Comentario();
            c.setCriador(usuario);
            c.setTexto(comentario);
            c.setAtivo(true);

            c.setPost(post);

            c.save();

            msg = "ok";
        }
        return ok(msg);
    }

}
