package controllers;

import models.Post;
import org.joda.time.DateTime;
import play.data.DynamicForm;
import play.data.Form;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;

import java.util.List;


public class Timeline extends Controller {

    int likeCounterTest = 0;

    public Result index() {

        System.out.println("INDEX TIMELINE");

        Post p = new Post();

        List<Post> lp = p.timelinePosts();

        /*for(Post post : lp){
            System.out.printf("Post id:" + post.getId().toString());
        }*/

        //timeline.render(lp)
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

            DateTime dt = DateTime.now();

            Long idPessoa = Long.parseLong(session().get("conectedId"));

            p.setAtivo(true);
            p.setConteudo(form.get("post"));
            p.setIdPessoa(idPessoa);
            p.setHora(dt);

            p.save();

            System.out.println("Criado um post em: " + dt);

            return redirect("/timeline");
        }

    }

    public Result like(String id) {
        String msg = "";

        if (likeCounterTest == 0) {
            likeCounterTest++;
            msg = "blue-text";
            //TODO: persist likes
        } else if (likeCounterTest == 1) {
            likeCounterTest--;
            msg = "remove";
            //TODO: persist dislikes
        }

        return ok(msg);
    }

    public Result addComment(String idPessoa,String comentario,String idPost) {
        String msg;

        if(idPessoa != null && comentario != null && idPost != null){
            msg = "ok";
            //TODO: persist comments
        }else{
            msg = "erro";
        }
        return ok(msg);
    }
}
