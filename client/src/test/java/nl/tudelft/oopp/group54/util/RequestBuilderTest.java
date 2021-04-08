package nl.tudelft.oopp.group54.util;

import nl.tudelft.oopp.group54.models.responseentities.DeleteQuestionResponse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RequestBuilderTest {

	RequestBuilder rb;

	@BeforeEach
	void setUp() {

		rb = new RequestBuilder();

		rb.add("num", 1);
		rb.add("longNum", 10000L);
		rb.add("str", "testing12");
		rb.add("true", true);
		rb.add("false", false);
		rb.add("nothing", null);
		rb.add("obj", List.of(1, 2, 3, 4, 5));
	}

	@Test
	void build() {
		String result = rb.build();

		assertTrue(result.contains("\"num\":1"));
		assertTrue(result.contains("\"longNum\":10000"));

		assertTrue(result.contains("\"str\":\"testing12\""));

		assertTrue(result.contains("\"true\":true"));
		assertTrue(result.contains("\"false\":false"));

		assertTrue(result.contains("\"nothing\":null"));

		assertTrue(result.contains("\"obj\":[1,2,3,4,5]"));

	}
}