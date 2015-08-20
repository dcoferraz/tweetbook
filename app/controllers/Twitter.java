package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import models.Pessoa;
import play.libs.Json;
import play.libs.F.Function;
import play.libs.F.Option;
import play.libs.F.Promise;
import play.libs.oauth.OAuth;
import play.libs.oauth.OAuth.ConsumerKey;
import play.libs.oauth.OAuth.OAuthCalculator;
import play.libs.oauth.OAuth.RequestToken;
import play.libs.oauth.OAuth.ServiceInfo;
import play.libs.ws.WSClient;
import play.libs.ws.WSResponse;
import play.mvc.Controller;
import play.mvc.Result;

import com.google.common.base.Strings;

import javax.inject.Inject;

public class Twitter extends Controller {
    static final String PROVIDER_NAME = "twitter";

    // ATENCAO: a "key" e o "secret" NAO DEVEM SER PUBLICADOS OU COMPARTILHADOS!
    static final ConsumerKey KEY = new ConsumerKey("HvV2oh9LmZmDTqD0pVRYeqBt3", "wyah0kwTIrOFvkVKtcQr5ujIQIBebE7LFbMDN97CgAzuvmagEx");

    private static final ServiceInfo SERVICE_INFO = new ServiceInfo("https://api.twitter.com/oauth/request_token",
                                                                    "https://api.twitter.com/oauth/access_token",
                                                                    //"https://api.twitter.com/oauth/authorize",
                                                                    "https://api.twitter.com/oauth/authenticate",
                                                                    KEY);

    private static final OAuth TWITTER = new OAuth(SERVICE_INFO);

    private final WSClient ws;

    @Inject
    public Twitter(WSClient ws) {
        this.ws = ws;
    }

    public Promise<Result> userInfo() {
        Option<RequestToken> sessionTokenPair = getSessionTokenPair();
        if (sessionTokenPair.isDefined()) {
            return ws.url("https://api.twitter.com/1.1/account/verify_credentials.json")
                    .sign(new OAuthCalculator(Twitter.KEY, sessionTokenPair.get()))
                    .get()
                    .map(new Function<WSResponse, Result>() {
                        @Override
                        public Result apply(WSResponse result) throws Throwable {
                            session().put("oauth_provider", PROVIDER_NAME);

                            JsonNode json = result.asJson();

                            //System.out.println(json.toString());

                            Pessoa p = Pessoa.getByOAuth(PROVIDER_NAME, json.get("id").asText());

                            if (p != null) {
                                // se foi encontrado um usuario no banco, entao redirecionar para a tela principal
                                session().put("oauth_id", json.get("id").asText());

                                System.out.println("encontrou usuario a partir do twitter");

                                session().put("conected", p.getNome());
                                session().put("showMenu", "true");
                                session().put("conectedId", p.getId().toString());

                                return redirect("/timeline");
                            } else {
                                // se nao foi encontrado, redirecionar para a tela de cadastro para concluir seu cadastro
                                p = new Pessoa();

                                p.setOauth_provider(PROVIDER_NAME);
                                p.setOauth_id(json.get("id").asText());

                                p.setNome(json.get("name").asText());

                                String cidadeUF = json.get("location").asText();
                                //p.setCidade(cidadeUF.substring(0, cidadeUF.indexOf("-")).trim());
                                p.setEstado(cidadeUF.substring(cidadeUF.indexOf("-") + 1, cidadeUF.length()));

                                p.setUrlImagem(json.get("profile_image_url").asText());

                                session().put("oauth_userData", Json.toJson(p).toString());

                                return redirect(routes.User.novo());
                            }
                        }
                    });
        }
        return Promise.pure(redirect(routes.Twitter.auth()));
    }

    public Result auth() {
            String verifier = request().getQueryString("oauth_verifier");
            if (Strings.isNullOrEmpty(verifier)) {
                String url = routes.Twitter.auth().absoluteURL(request());

                System.out.println("url obtida: " + url);

                RequestToken requestToken = TWITTER.retrieveRequestToken(url);
                saveSessionTokenPair(requestToken);

                return redirect(TWITTER.redirectUrl(requestToken.token));
            } else {
                RequestToken requestToken = getSessionTokenPair().get();
                RequestToken accessToken = TWITTER.retrieveAccessToken(requestToken, verifier);

                saveSessionTokenPair(accessToken);

                return redirect(routes.Twitter.userInfo());
            }
    }

    private void saveSessionTokenPair(RequestToken requestToken) {
        session("token", requestToken.token);
        session("secret", requestToken.secret);
    }

    private Option<RequestToken> getSessionTokenPair() {
        if (session().containsKey("token")) {
            return Option.Some(new RequestToken(session("token"), session("secret")));
        }
        return Option.None();
    }

}