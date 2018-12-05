package com.dierauf.app.unitsconverter;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import io.restassured.RestAssured;
import io.restassured.path.json.config.JsonPathConfig;
import io.restassured.response.Response;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class UnitsConverterIT extends CommonTests {

	private static String CONTEXT_PATH = "/";

	@BeforeEach
	void setUp() throws Exception {
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = 8080;
	}


	@Override
	protected void runPositiveTest(final String name, final double expectedConversionFactor,
	    final String expectedConversionUnit) {

		Response response = given().contentType("application/json").accept("application/json").and()
		    .param("units", name).when().get(CONTEXT_PATH + "units/si").then().statusCode(200)
		    .contentType("application/json").extract().response();

		double multiplicationFactor = response.jsonPath()
		    .using(new JsonPathConfig(JsonPathConfig.NumberReturnType.BIG_DECIMAL)).getDouble("multiplication_factor");

		assertNotNull(multiplicationFactor);
		Assert.assertEquals(expectedConversionFactor, multiplicationFactor, .00000000000000d);
		String units = response.jsonPath().getString("unit_name");
		assertNotNull(units);
		Assert.assertEquals(expectedConversionUnit, units);
	}


	@Override
	protected void runNegativeTest_Calculate(final String name, final String expected) {
		this.runNegativeTest(name, expected);
	}


	@Override
	protected void runNegativeTest(final String name, final String expected) {
		Response response = given().contentType("application/json").accept("application/json").and()
		    .param("units", name).when().get(CONTEXT_PATH + "units/si").then().statusCode(500)
		    .contentType("application/json").extract().response();
		int status = response.jsonPath().getInt("status");
		assertNotNull(status);
		assertTrue(status == 500);
		String message = response.jsonPath().getString("message");
		assertNotNull(message);
		assertTrue(message.startsWith(expected));
	}


}
