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

    Pessoa pessoaDAO = new Pessoa();

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

            return redirect("/timeline");

        }
    }


    public Result profile(String id) {

        Long idPessoa = Long.parseLong(id);

        Pessoa p;

        p = pessoaDAO.getById(idPessoa);

        System.out.println("Achei um com nome: " + p.getNome());


        return ok(profile.render(p));
    }

    @Transactional
    public Result editar() {
        DynamicForm form = Form.form().bindFromRequest();

        if (form.data().size() == 0) {

            return ok(error.render("Expecting some data"));

        } else {

            Pessoa p = new Pessoa();

            p.setId(Long.parseLong(form.get("id")));
            p.setNome(form.get("nome"));
            p.setSenha(form.get("senha"));
            p.setUrlImagem(form.get("urlImagem"));
            p.setSexo(form.get("sexo"));
            p.setCidade(form.get("cidade"));
            p.setEstado(form.get("estado"));

            //edit
            p.update();


            return redirect("/timeline");

        }
    }
}
