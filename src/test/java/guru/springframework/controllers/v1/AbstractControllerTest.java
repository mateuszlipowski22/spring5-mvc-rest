package guru.springframework.controllers.v1;

import com.fasterxml.jackson.databind.ObjectMapper;
import guru.springframework.api.v1.model.CustomerDTO;
import guru.springframework.api.v1.model.VendorDTO;

public abstract class AbstractControllerTest {
	
	public static String asJsonStringCustomer(CustomerDTO customerDTO) {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			return objectMapper.writeValueAsString(customerDTO);
		} catch(Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static String asJsonStringVendor(VendorDTO vendorDTO) {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			return objectMapper.writeValueAsString(vendorDTO);
		} catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
}