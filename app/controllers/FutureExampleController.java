package controllers;

import static java.time.temporal.ChronoUnit.SECONDS;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Executor;
import java.util.concurrent.ForkJoinPool;

import javax.inject.Inject;

import play.libs.concurrent.Futures;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Controller;
import play.mvc.Result;

public class FutureExampleController extends Controller {

	private HttpExecutionContext httpExecutionContext;
	private final Futures futures;
	private final Executor customExecutor = ForkJoinPool.commonPool();

	@Inject
	public FutureExampleController(HttpExecutionContext ec, Futures futures) {
		this.futures = futures;
		this.httpExecutionContext = ec;
	}

	public CompletionStage<Result> index() {
		// Use a different task with explicit EC
		return calculateResponse().thenApplyAsync(answer -> {
			// uses Http.Context
			ctx().flash().put("info", "Response updated!");
			return ok("answer was " + answer);
		}, httpExecutionContext.current());
	}

	private static CompletionStage<String> calculateResponse() {
		return CompletableFuture.completedFuture("42");
	}

	private static CompletionStage<Integer> calculateSquare(int base, int height) {
		return CompletableFuture.completedFuture(base * height);
	}

	private CompletionStage<Integer> callWithOneSecondTimeout() {
		return futures.timeout(calculateSquare(10, 50), Duration.ofSeconds(1));
	}

	public CompletionStage<Result> timeout() {
		return callWithOneSecondTimeout().thenApplyAsync(response -> {
			return ok("answer was " + response);
		});
	}

	public CompletionStage<Result> delayedResult() {
		long start = System.currentTimeMillis();
		return futures.delayed(() -> CompletableFuture.supplyAsync(() -> {
			long end = System.currentTimeMillis();
			long seconds = end - start;
			return ok("rendered after " + seconds / 1000 + " seconds");
		}, customExecutor), Duration.of(3, SECONDS));
	}
}
