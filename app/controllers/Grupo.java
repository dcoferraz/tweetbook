package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;

public class Grupo extends Controller {

    public Result index() {
        return ok(grupo.render());
    }

    /**
     * addParticipante persists a participante to an event
     * @param idGrupo
     * @param idParticipante
     * @return redirect
     */
    public Result addParticipante(String idGrupo, String idParticipante) {

        //TODO: adicionar participante se ele nao faz parte do grupo
        //TODO: participante se ele faz parte do grupo

        return ok("ok");
    }
}
