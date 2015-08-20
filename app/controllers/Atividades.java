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


public class Atividades extends Controller {

    /**
     * index renders atividades template
     * @return redirect
     */
    public Result index() {

        Post p = new Post();
        Long idPessoa = Long.parseLong(session().get("conectedId"));
        List<Post> lp = p.timelinePostsById(idPessoa);

        return ok(atividades.render(lp));
    }
}
