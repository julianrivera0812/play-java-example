package controllers;

import java.util.Date;

import javax.inject.Inject;

import com.google.gson.Gson;
import com.typesafe.config.Config;

import play.Logger;
import play.mvc.*;

import views.html.*;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class HomeController extends Controller {

	private final Config config;

	@Inject
	public HomeController(Config config) {
		this.config = config;
	}

    /**
     * An action that renders an HTML page with a welcome message.
     * The configuration in the <code>routes</code> file means that
     * this method will be called when the application receives a
     * <code>GET</code> request with a path of <code>/</code>.
     */
	public Result index() {
		System.out.println(config.getString("application.name"));
		Logger.warn("-------------- Prueba de uso del log --------------------");
		return ok(index.render("Your new application is ready."));
	}
    
    public Result helloWorld() {
        return ok("Hello world!");
    }
    
    public Result toJson() {
        return ok(new Gson().toJson(new Date()));
    }

}
