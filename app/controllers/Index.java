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

        System.out.println("current_id: "+session().get("current_id"));

        DynamicForm form = Form.form().bindFromRequest();

        if (form.data().size() == 0) {

            return ok(error.render("Expecting some data"));

        } else {

            Pessoa p = new Pessoa();

            //TODO: get id from authLogin and save in session

            Long pessoaId = null;
            String oauth_provider = session().get("oauth_provider");
            if (oauth_provider != null && !oauth_provider.isEmpty()) {
                pessoaId = Pessoa.getByOAuth(oauth_provider, session().get("oauth_id")).getId();
            } else {
                pessoaId = p.authLogin(form.get("login"), form.get("password"));

                System.out.println("depois do authLogin");
            }

            if (pessoaId != null) {

                System.out.println("antes de colocar valores no session");

                session().put("conected", p.getNome());
                session().put("showMenu", "true");
                session().put("conectedId", pessoaId.toString());

                System.out.println("antes de redirecionar para timeline...");

                return redirect("/timeline");

            } else {
                return ok(error.render("Login/Senha incorretos! Tente novamente! :)"));
            }

        }
    }


    public Result logout() {
        /*CLEAR SESSION*/
        session().clear();

        return redirect("/");
    }

}
