package guru.springframework.services;

import guru.springframework.api.v1.model.CustomerDTO;
import guru.springframework.api.v1.model.VendorDTO;
import org.springframework.stereotype.Service;

import java.util.List;

public interface VendorService {

    List<VendorDTO> getAllVendors();

    VendorDTO getVendorById(String id);

    VendorDTO createNewVendor(VendorDTO vendorDTO);

    VendorDTO saveVendorByDTO(Long id, VendorDTO vendorDTO);

    VendorDTO patchVendor(Long id, VendorDTO vendorDTO);

    void deleteVendorById(Long id);

}
