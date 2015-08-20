package controllers;

import play.data.DynamicForm;
import play.mvc.*;
import play.db.jpa.*;
import views.html.*;
import models.Pessoa;
import play.data.Form;
import java.util.List;

import static play.libs.Json.*;

public class Amigo extends Controller {

    public Result index() {
        return ok(amigo.render());
    }

    public Result addAmigo(String idUser, String idAmigo){

        String msg;

        if (idUser != null && idAmigo != null) { //TODO: isFriend == null
            //TODO: search if they are friends
            //TODO: persist new friend
            msg = "ok";

        } else {//TODO: if(isFriend != null)
            msg = "remove";
            //TODO: persist remove friend
        }

        return ok(msg);
    }
}
