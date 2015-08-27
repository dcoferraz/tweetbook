package controllers;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.annotation.Transactional;
import models.Pessoa;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;

public class Index extends Controller {

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

            Long pessoaId = null;
            String oauth_provider = session().get("oauth_provider");

            if (oauth_provider != null && !oauth_provider.isEmpty()) {
                pessoaId = Pessoa.getByOAuth(oauth_provider, session().get("oauth_id")).getId();
            } else {
                pessoaId = Pessoa.authLogin(form.get("login"), form.get("password"));
            }

            if (pessoaId != null) {

                Pessoa p = Ebean.find(Pessoa.class, pessoaId);

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
