package controllers;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.annotation.Transactional;
import com.fasterxml.jackson.databind.JsonNode;
import models.Pessoa;
import play.data.DynamicForm;
import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;

import java.util.List;


/**
 * Created by Daniel on 16/08/2015.
 */
public class User extends Controller {

    /**
     * novo persists new user
     *
     * @return template render
     */
    public Result novo() {

        Pessoa p;
        String oauth_userData = session().get("oauth_userData");
        if (oauth_userData == null || oauth_userData.isEmpty()) {
            p = new Pessoa();
        } else {
            JsonNode json = Json.parse(oauth_userData);
            p = Json.fromJson(json, Pessoa.class);
        }

        return ok(newUser.render(p));
    }

    /**
     * criar persists new user from Twitter
     *
     * @return redirect
     */
    @Transactional
    public Result criar() {

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

            p.save();

            Long pessoaId = Pessoa.authLogin(p.getEmail(), p.getSenha());

            session().put("conected", p.getNome());
            session().put("showMenu", "true");
            session().put("conectedId", pessoaId.toString());

            return redirect("/timeline");

        }
    }

    /**
     * profile selects data from DB
     *
     * @param id
     * @return template render
     */
    public Result profile(String id) {

        Long idPessoa = Long.parseLong(id);

        Pessoa p;

        p = Ebean.find(Pessoa.class, idPessoa);

        return ok(profile.render(p));
    }

    /**
     * editar edits a created user
     *
     * @return redirect
     */
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

    public Result getPessoas()
    {
        List<Pessoa> pessoasAtivo = Ebean.find(Pessoa.class).findList();

        if (pessoasAtivo != null)
        {
            return ok(Json.toJson(pessoasAtivo));
        }

        return ok("erro");
    }
}
