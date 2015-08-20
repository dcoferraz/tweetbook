package controllers;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.annotation.Transactional;
import javafx.geometry.Pos;
import models.Comentario;
import models.Pessoa;
import models.Post;
import org.joda.time.DateTime;
import play.data.DynamicForm;
import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;

import java.util.Date;
import java.util.List;


public class Timeline extends Controller {

    /**
     * index function that render the timeline
     * @return redirect
     */
    public Result index() {
        Post p = new Post();
        List<Post> lp = p.timelinePosts();

        return ok(timeline.render(lp));
    }

    /**
     * newPost persists a Post
     * @return redirect
     */
    @Transactional
    public Result newPost() {

        System.out.println("newPost");

        DynamicForm form = Form.form().bindFromRequest();

        if (form.data().size() == 0 || form.get("post") == "") {

            return ok(error.render("No data for post... :("));

        } else {

            Post p = new Post();

            Date dt = DateTime.now().toDate();

            Long idPessoa = Long.parseLong(session().get("conectedId"));
            Pessoa currentUser = Pessoa.getById(idPessoa);

            p.setAtivo(true);
            p.setConteudo(form.get("post"));
            p.setPostadoEm(dt);
            p.setCriador(currentUser);

            p.save();

            System.out.println("Criado um post em: " + dt);

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

        System.out.println("//5 idUser like(): "+idUser);

        Long idPessoa = Long.parseLong(idUser);
        Long idPostAjax = Long.parseLong(idPost);

        System.out.println("6// idPessoa: "+idPessoa);

        Pessoa p1 = Pessoa.getById(idPessoa);

        if(p1.didHeLike(idPessoa,idPostAjax)){
            msg = "remove";

            System.out.println("9// remove");
            //TODO: remove likes
            p1.removeLike(idPostAjax, idPessoa);

        } else {
            msg = "blue-text";
            System.out.println("9// add");
            //TODO: persist likes

            Pessoa p2 = new Pessoa();
            p2.setId(idPostAjax);
            p1.getCurtidas().add(p2);
            p1.save();
        }

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
