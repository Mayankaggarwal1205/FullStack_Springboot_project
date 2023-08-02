package com.JWTAuthentication.service;

import com.JWTAuthentication.Dto.GetCustomerListDTO;
import com.JWTAuthentication.Dto.ResponseDTO;
import com.JWTAuthentication.entity.Customer;
import com.JWTAuthentication.repo.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepo customerRepo;

    public ResponseEntity<ResponseDTO> createCustomer(Customer customer){
        ResponseDTO responseDTO = new ResponseDTO();
        if(customer.getFirst_name() == null || customer.getLast_name() == null || customer.getFirst_name().isEmpty()
        || customer.getLast_name().isEmpty()){
            responseDTO.setResponseCode(400);
            responseDTO.setResponseMessage("First Name or Last Name is missing");
            return new ResponseEntity<>(responseDTO,HttpStatus.BAD_REQUEST);
        }
        Customer save = this.customerRepo.save(customer);
        if(save != null){
            responseDTO.setResponseCode(201);
            responseDTO.setResponseMessage("Successfully Created");
            return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
        }
        return null;
    }

    public ResponseEntity<ResponseDTO> updateCustomer(Customer customer, int customerId) throws Exception{
        ResponseDTO responseDTO = new ResponseDTO();
        if(customer == null || (
                customer.getFirst_name() == null && customer.getLast_name() == null &&
                        customer.getStreet()==null &&  customer.getAddress()==null &&
                        customer.getCity()==null &&  customer.getState()==null && customer.getEmail()==null
                        && customer.getPhone()==null
                )){
            responseDTO.setResponseCode(400);
            responseDTO.setResponseMessage("Body is Empty");
            return new ResponseEntity<>(responseDTO,HttpStatus.BAD_REQUEST);
        }

        Optional<Customer> customer2 = this.customerRepo.findById(customerId);
        if(customer2 == null || customer2.isEmpty()){
            responseDTO.setResponseCode(500);
            responseDTO.setResponseMessage("UUID not found");
            return new ResponseEntity<>(responseDTO,HttpStatus.BAD_REQUEST);
        }

        Customer customer1 = customer2.get();
        customer1.setEmail(customer.getEmail());
        customer1.setCity(customer.getCity());
        customer1.setAddress(customer.getAddress());
        customer1.setPhone(customer.getPhone());
        customer1.setState(customer.getState());
        customer1.setFirst_name(customer.getFirst_name());
        customer1.setLast_name(customer.getLast_name());
        customer1.setStreet(customer.getStreet());
        Customer updated = this.customerRepo.save(customer1);
        responseDTO.setResponseCode(200);
        responseDTO.setResponseMessage("Successfully updated");
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    public ResponseEntity<ResponseDTO> deleteCustomer(int customerId){
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            Optional<Customer> customer2 = this.customerRepo.findById(customerId);
            if (customer2 == null || customer2.isEmpty()) {
                responseDTO.setResponseCode(400);
                responseDTO.setResponseMessage("UUID not found");
                return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
            }
            this.customerRepo.deleteById(customerId);
            responseDTO.setResponseCode(200);
            responseDTO.setResponseMessage("Successfully deleted");
        } catch(Exception e){
            responseDTO.setResponseCode(500);
            responseDTO.setResponseMessage("Error not deleted");
        }
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    public Optional<Customer> getCustomer(int customerId){
        return this.customerRepo.findById(customerId);
    }

    public ResponseEntity<GetCustomerListDTO> getAllCustomers(){
        GetCustomerListDTO getCustomerListDTO = new GetCustomerListDTO();
        getCustomerListDTO.setResponseCode(200);
        List<Customer> all = this.customerRepo.findAll();
        getCustomerListDTO.setResponseMessage(all);
        return new ResponseEntity<>(getCustomerListDTO,HttpStatus.OK);
    }


}
