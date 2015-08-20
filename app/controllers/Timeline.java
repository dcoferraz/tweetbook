package controllers;

import com.avaje.ebean.annotation.Transactional;
import models.Pessoa;
import models.Post;
import org.joda.time.DateTime;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;

import java.util.Date;
import java.util.List;


public class Timeline extends Controller {

    public Result index() {
        Post p = new Post();
        List<Post> lp = p.timelinePosts();

        return ok(timeline.render(lp));
    }

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

    public Result like(String id) {
        String msg;

        //Long idPessoa = Long.parseLong(session().get("currentId"));
        //Pessoa p = Pessoa.getById(idPessoa);

        msg = "blue-text";
        //TODO: persist likes

        //TODO: persist dislikes, msg = "remove";

        return ok(msg);
    }

    public Result addComment(String idPessoa, String comentario, String idPost) {
        String msg;

        if (idPessoa != null && comentario != null && idPost != null) {
            msg = "ok";
            //TODO: persist comments
        } else {
            msg = "erro";
        }
        return ok(msg);
    }
}
