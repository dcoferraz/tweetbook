package controllers;

import play.db.jpa.Transactional;
import models.Pessoa;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;

/**
 * Created by Daniel on 16/08/2015.
 */
public class User extends Controller {

    public Result novo() {
        return ok(newUser.render());
    }

    @Transactional
    public Result criar() {

        DynamicForm form = Form.form().bindFromRequest();

        if (form.data().size() == 0) {

            return ok(error.render("Expecting some data"));

        } else {

            Pessoa p = new Pessoa();



            p.setNome(form.get("nome"));
            p.setSenha(form.get("senha"));
            p.setUrlImagem(form.get("urlImagem"));
            p.setSexo(form.get("sexo"));
            p.setCidade(form.get("cidade"));
            p.setEstado(form.get("estado"));

            p.save();

            return ok(timeline.render());

        }
    }
}
