package com.JWTAuthentication.controller;

import com.JWTAuthentication.Dto.GetCustomerListDTO;
import com.JWTAuthentication.Dto.ResponseDTO;
import com.JWTAuthentication.entity.Customer;
import com.JWTAuthentication.service.CustomerService;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class Home {

    @Autowired
    private CustomerService customerService;

//    @GetMapping("/login")
//    public String login() {
//        return "login";
//    }

//    @RequestMapping("/welcome")
//    public String welcome(){
//        String text = "this is private page";
//        text += "this page is not allowed to unauthenticated users";
//        return text;
//    }

//    @RequestMapping("/getusers")
//    public String user(){
//        return "{\"name\":\"Mayank\"}";
//    }

    @PostMapping("/createCustomer")
    public ResponseEntity<ResponseDTO> createCustomer(@RequestBody Customer customer){
        return this.customerService.createCustomer(customer);
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<ResponseDTO> updateCustomer(@RequestBody Customer customer, @PathVariable int id) throws Exception {
        return this.customerService.updateCustomer(customer,id);
    }

    @GetMapping("/getCustomer")
    public ResponseEntity<GetCustomerListDTO> getAllCustomer(){
        return this.customerService.getAllCustomers();
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<ResponseDTO> deleteCustomer(@PathVariable int id){
        return this.customerService.deleteCustomer(id);
    }






    @RequestMapping(value = "/customer", method = RequestMethod.GET)
//    @GetMapping("/admin/Customer")
    public String getCustomer(Model model) {
        ResponseEntity<GetCustomerListDTO>  obj = customerService.getAllCustomers();
//        JSON
//        JSONPObject myResponse = new JSONPObject(obj.getBody());
        model.addAttribute("customers", customerService.getAllCustomers());
        return "Customer";
    }

//    @GetMapping("/admin/Customer/add")
//    public String getAddCustomer(Model model) {
//        Customer customer = new Customer();
//        model.addAttribute("customer.fname", customer.getFirst_name());
//        model.addAttribute("customer.lname", customer.getLast_name());
//        model.addAttribute("customer.address", customer.getAddress());
//        model.addAttribute("customer.city", customer.getCity());
//        model.addAttribute("customer.state", customer.getState());
//        model.addAttribute("customer.email", customer.getEmail());
//        model.addAttribute("customer.phone", customer.getPhone());
//        return "customerAdd";
//    }
//
    @PostMapping("/customer/add")
    public String postAddCustomer(@ModelAttribute("customer") Customer customer) {
        System.out.println(customer.toString());
        customerService.createCustomer(customer);
        return "redirect:/Customer";
    }
//
//    @GetMapping("/admin/customer/delete/{id}")
//    public String deletecategory(@PathVariable Integer id) {
//        customerService.deleteCustomer(id);
//        return "redirect:/admin/Customer";
//    }
//
//    @GetMapping("/admin/customer/update/{id}")
//    public String updateCategory(@PathVariable int id, Model model) {
//        Optional<Customer> customer = customerService.getCustomer(id);
//        if(customer.isPresent()) {
//            model.addAttribute("customer", customer.get());
//            return "customerAdd";
//        }
//        return "404";
//    }


}
