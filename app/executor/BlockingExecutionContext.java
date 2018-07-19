package executor;

import javax.inject.Inject;

import akka.actor.ActorSystem;
import play.libs.concurrent.CustomExecutionContext;

public class BlockingExecutionContext extends CustomExecutionContext{

	@Inject
	public BlockingExecutionContext(ActorSystem actorSystem, String name) {
		super(actorSystem, "my-blocking-dispatcher");
	}

}
