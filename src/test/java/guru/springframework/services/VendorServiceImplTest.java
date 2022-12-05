package guru.springframework.services;

import guru.springframework.api.v1.mapper.CustomerMapper;
import guru.springframework.api.v1.mapper.VendorMapper;
import guru.springframework.api.v1.model.CustomerDTO;
import guru.springframework.api.v1.model.VendorDTO;
import guru.springframework.controllers.v1.CustomerController;
import guru.springframework.controllers.v1.VendorController;
import guru.springframework.domain.Customer;
import guru.springframework.domain.Vendor;
import guru.springframework.repository.CustomerRepository;
import guru.springframework.repository.VendorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class VendorServiceImplTest {

    public static final Long ID = 2L;
    public static final String NAME = "Vendor name";

    VendorService vendorService;

    @Mock
    VendorRepository vendorRepository;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        vendorService = new VendorServiceImpl(vendorRepository, VendorMapper.INSTANCE);
    }

    @Test
    public void getAllVendors() throws Exception {

        //given
        List<Vendor> vendors = Arrays.asList(new Vendor(), new Vendor(), new Vendor());

        when(vendorRepository.findAll()).thenReturn(vendors);

        //when
        List<VendorDTO> vendorDTOS = vendorService.getAllVendors();

        //then
        assertEquals(3, vendorDTOS.size());

    }

    @Test
    public void getVendorById() throws Exception {

        //given
        Vendor vendor = new Vendor();
        vendor.setName(NAME);
        vendor.setId(ID);

        when(vendorRepository.findById(anyLong())).thenReturn(Optional.of(vendor));

        //when
        VendorDTO vendorDTO = vendorService.getVendorById(String.valueOf(ID));

        //then
        assertEquals(NAME, vendorDTO.getName());

    }

    @Test
    public void creatNewCustomer() throws Exception{

        //given
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName(NAME);

        Vendor vendorSaved = new Vendor();
        vendorSaved.setName(vendorDTO.getName());
        vendorSaved.setId(ID);

        when(vendorRepository.save(any(Vendor.class))).thenReturn(vendorSaved);

        //when
        VendorDTO savedDTO = vendorService.createNewVendor(vendorDTO);

        //then
        assertEquals(vendorDTO.getName(), savedDTO.getName());
        assertEquals(VendorController.BASE_URL+ID, savedDTO.getVendorURL());

    }

    @Test
    public void saveVendorByDTO() throws Exception{

        //given
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName(NAME);

        Vendor vendorSaved = new Vendor();
        vendorSaved.setName(vendorDTO.getName());
        vendorSaved.setId(ID);

        when(vendorRepository.save(any(Vendor.class))).thenReturn(vendorSaved);

        //when
        VendorDTO savedDTO = vendorService.saveVendorByDTO(ID,vendorDTO);

        //then
        assertEquals(vendorDTO.getName(), savedDTO.getName());
        assertEquals(VendorController.BASE_URL+ID, savedDTO.getVendorURL());

    }

    @Test
    public void deleteVendorById() throws Exception{
        Long id = 1L;

        vendorRepository.deleteById(id);

        verify(vendorRepository, times(1)).deleteById(anyLong());

    }

}