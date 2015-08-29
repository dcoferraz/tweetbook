package controllers;

import play.data.DynamicForm;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;

public class Eventos extends Controller {

    /**
     * index renders evento template
     * @return redirect
     */
    public Result index(){return ok(evento.render());}

}
