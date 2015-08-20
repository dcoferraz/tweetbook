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

    @Transactional
    public Result like(String idPost, String idUser) {
        String msg;

        System.out.println("//5 idUser like(): "+idUser);

        Long idPessoa = Long.parseLong(idUser);
        Long idPostAjax = Long.parseLong(idPost);

        System.out.println("6// idPessoa: "+idPessoa);

        Pessoa p1 = Pessoa.getById(idPessoa);
        Pessoa p2 = new Pessoa();

        boolean allreadyLike = false;
        for(Pessoa p : p1.getCurtidas()){
            if (p.getId().equals(idPostAjax)) {
                allreadyLike = true;
                break;
            }
        }

        System.out.println("7// allreadyLiked: "+allreadyLike);
        System.out.println("8// idPost: "+idPostAjax);



        p2.setId(idPostAjax);

        if(allreadyLike){
            msg = "remove";


            System.out.println("9// remove");
            //TODO: remove likes
            p1.getCurtidas().remove(p2);
            p1.save();
            p2 = null;

        } else {
            msg = "blue-text";
            System.out.println("9// add");
            //TODO: persist likes
            p1.getCurtidas().add(p2);
            p1.save();
            p2 = null;
        }

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
