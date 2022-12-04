package guru.springframework.controllers.v1;

import com.fasterxml.jackson.databind.ObjectMapper;
import guru.springframework.api.v1.model.CustomerDTO;

public abstract class AbstractControllerTest {
	
	public static String asJsonString(CustomerDTO customerDTO) {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			return objectMapper.writeValueAsString(customerDTO);
		} catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
}