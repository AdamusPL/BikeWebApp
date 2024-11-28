package com.bikeparadise.bikewebapp;

import com.bikeparadise.bikewebapp.model.Bike;
import com.bikeparadise.bikewebapp.model.ShopAssistant;
import com.bikeparadise.bikewebapp.repository.BikeRepository;
import com.bikeparadise.bikewebapp.service.BikeService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.Cookie;
import jakarta.transaction.Transactional;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class IntegrationTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private BikeService bikeService;

	@Autowired
	private BikeRepository bikeRepository;

	@Test
	@Transactional
	void getDetailedInfoAboutBike() throws Exception {
		String modelName = "Swift 4";
		BigDecimal price = BigDecimal.valueOf(1999.99);
		String description = "3x7 bike, best for people who want to start a story with MTB";
		ShopAssistant shopAssistant = new ShopAssistant();

		Bike bike = new Bike(modelName, price, description, shopAssistant);

		bikeRepository.save(bike);

		mockMvc.perform(get("/get-detailed-info-about-bike?bikeId=1"))
				.andDo(print())
				.andExpect(status().is(200))
				.andExpect(jsonPath("$.fullModelName", Matchers.is(" Swift 4")));
	}

	@Test
	void roleUserSignInAndEndpointsTest() throws Exception {
		MvcResult result = mockMvc.perform(post("/sign-in")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"username\": \"test\", \"password\": \"test1234\"}"))
				.andDo(print())
				.andExpect(status().is(200))
				.andReturn();

		String token = result.getResponse().getContentAsString();
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode jsonNode = objectMapper.readTree(token);
		String accessToken = jsonNode.get("accessToken").asText();

		Cookie cookie = new Cookie("token", accessToken);

		mockMvc.perform(get("/get-user-data")
				.cookie(cookie))
				.andDo(print())
				.andExpect(status().is(200))
				.andReturn();
	}

}
