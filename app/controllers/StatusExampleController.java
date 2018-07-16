package controllers;

import play.mvc.Controller;
import play.mvc.Result;

public class StatusExampleController extends Controller {

	public Result status200() {
		return ok("HTTP 200 OK");
	}

	public Result status401() {
		return unauthorized("HTTP 401 Unauthorized");
	}

	public Result status500() {
		return internalServerError("HTTP 500 Internal Server Error");
	}

	public Result downloadPDF() {
		return redirect("/assets/pdfFile.pdf");
	}
}
