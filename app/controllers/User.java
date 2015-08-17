package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import play.db.jpa.Transactional;
import models.Pessoa;
import play.data.DynamicForm;
import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;


/**
 * Created by Daniel on 16/08/2015.
 */
public class User extends Controller {

    Pessoa pessoaDAO = new Pessoa();

    public Result novo() {

        Pessoa p;
        if (session().get("oauth_userData").isEmpty()) {
            p = new Pessoa();
        }
        else {
            JsonNode json = Json.parse(session().get("oauth_userData"));
            p = Json.fromJson(json, Pessoa.class);
        }

        return ok(newUser.render(p));
    }

    @Transactional
    public Result criar() {

        DynamicForm form = Form.form().bindFromRequest();

        if (form.data().size() == 0) {

            return ok(error.render("Expecting some data"));

        } else {

            Pessoa p;
            if (session().get("oauth_userData").isEmpty()) {
                p = new Pessoa();
            }
            else {
                JsonNode json = Json.parse(session().get("oauth_userData"));
                p = Json.fromJson(json, Pessoa.class);
            }

            p.setNome(form.get("nome"));
            if (p.getOauth_id().isEmpty()) {
                p.setSenha(form.get("senha"));
            }
            else {
                p.setSenha("-----");
            }
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
