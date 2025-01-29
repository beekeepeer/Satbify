package com.DAWIntegration.Satbify;

import com.DAWIntegration.Satbify.module.RequestDeserialized;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.core.io.Resource;

import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@JsonTest
class SatbifyApplicationTests {

	@Value("classpath:data/incomingDataExample.json")
	Resource jsonFromReaper;

	@Autowired
	ObjectMapper objectMapper;

	String json;

	@Test
	void contextLoads() {
		assertNotNull(objectMapper);
	}

	@BeforeEach
	void setUp() throws Exception {
		json = new String(Files.readAllBytes(jsonFromReaper.getFile().toPath()));
		objectMapper.readValue(json, RequestDeserialized.class);
	}

}
