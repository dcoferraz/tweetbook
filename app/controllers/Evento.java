package controllers;

import play.data.DynamicForm;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;

public class Evento extends Controller {

    public Result index(){return ok(evento.render());}

}
