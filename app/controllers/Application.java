package controllers;

import play.data.DynamicForm;
import play.mvc.*;
import play.db.jpa.*;
import views.html.*;
import models.Pessoa;
import play.data.Form;
import java.util.List;

import static play.libs.Json.*;

public class Application extends Controller {

    public Result index() {
        return ok(index.render());
    }

    @Transactional
    public Result addPerson() {
        DynamicForm form = Form.form().bindFromRequest();

        if (form.data().size() == 0) {

            return badRequest("Expceting some data");

        } else {
            String response = "Nome: " + form.get("name");

            System.out.println(response);

//            Pessoa p = new Pessoa();
//
//            p.setNome(form.get("name"));
//
//            p.save();

            return redirect(routes.Index.index());
        }

        //JPA.em().persist(person);
        //return redirect(routes.Application.index());
    }

    @Transactional(readOnly = true)
    public Result getPersons() {
        List<Pessoa> pessoas = (List<Pessoa>) JPA.em().createQuery("select p from Pessoa p").getResultList();
        return ok(toJson(pessoas));
    }
}
