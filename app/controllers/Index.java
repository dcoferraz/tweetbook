package controllers;


import com.avaje.ebean.annotation.Transactional;
import models.Pessoa;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.error;
import views.html.tweetbook;

public class Index extends Controller {

    private Pessoa pessoaDao = new Pessoa();

    public Result index() {

        String isConected = session().get("conected");

        if (isConected != null && (!isConected.equals(""))) {
            session().put("showMenu", "true");
            return redirect("/timeline");
        } else {
            session().put("showMenu", "false");
            return ok(tweetbook.render());
        }
    }

    @Transactional
    public Result login() {

        DynamicForm form = Form.form().bindFromRequest();

        if (form.data().size() == 0) {

            return ok(error.render("Expecting some data"));

        } else {

            Pessoa p = new Pessoa();
            Long pessoaId = null;
            String oauth_provider = session().get("oauth_provider");


            if (oauth_provider != null && !oauth_provider.isEmpty()) {

                pessoaId = Pessoa.getByOAuth(oauth_provider, session().get("oauth_id")).getId();

            } else {

                pessoaId = p.authLogin(form.get("login"), form.get("password"));

            }

            if (pessoaId != null) {

                p = pessoaDao.getById(pessoaId);

                session().put("conected", p.getNome());
                System.out.println("3/// Session nome: " + session().get("conected"));

                session().put("showMenu", "true");
                session().put("conectedId", pessoaId.toString());
                System.out.println("4/// Session ID: " + session().get("conectedId"));

                return redirect("/timeline");

            } else {
                return ok(error.render("Login/Senha incorretos! Tente novamente! :)"));
            }

        }
    }


    public Result logout() {

        /* CLEAR SESSION */
        session().clear();

        session().put("conected", "");
        session().put("conectedId", "");

        System.out.println("/* logout() method called */");

        return redirect("/");
    }

}
