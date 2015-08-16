package controllers;


import models.Pessoa;
import play.data.DynamicForm;
import play.data.Form;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;

public class Index extends Controller{

    public Result index() {
        return ok(tweetbook.render());
    }

    @Transactional
    public Result login() {

        DynamicForm form = Form.form().bindFromRequest();

        if (form.data().size() == 0) {

            return ok(error.render("Expecting some data"));

        } else {

            Pessoa p = new Pessoa();

            if(p.authLogin(form.get("login"), form.get("password"))){
                return ok(timeline.render());
            }else{
                return ok(error.render("Login/senha incorreto! Tente de novo! :)"));
            }

        }
    }

}
