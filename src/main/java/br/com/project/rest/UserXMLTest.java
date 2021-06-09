package br.com.project.rest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.internal.path.xml.NodeImpl;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;


public class UserXMLTest {
	
	public int STATUS_200 = 200; //STATUS CODE 200
	public int STATUS_201 = 201; //STATUS CODE 201
	public int STATUS_404 = 404; //STATUS CODE 404
	
	public static RequestSpecification reqSpec;
	public static ResponseSpecification resSpec;
	
	@BeforeClass
	public static void setup() {
		RestAssured.baseURI = "http://restapi.wcaquino.me";
//		RestAssured.port = 443;
//		RestAssured.basePath = "/v2";
		
		RequestSpecBuilder reqBuilder = new RequestSpecBuilder();
		reqBuilder.log(LogDetail.ALL);
		reqSpec = reqBuilder.build();
		
		ResponseSpecBuilder resBuilder = new ResponseSpecBuilder();
		resBuilder.expectStatusCode(200);
		resSpec = resBuilder.build();
		
		RestAssured.requestSpecification = reqSpec;
		RestAssured.responseSpecification = resSpec;
		
	}
	
	@Test
	public void trabalhandoComXML() {
		given()
		.when()
			.get("/usersXML/3")
		.then()
			.rootPath("user")
			.body("name", is("Ana Julia"))
			.body("@id", is("3")) //@ para referenciar um atributo XML, todos os valores para o XML são String
			.rootPath("user.filhos")
			.body("name.size()", is(2))
			.body("name[0]", is("Zezinho"))
			.body("name[1]", is("Luizinho"))
			.body("name", hasItem("Luizinho"))
			.body("name", hasItems("Zezinho", "Luizinho"));
	}
	
	@Test
	public void pesquisasAvancadasComXML() {
		given()
		.when()
			.get("/usersXML")
		.then()
			.body("users.user.size()", is(3))
			.body("users.user.findAll{it.age.toInteger() <= 25}.size()", is(2))
			.body("users.user.@id", hasItems("1", "2", "3"))
			.body("users.user.find{it.age == 25}.name", is("Maria Joaquina"))
			.body("users.user.findAll{it.name.toString().contains('n')}.name", hasItems("Maria Joaquina", "Ana Julia")) //No XML tem que "Forçar" a dizer que é uma String
			.body("users.user.salary.find{it != null}.toDouble()", is(1234.5678))
			.body("users.user.age.collect{it.toInteger() * 2}", hasItems(40, 50, 60))
			.body("users.user.name.findAll{it.toString().startsWith('Maria')}.collect{it.toString().toUpperCase()}", is("MARIA JOAQUINA"));
	}
	
	@Test
	public void pesquisasAvancadasComXMLEJava() {
		String nome = given()
		.when()
			.get("/usersXML")
		.then()
			.extract().path("users.user.name.findAll{it.toString().startsWith('Maria')}");
		
		Assert.assertEquals("Maria Joaquina".toUpperCase(), nome.toUpperCase());
	}
	
	@Test
	public void pesquisasAvancadasComXMLEJavaArray() {
		ArrayList<NodeImpl> nomes = given()
		.when()
			.get("/usersXML")
		.then()
			.extract().path("users.user.name.findAll{it.toString().contains('n')}");
		
		Assert.assertEquals(2, nomes.size());
		Assert.assertEquals("Maria Joaquina".toUpperCase(), nomes.get(0).toString().toUpperCase());
		Assert.assertTrue("ANA JULIA".equalsIgnoreCase(nomes.get(1).toString()));
	}
	
	@Test
	public void pesquisasAvancadasComXPath() {
		given()
		.when()
			.get("/usersXML")
		.then()
			.body(hasXPath("count(/users/user)", is("3")))
			.body(hasXPath("/users/user[@id = '1']"))
			.body(hasXPath("//user[@id = '2']"))
			.body(hasXPath("//name[contains(text(),'Luizinho')]/../../name", is("Ana Julia")))
			.body(hasXPath("//name[text() = 'Ana Julia']/following-sibling::filhos", allOf(containsString("Zezinho"), containsString("Luizinho"))))
			.body(hasXPath("/users/user[last()]/name", is("Ana Julia")))
		;
	}
}