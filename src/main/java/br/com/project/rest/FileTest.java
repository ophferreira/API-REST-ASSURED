package br.com.project.rest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.junit.Test;



public class FileTest {
	
	@Test
	public void obrigarEnvioArquivo() {
		given()
			.log().all()
		.when()
			.post("https://restapi.wcaquino.me/upload")
		.then()
			.log().all()
			.statusCode(404)
			.body("error", is("Arquivo não enviado"))
		;
	}
	
	@Test
	public void fazerUploadArquivo() {
		given()
			.log().all()
			.multiPart("arquivo", new File("resources/users.pdf"))
		.when()
			.post("https://restapi.wcaquino.me/upload")
		.then()
			.log().all()
			.statusCode(200)
			.body("name", is("users.pdf"))
		;
	}
	
	@Test
	public void naoFazerUploadArquivoGrande() {
		given()
			.log().all()
			.multiPart("arquivo", new File("resources/TestMax.pdf"))
		.when()
			.post("https://restapi.wcaquino.me/upload")
		.then()
			.log().all()
			.time(lessThan(10000L))
			.statusCode(413)
		;
	}
	
	@Test
	public void baixarArquivo() throws IOException {
		byte[] img = given()
			.log().all()
		.when()
			.get("https://restapi.wcaquino.me/download")
		.then()
			.statusCode(200)
			.extract().asByteArray()
		;
		
		File image = new File("resources/img.jpg");
		OutputStream out = new FileOutputStream(image);
		out.write(img);
		out.close();
	}
}