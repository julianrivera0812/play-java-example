package controllers;

import java.util.HashMap;
import java.util.Map;

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
		return ok(header.toString());
	}

	public Result getOnePerson() {
		return ok(peopleMap.get("001"));
	}
	
	public Result getCookie(String name) {
		Cookie cookie = request().cookie(name);
		StringBuilder cookieString = new StringBuilder().append("COOKIE ").append(name).append(" -> ")
				.append(cookie == null ? "NO COOKIE": cookie.value());
		System.out.println(cookieString.toString());
		return ok(cookieString.toString());
	}
}
