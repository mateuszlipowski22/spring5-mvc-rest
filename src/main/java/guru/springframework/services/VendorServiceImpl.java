package guru.springframework.services;

import guru.springframework.api.v1.mapper.VendorMapper;
import guru.springframework.api.v1.model.CustomerDTO;
import guru.springframework.api.v1.model.VendorDTO;
import guru.springframework.controllers.v1.VendorController;
import guru.springframework.domain.Customer;
import guru.springframework.domain.Vendor;
import guru.springframework.repository.VendorRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class VendorServiceImpl implements VendorService{

    private final VendorRepository vendorRepository;
    private final VendorMapper vendorMapper;

    @Override
    public List<VendorDTO> getAllVendors() {
        return vendorRepository.findAll()
                .stream().map(vendor -> {
                    VendorDTO vendorDTO=vendorMapper.vendorToVendorDTO(vendor);
                    vendorDTO.setVendorURL(getVendorUrl(vendor.getId()));
                    return vendorDTO;
                })
                .collect(Collectors.toList());
    }

    @Override
    public VendorDTO getVendorById(String id) {
        return vendorMapper.vendorToVendorDTO(vendorRepository
                        .findById(Long.valueOf(id))
                        .orElseThrow(ResourceNotFoundException::new));
    }

    @Override
    public VendorDTO createNewVendor(VendorDTO vendorDTO) {
        Vendor vendorToSave = vendorMapper.vendorDTOToVendor(vendorDTO);
        return createAndSave(vendorToSave);
    }

    @Override
    public VendorDTO saveVendorByDTO(Long id, VendorDTO vendorDTO) {
        Vendor vendorToSave = vendorMapper.vendorDTOToVendor(vendorDTO);
        vendorToSave.setId(id);
        return createAndSave(vendorToSave);
    }

    @Override
    public VendorDTO patchVendor(Long id, VendorDTO vendorDTO) {
        return vendorRepository.findById(id)
                .map(vendor -> {
                    if(vendorDTO.getName()!=null){
                        vendor.setName(vendorDTO.getName());
                    }

                    VendorDTO returnDTO = vendorMapper.vendorToVendorDTO(vendorRepository.save(vendor));
                    returnDTO.setVendorURL(getVendorUrl(id));

                    return returnDTO;
                }).orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public void deleteVendorById(Long id) {
        vendorRepository.deleteById(id);
    }

    private String getVendorUrl(Long id) {
        return VendorController.BASE_URL+id;
    }

    private VendorDTO createAndSave(Vendor vendorToSave) {
        Vendor vendorSaved = vendorRepository.save(vendorToSave);
        VendorDTO returnedDTO = vendorMapper.vendorToVendorDTO(vendorSaved);
        returnedDTO.setVendorURL(getVendorUrl(vendorSaved.getId()));
        return returnedDTO;
    }
}
