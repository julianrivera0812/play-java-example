package controllers;

import static java.time.temporal.ChronoUnit.SECONDS;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Executor;

import javax.inject.Inject;

import com.fasterxml.jackson.databind.JsonNode;

import executor.BlockingExecutionContext;
import model.Person;
import play.libs.Json;
import play.libs.concurrent.Futures;
import play.libs.concurrent.HttpExecution;
import play.mvc.Controller;
import play.mvc.Result;

public class PersonController extends Controller {

	private Futures futures;
	private BlockingExecutionContext blockingExecutionContext;

	@Inject
	public PersonController(Futures futures, BlockingExecutionContext blockingExecutionContext) {
		this.futures = futures;
		this.blockingExecutionContext = blockingExecutionContext;
	}

	public CompletionStage<Result> createPersonBlocking() throws InterruptedException {

		JsonNode jsonPerson = request().body().asJson();

		if (jsonPerson == null) {
			return CompletableFuture.completedFuture(badRequest("Se esperaba un Json"));
		}

		Person person = Json.fromJson(jsonPerson, Person.class);

		if (person == null) {
			return CompletableFuture.completedFuture(badRequest("No se logró parsear la persona"));
		}

		return futures.delayed(() -> {
			System.out.println(person);
			return CompletableFuture.completedFuture(ok("Persona creada correctamente"));
			// CreatePerson
		}, Duration.of(1, SECONDS));

	}

	public CompletionStage<Result> createPersonNonBlocking() throws InterruptedException {

		JsonNode jsonPerson = request().body().asJson();

		if (jsonPerson == null) {
			return CompletableFuture.completedFuture(badRequest("Se esperaba un Json"));
		}

		Person person = Json.fromJson(jsonPerson, Person.class);

		if (person == null) {
			return CompletableFuture.completedFuture(badRequest("No se logró parsear la persona"));
		}
		// Crear persona
		System.out.println(person);

		return CompletableFuture.completedFuture(ok("Persona creada correctamente"));

	}

	private List<Person> getPeopleListBlocking() throws InterruptedException {

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return Arrays.asList(new Person(1, "Persona", "No 1"), new Person(2, "Persona", "No 2"),
				new Person(3, "Persona", "No 3"));

	}

	public CompletionStage<Result> getPeopleBlocking() throws InterruptedException {
		return CompletableFuture.completedFuture(ok(Json.toJson(getPeopleListBlocking())));
	}

	public CompletionStage<Result> getPeopleNonBlocking() {
		Executor executor = HttpExecution.fromThread((Executor) blockingExecutionContext);
		return CompletableFuture.supplyAsync(() -> {

			try {
				return ok(Json.toJson(getPeopleListBlocking()));
			} catch (InterruptedException e) {
				e.printStackTrace();
				return badRequest();
			}

		}, executor);
	}
}
