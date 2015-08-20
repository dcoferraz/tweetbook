package controllers;

import com.avaje.ebean.annotation.Transactional;
import com.fasterxml.jackson.databind.JsonNode;
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
        String oauth_userData = session().get("oauth_userData");
        if (oauth_userData == null || oauth_userData.isEmpty()) {
            p = new Pessoa();
        }
        else {
            JsonNode json = Json.parse(oauth_userData);
            p = Json.fromJson(json, Pessoa.class);
        }

        return ok(newUser.render(p));
    }

    @Transactional
    public Result criar() {

        System.out.println("CONTROLLER USER, METHOD CRIAR");

        DynamicForm form = Form.form().bindFromRequest();

        if (form.data().size() == 0) {

            return ok(error.render("Expecting some data"));

        } else {

            Pessoa p;
            String oauth_userData = session().get("oauth_userData");
            if (oauth_userData != null && !oauth_userData.isEmpty()) {
                JsonNode json = Json.parse(oauth_userData);
                p = Json.fromJson(json, Pessoa.class);
            } else {
                p = new Pessoa();
            }

            p.setNome(form.get("nome"));

            // TODO: tratar se o email ja estiver cadastrado (deve ser unico)
            p.setEmail(form.get("email"));

            if (p.getOauth_id() != null && !p.getOauth_id().isEmpty()) {
                p.setSenha("-----");
            } else {
                p.setSenha(form.get("senha"));
            }
            p.setUrlImagem(form.get("urlImagem"));
            p.setSexo(form.get("sexo"));
            p.setCidade(form.get("cidade"));
            p.setEstado(form.get("estado"));

            System.out.println("Antes de criar o usuario");

            p.save();

            System.out.println("Depois de criar o usuario");

            Long pessoaId = p.authLogin(p.getEmail(), p.getSenha());

            session().put("conected", p.getNome());
            session().put("showMenu", "true");
            session().put("conectedId", pessoaId.toString());

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
            p.setEmail(form.get("email"));
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
