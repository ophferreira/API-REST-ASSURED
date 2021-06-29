package br.com.project.rest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import io.restassured.http.ContentType;

public class AuthTest {
	
	@Test
	public void acessarSWAPI() {
		given()
			.log().all()
		.when()
			.get("https://swapi.dev/api/people/1")
		.then()
			.log().all()
			.statusCode(200)
			.body("name", is("Luke Skywalker"))
		;
	}
	
// https://api.openweathermap.org/data/2.5/weather?q=Fortaleza,BR&appid=ad1637aa54dc2bb34670951e5c33a7b5&units=metric
	
	@Test
	public void obterClima() {
		given()
			.log().all()
			.queryParam("q", "Fortaleza,BR")
			.queryParam("appid", "ad1637aa54dc2bb34670951e5c33a7b5")
			.queryParam("units", "metric")
		.when()
			.get("https://api.openweathermap.org/data/2.5/weather")
		.then()
			.log().all()
			.statusCode(200)
			.body("name", is("Fortaleza"))
		;
	}
	
	@Test
	public void naoDeveLogarSemSenha() {
		given()
			.log().all()
		.when()
			.get("http://restapi.wcaquino.me/basicauth")
		.then()
			.log().all()
			.statusCode(401)
		;
	}
	
	@Test
	public void deveAutenticar() {
		given()
			.log().all()
		.when()
			.get("http://admin:senha@restapi.wcaquino.me/basicauth")
		.then()
			.log().all()
			.statusCode(200)
			.body("status", is("logado"))
		;
	}
	
	@Test
	public void deveAutenticar2() {
		given()
			.log().all()
			.auth().basic("admin", "senha")
		.when()
			.get("http://restapi.wcaquino.me/basicauth")
		.then()
			.log().all()
			.statusCode(200)
			.body("status", is("logado"))
		;
	}
	
	@Test
	public void deveAutenticarChallenge() {
		given()
			.log().all()
			.auth().preemptive().basic("admin", "senha")
		.when()
			.get("http://restapi.wcaquino.me/basicauth2")
		.then()
			.log().all()
			.statusCode(200)
			.body("status", is("logado"))
		;
	}
	
	@Test
	public void autenticarComTokenJWT() {
		Map<String, String> login = new HashMap<String, String>();
		login.put("email", "pedro@outlook.com.br");
		login.put("senha", "pedro123");
		
		//Logar e receber o token
		
		String token = given()
			.log().all()
			.body(login)
			.contentType(ContentType.JSON)
		.when()
			.post("https://barrigarest.wcaquino.me/signin")
		.then()
			.log().all()
			.statusCode(200)
			.extract().path("token")
		;
		
		//Obter as contas
		given()
			.log().all()
			.header("Authorization", "JWT " + token)
		.when()
			.get("https://barrigarest.wcaquino.me/contas")
		.then()
			.log().all()
			.statusCode(200)
		;
	}
	
//	@Test
//	public void acessarApplicationWeb() {
//		//Login
//		
//		String cookie	 = given()
//			.log().all()
//			.formParam("email", "pedro@outloo.com.br")
//			.formParam("senha", "pedro123")
//			.contentType(ContentType.URLENC.withCharset("UTF-8"))
//		.when()
//			.post("https://seubarriga.wcaquino.me/logar")
//		.then()
//			.log().all()
//			.statusCode(200)
//			.extract().header("set-cookie")
//		;
//		
//		cookie = cookie.split("=")[1].split(";")[0];
//		System.out.println(cookie);
//	}
}