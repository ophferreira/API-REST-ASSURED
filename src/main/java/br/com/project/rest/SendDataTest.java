package br.com.project.rest;

import static io.restassured.RestAssured.*;

import org.hamcrest.Matchers;
import org.junit.Test;

import io.restassured.http.ContentType;

public class SendDataTest {
	
	@Test
	public void enviarValorViaQuery() {
		given()
			.log().all()
		.when()
			.get("https://restapi.wcaquino.me/v2/users?format=json")
		.then()
			.log().all()
			.statusCode(200)
			.contentType(ContentType.JSON);
	}
	
	@Test
	public void enviarValorViaQueryParam() {
		given()
			.log().all()
			.queryParam("format", "xml")
			.queryParam("toput", "throught")
		.when()
			.get("https://restapi.wcaquino.me/v2/users")
		.then()
			.log().all()
			.statusCode(200)
			.contentType(ContentType.XML)
			.contentType(Matchers.containsString("utf-8"));
	}
	
	@Test
	public void enviarValorViaHeader() {
		given()
			.log().all()
			.accept(ContentType.XML)
		.when()
			.get("https://restapi.wcaquino.me/v2/users")
		.then()
			.log().all()
			.statusCode(200)
			.contentType(ContentType.XML);
	}
}