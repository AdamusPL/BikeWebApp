package com.bikeparadise.bikewebapp;

import com.bikeparadise.bikewebapp.model.bike.Bike;
import com.bikeparadise.bikewebapp.model.roles.Client;
import com.bikeparadise.bikewebapp.model.roles.ShopAssistant;
import com.bikeparadise.bikewebapp.model.user.User;
import com.bikeparadise.bikewebapp.model.user.UserData;
import com.bikeparadise.bikewebapp.model.user.UserEmail;
import com.bikeparadise.bikewebapp.model.user.UserPhoneNumber;
import com.bikeparadise.bikewebapp.repository.bike.BikeRepository;
import com.bikeparadise.bikewebapp.repository.roles.ClientRepository;
import com.bikeparadise.bikewebapp.repository.user.UserRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.Cookie;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class IntegrationTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private BikeRepository bikeRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ClientRepository clientRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

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
				.andExpect(status().is(200));
	}

	@Test
	@Transactional
	void roleUserSignInAndEndpointsTest() throws Exception {
		User user = new User("test12", passwordEncoder.encode("test1234"));
		Client client = new Client();
		clientRepository.save(client);

		UserData userData = new UserData("Test", "Test");
		userData.setClient(client);

		UserPhoneNumber userPhoneNumber = new UserPhoneNumber("123456789", userData);
		UserEmail userEmail = new UserEmail("a@g", userData);

		userData.setUserEmail(new ArrayList<>(List.of(userEmail)));
		userData.setUserPhoneNumber(new ArrayList<>(List.of(userPhoneNumber)));

		user.setUserData(userData);
		userRepository.save(user);

		MvcResult result = mockMvc.perform(post("/sign-in")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"username\": \"test12\", \"password\": \"test1234\"}"))
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
