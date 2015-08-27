package controllers;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.Expr;
import com.avaje.ebean.annotation.Transactional;
import models.Grupo;
import models.Pessoa;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;

import java.util.List;

public class Grupos extends Controller {

    public Result index() {
        Pessoa currentUser = Ebean.find(Pessoa.class, session().get("conectedId"));

        List<Grupo> gruposAtivos = Ebean.find(Grupo.class)
                .where()
                .eq("ativo", true)
                .findList();

        return ok(grupo.render(gruposAtivos));
    }

    /**
     * addParticipante persists a participante to an event
     * @param idGrupo
     * @param idParticipante
     * @return redirect
     */
    @Transactional
    public Result addParticipante(String idGrupo, String idParticipante) {

        Grupo g = Ebean.find(Grupo.class, idGrupo);
        System.out.println("g = " + g);

        Pessoa participante = Ebean.find(Pessoa.class, idParticipante);

        String result;

        if (g.getParticipantes().contains(participante)) {
            g.getParticipantes().remove(participante);
            result = "remove";
        }
        else {
            g.getParticipantes().add(participante);
            result = "ok";
        }

        g.save();

        return  ok(result);
    }

    /**
     * Pesists new Group
     * @param idUser
     * @param local
     * @param data
     * @param descricao
     * @param nome
     * @return String
     */
    @Transactional
    public Result addGroup(String idUser, String publico, String ativo, String nome) {

        Pessoa p = Ebean.find(Pessoa.class, idUser);

        // create a new group and set the properties
        Grupo g = new Grupo();

        g.setNome(nome);
        g.setCriador(p);
        System.out.println("ativo="+ativo);

        g.setAtivo(Boolean.parseBoolean(ativo));
        g.setPublico(Boolean.parseBoolean(publico));

        // add the creator as participant
        g.getParticipantes().add(p);

        g.save();

        return ok("ok");
    }
}
