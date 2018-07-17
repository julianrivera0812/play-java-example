package action;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import play.Logger;
import play.mvc.Action.Simple;
import play.mvc.Http.Context;
import play.mvc.Result;

public class AuthenticatedAction extends Simple {

	@Override
	public CompletionStage<Result> call(Context ctx) {
		Logger.info("Calling action for {}", ctx);
		System.out.println("action");

		/*
		 * Crear un ActionsComposition que valide la autenticación de la siguiente
		 * forma, si en el request llegar un header con el nombre tkn continuará al
		 * action de lo contrario retornará un http 403
		 */
		if(ctx.request().getHeaders().get("tkn").isPresent()) {
			return delegate.call(ctx);
		}
		return CompletableFuture.completedFuture(forbidden());
	}

}
