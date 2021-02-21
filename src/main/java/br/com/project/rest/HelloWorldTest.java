package br.com.project.rest;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.hamcrest.Matchers;
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
	
	@Test
	public void usandoMatchersHamcrest() {
		assertThat(21, is(21));
		assertThat(21, isA(Integer.class));
		assertThat(21d, isA(Double.class));
		assertThat(21, greaterThan(20)); //Verifica se é maior
		assertThat(21, lessThan(22)); //Verifica se é menor
		
		List<Integer> impares = Arrays.asList(1,3,5,7,9);
		
		assertThat(impares, hasSize(5)); //Verifica o tamanho da lista
		assertThat(impares, contains(1,3,5,7,9)); //Verifica se contém os valores
		assertThat(impares, hasItem(3)); //Verifica se contém o valor na lista
		
		assertThat("Mariah", is(not("John")));
		assertThat("Mariah", not("John")); //Funciona da mesma forma sem o is
		
		assertThat("Mariah", anyOf(is("Mariah"), is("John"))); //Condicional OU
		assertThat("Mariah", allOf(startsWith("Ma"), endsWith("ah"), containsString("ri"))); // Condicional E
	}
}
