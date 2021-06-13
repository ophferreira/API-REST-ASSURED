package br.com.project.rest;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.is;

import org.junit.Test;

import io.restassured.http.ContentType;

public class VerbosTest {
	
	@Test
	public void saveUserJson() {
		given()
			.log().all()
			.contentType("application/json")
			.body("{\"name\":\"Pedro Ferreira\",\"age\":23}")
		.when()
			.post("http://restapi.wcaquino.me/users")
		.then()
			.log().all()
			.statusCode(201)
			.body("name", is("Pedro Ferreira"));
	}
	
	@Test
	public void noSaveUser() {
		given()
		.log().all()
		.contentType("application/json")
		.body("{\"age\":23}")
	.when()
		.post("http://restapi.wcaquino.me/users")
	.then()
		.log().all()
		.statusCode(400)
		.body("error", is("Name é um atributo obrigatório"));
	}
	
	@Test
	public void saveUserXML() {
		given()
			.log().all()
			.contentType(ContentType.XML)
			.body("<user><name>Pedro Ferreira</name><age>50</age></user>")
		.when()
			.post("http://restapi.wcaquino.me/usersXML")
		.then()
			.log().all()
			.statusCode(201)
			.body("user.name", is("Pedro Ferreira"));
	}
}