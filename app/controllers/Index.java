package controllers;


import models.Pessoa;
import play.data.DynamicForm;
import play.data.Form;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;

public class Index extends Controller {

    public Result index() {

        String isConected = session().get("conected");

        if (isConected != null) {
            session().put("showMenu", "true");
        } else {
            session().put("showMenu", "false");
        }

        return ok(tweetbook.render());
    }

    @Transactional
    public Result login() {

        DynamicForm form = Form.form().bindFromRequest();

        if (form.data().size() == 0) {

            return ok(error.render("Expecting some data"));

        } else {

            Pessoa p = new Pessoa();


            //TODO: get id from authLogin and save in session

            Long pessoaId = p.authLogin(form.get("login"), form.get("password"));

            if (pessoaId != null) {

                session().put("conected", form.get("login"));
                session().put("showMenu", "true");
                session().put("conectedId", pessoaId.toString());

                return redirect("/timeline");

            } else {
                return ok(error.render("Login/senha incorreto! Tente de novo! :)"));
            }

        }
    }


    public Result logout() {
        /*CLEAR SESSION*/
        session().clear();

        return redirect("/");
    }

}
