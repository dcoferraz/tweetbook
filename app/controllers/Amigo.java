package controllers;

import com.avaje.ebean.Ebean;
import play.data.DynamicForm;
import play.mvc.*;
import play.db.jpa.*;
import views.html.*;
import models.Pessoa;
import play.data.Form;
import java.util.List;

import static play.libs.Json.*;

public class Amigo extends Controller {

    /**
     * index redirects to amigo template
     * @return redirect
     */
    public Result index() {

        Long idPessoa = Long.parseLong(session().get("conectedId"));

        List<Pessoa> pessoas = Ebean.find(Pessoa.class)
                                .where().ne("id", idPessoa)
                                .findList();

        return ok(amigo.render(pessoas));
    }

    /**
     * addAmigo persists a friendship relation to the DB
     * @param idUser
     * @param idAmigo
     * @return redirect
     */
    public Result addAmigo(String idUser, String idAmigo){

        String msg = "ok";

        if (idUser != null && idAmigo != null) {
            // search if they are friends

            Long idPessoa1 = Long.parseLong(idUser);
            Pessoa p1 = Pessoa.getById(idPessoa1);

            Long idPessoa2 = Long.parseLong(idAmigo);
            Pessoa p2 = Pessoa.getById(idPessoa2);

            boolean alreadyFriends = false;
            for(Pessoa p : p1.getAmigos()){
                if (p.getId().equals(p2.getId())) {
                    alreadyFriends = true;
                    break;
                }
            }

            if (!alreadyFriends) {
                // persist new friend
                p1.getAmigos().add(p2);
                p2.getAmigos().add(p1);

                p1.save();
                p2.save();

                msg = "ok";
            } else {
                // persist remove friend
                p1.getAmigos().remove(p1.getAmigos().indexOf(p2));
                p2.getAmigos().remove(p2.getAmigos().indexOf(p1));

                p1.save();
                p2.save();

                msg = "remove";
            }
        }

        return ok(msg);
    }
}
