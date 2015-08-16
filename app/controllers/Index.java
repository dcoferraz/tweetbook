package controllers;


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

            return badRequest("Expceting some data");

        } else {

            return ok(timeline.render(form.get("login")));

        }
    }

}
