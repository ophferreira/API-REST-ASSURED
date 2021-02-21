package br.com.project.rest;

import static io.restassured.RestAssured.*;

import org.junit.Assert;
import org.junit.Test;

import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;

public class HelloWorldTest {

	public int STATUS_200 = 200; //STATUS CODE 200
	public int STATUS_201 = 201; //STATUS CODE 201
	
	@Test
	public void testHelloWorld() {
		Response response = request(Method.GET, "http://restapi.wcaquino.me/ola");
		Assert.assertTrue(response.getBody().asString().equals("Ola Mundo!"));
		Assert.assertTrue(response.statusCode() == STATUS_200);
		Assert.assertTrue("O status code deveria ser 200", response.statusCode() == STATUS_200);
		Assert.assertEquals(STATUS_200, response.statusCode());
		
//		throw new RuntimeException();
		ValidatableResponse validate = response.then();
		validate.statusCode(STATUS_200);
	}
	
	@Test
	public void outrasFormasRestAssured() {
		Response response = request(Method.GET, "http://restapi.wcaquino.me/ola");
		
		ValidatableResponse validate = response.then();
		validate.statusCode(STATUS_200);
		
		get("http://restapi.wcaquino.me/ola").then().statusCode(STATUS_200);
	}
	
	@Test
	public void usandoGerkinCucumber() {
		given().when().get("http://restapi.wcaquino.me/ola").then().statusCode(STATUS_200);
	}
}
