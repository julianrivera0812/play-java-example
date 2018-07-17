package controllers;

import action.AuthenticatedAction;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.With;

public class ActionCompositionController extends Controller {

	@With(AuthenticatedAction.class)
	public Result authenticated() {
		return ok("Authenticated");
	}

}
