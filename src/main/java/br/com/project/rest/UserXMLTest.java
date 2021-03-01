package br.com.project.rest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import org.junit.Test;


public class UserXMLTest {
	
	public int STATUS_200 = 200; //STATUS CODE 200
	public int STATUS_201 = 201; //STATUS CODE 201
	public int STATUS_404 = 404; //STATUS CODE 404
	
	@Test
	public void trabalhandoComXML() {
		given() //Condição
		.when()
			.get("http://restapi.wcaquino.me/usersXML/3")
		.then()
			.statusCode(STATUS_200)
			.rootPath("user")
			.body("name", is("Ana Julia"))
			.body("@id", is("3")) //@ para referenciar um atributo XML, todos os valores para o XML são String
			.rootPath("user.filhos")
			.body("name.size()", is(2))
			.body("name[0]", is("Zezinho"))
			.body("name[1]", is("Luizinho"))
			.body("name", hasItem("Luizinho"))
			.body("name", hasItems("Zezinho", "Luizinho"))
		;
	}
}