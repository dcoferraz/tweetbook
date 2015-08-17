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
            return redirect("/timeline");
        } else {
            session().put("showMenu", "false");
            return ok(tweetbook.render());
        }


    }

    @Transactional
    public Result login() {

        System.out.printf("login executado!");

        DynamicForm form = Form.form().bindFromRequest();

        if (form.data().size() == 0) {

            System.out.printf("if login executado!");

            return ok(error.render("Expecting some data"));

        } else {

            System.out.printf("else login executado!");

            Pessoa p = new Pessoa();


            //TODO: get id from authLogin and save in session

            Long pessoaId;
            if (session().get("oauth_provider").isEmpty()) {
                pessoaId = p.authLogin(form.get("login"), form.get("password"));
            }
            else {
                pessoaId = Pessoa.getByOAuth(session().get("oauth_provider"), session().get("oauth_id")).getId();
            }

            if (pessoaId != null) {

                session().put("conected", p.getNome());
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
