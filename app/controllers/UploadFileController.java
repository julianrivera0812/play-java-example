package controllers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;

public class UploadFileController extends Controller {

	public Result render() {
		return ok(views.html.uploadfile.render());
	}

	public Result uploadFile() {

		Http.MultipartFormData<File> body = request().body().asMultipartFormData();
		Http.MultipartFormData.FilePart<File> data = body.getFile("fileData");

		if (data != null) {

			try {
				String fileName = data.getFilename();
				File file = data.getFile();

				Files.move(file.toPath(), new File("C:\\temp", fileName).toPath());
			} catch (IOException e) {
				return badRequest("Error almacenando el archivo -> " + e.getMessage());
			}

			return ok("Se cargó el archivo exitosamente");
		} else {

			return badRequest("No se envió archivo");
		}
	}
}
