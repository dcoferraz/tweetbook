package controllers;

import com.avaje.ebean.annotation.Transactional;
import models.Pessoa;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;

public class Index extends Controller {

    private Pessoa pessoaDao = new Pessoa();

    /**
     * index
     * @return view or redirect
     */

    public Result index() {

        if(session().get("conected") != null){
            session().put("showMenu", "true");
            return redirect("/timeline");
        }

        session().put("conected", "");
        session().put("conectedId", "");

        session().put("showMenu", "false");
        return ok(tweetbook.render());

    }

    /**
     * login function to log the user in the platform
     * @return view or redirect
     */
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

                session().put("showMenu", "true");
                session().put("conectedId", pessoaId.toString());

                return redirect("/timeline");

            } else {
                return ok(error.render("Login/Senha incorretos! Tente novamente! :)"));
            }

        }
    }

    /**
     * logout function to kill the user session
     * @return redirect
     */
    public Result logout() {

        /* CLEAR SESSION */
        session().clear();

        return redirect("/");
    }

}
