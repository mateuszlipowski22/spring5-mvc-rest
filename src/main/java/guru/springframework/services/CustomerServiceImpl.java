package guru.springframework.services;

import guru.springframework.api.v1.mapper.CustomerMapper;
import guru.springframework.api.v1.model.CustomerDTO;
import guru.springframework.domain.Customer;
import guru.springframework.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class CustomerServiceImpl implements CustomerService{

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @Override
    public List<CustomerDTO> getAllCustomers() {
        return customerRepository.findAll()
                .stream()
                .map(customer -> {
                    CustomerDTO customerDTO = customerMapper.customerToCustomerDTO(customer);
                    customerDTO.setCustomerURL("/api/v1/customer/" + customer.getId());
                    return customerDTO;
                })
                .collect(Collectors.toList());
    }

    @Override
    public CustomerDTO getCustomerById(String id) {
        return customerMapper
                .customerToCustomerDTO(customerRepository
                        .findById(Long.valueOf(id))
                        .orElseThrow(RuntimeException::new));
    }

    @Override
    public CustomerDTO createNewCustomer(CustomerDTO customerDTO) {

        Customer customerToSave = customerMapper.customerDTOToCustomer(customerDTO);
        return createAndSave(customerToSave);
    }

    @Override
    public CustomerDTO saveCustomerByDTO(Long id, CustomerDTO customerDTO) {
        Customer customerToSave = customerMapper.customerDTOToCustomer(customerDTO);
        customerToSave.setId(id);
        return createAndSave(customerToSave);
    }

    private CustomerDTO createAndSave(Customer customerToSave) {
        Customer customerSaved = customerRepository.save(customerToSave);
        CustomerDTO returnedDTO = customerMapper.customerToCustomerDTO(customerSaved);
        returnedDTO.setCustomerURL("/api/v1/customer/" + customerSaved.getId());
        return returnedDTO;
    }

    @Override
    public CustomerDTO patchCustomer(Long id, CustomerDTO customerDTO) {
        return customerRepository.findById(id)
                .map(customer -> {
                    if(customerDTO.getFirstname()!=null){
                        customer.setFirstname(customerDTO.getFirstname());
                    }
                    if(customerDTO.getLastname()!=null){
                        customer.setLastname(customerDTO.getLastname());
                    }
                    return customerMapper.customerToCustomerDTO(customerRepository.save(customer));
                }).orElseThrow(RuntimeException::new);
    }
}
