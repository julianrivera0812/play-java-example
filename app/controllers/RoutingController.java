package controllers;

import java.time.Duration;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;

import model.Person;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Http.Cookie;
import play.mvc.Result;

public class RoutingController extends Controller {

	private Map<String, String> peopleMap = new HashMap<>();

	public RoutingController() {
		peopleMap.put("001", "Pepito uno");
		peopleMap.put("002", "Pepito dos");
		peopleMap.put("003", "Pepito tres");
		peopleMap.put("004", "Pepito cuatro");
		peopleMap.put("005", "Pepito cinco");
	}

	public Result getPerson(String id) {
		return ok(peopleMap.get(id));
	}

	public Result getHeader(String name) {

		StringBuilder header = new StringBuilder().append("HEADER ").append(name).append(" -> ")
				.append(request().getHeaders().get(name).orElse("NO HEADER"));
		System.out.println(header.toString());
		response().setHeader("HEADER-RESPONSE", "Header en la respuesta");
		return ok(header.toString());
	}

	public Result getOnePerson() {
		return ok(peopleMap.get("001"));
	}

	public Result getCookie(String name) {
		Cookie cookie = request().cookie(name);
		StringBuilder cookieString = new StringBuilder().append("COOKIE ").append(name).append(" -> ")
				.append(cookie == null ? "NO COOKIE" : cookie.value());
		System.out.println(cookieString.toString());
		response().setCookie(Cookie.builder("COOKIE-RESPONSE", "C00K13R3SP0NS3").withMaxAge(Duration.ofSeconds(360))
				.withPath("/").withDomain("localhost").withSecure(false).withHttpOnly(false)
				.withSameSite(Cookie.SameSite.STRICT).build());
		return ok(cookieString.toString());
	}

	public Result createPerson() {

		JsonNode jsonPerson = request().body().asJson();

		if (jsonPerson == null) {
			return badRequest("Se esperaba un Json");
		}

		String firstname = jsonPerson.findPath("firstname").textValue();

		if (firstname == null) {
			return badRequest("El parámetro [firstname] es requerido");
		}

		Person person = Json.fromJson(jsonPerson, Person.class);

		return ok(person == null ? "BAD" : person.toString());
	}

	public Result workWithJson() {
		List<Person> personList = Arrays.asList(new Person(1, "Persona", "No 1"), new Person(2, "Persona", "No 2"),
				new Person(3, "Persona", "No 3"));

		/* Convertir objeto a Json */
		JsonNode jsonPersonList = Json.toJson(personList);
		System.out.println(jsonPersonList.toString());

		/* Obtener una propiedad especifica de la lista de JsonNode */
		List<JsonNode> values = jsonPersonList.findValues("identification");
		for (JsonNode jsonNode : values) {
			System.out.println(jsonNode.asLong());
		}

		/* parsear un string que representa un Json a JsonNode */
		JsonNode jsonParsed = Json.parse(
				"[{\"identification\":1,\"firstname\":\"Persona\",\"lastname\":\"No 1\"},{\"identification\":2,\"firstname\":\"Persona\",\"lastname\":\"No 2\"},{\"identification\":3,\"firstname\":\"Persona\",\"lastname\":\"No 3\"}]");

		if (jsonParsed.isArray()) {
			for (JsonNode jsonNode : jsonParsed) {
				System.out.println("JsonNode -> " + jsonNode.toString());
				/* Obtener el objeto a partir del JsonNode */
				Person person = Json.fromJson(jsonNode, Person.class);
				System.out.println("PERSON -> " + person);
			}
		}

		return ok(jsonPersonList);
	}
}
