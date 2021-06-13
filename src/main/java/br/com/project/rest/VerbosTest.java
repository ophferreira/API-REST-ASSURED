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
	
	@Test
	public void changeUser() {
		given()
			.log().all()
			.contentType(ContentType.JSON)
			.body("{\"name\":\"New User\",\"age\":20}")
		.when()
			.put("https://restapi.wcaquino.me/users/1")
		.then()
			.log().all()
			.statusCode(200)
			.body("name", is("New User"))
			.body("age", is(20));
	}
	
	@Test
	public void customURL() {
		given()
			.log().all()
			.contentType(ContentType.JSON)
			.body("{\"name\":\"New User\",\"age\":20}")
			.pathParam("entidade", "users")
			.pathParam("userID", 1)
		.when()
			.put("https://restapi.wcaquino.me/{entidade}/{userID}")
		.then()
			.log().all()
			.statusCode(200)
			.body("name", is("New User"))
			.body("age", is(20));
	}
	
	@Test
	public void deleteUser() {
		given()
			.log().all()
		.when()
			.delete("https://restapi.wcaquino.me/users/1")
		.then()
			.log().all()
			.statusCode(204);
	}
	
	@Test
	public void noDeleteUserNonexistent() {
		given()
			.log().all()
		.when()
			.delete("https://restapi.wcaquino.me/users/1000")
		.then()
			.log().all()
			.statusCode(400);
	}
}