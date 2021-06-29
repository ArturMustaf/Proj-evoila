package de.thbingenproj.backend.controller.keycloak;

import de.thbingenproj.backend.entities.repositories.CustomerRepository;
import de.thbingenproj.backend.entities.user.Customer;
import de.thbingenproj.backend.entities.user.CustomerDTO;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

@RestController
public class Authenticator {
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    ModelMapper modelMapper;

    @Transactional
    @PostMapping("/auth/update")
    public Customer updateUserData(@RequestBody CustomerDTO customer, HttpServletRequest request) {
        KeycloakAuthenticationToken principal = (KeycloakAuthenticationToken) request.getUserPrincipal();
        String userId = principal.getAccount().getKeycloakSecurityContext().getToken().getSubject().intern();
        customer.setUserId(userId);
        var customerObj = customerRepository.findCustomerByUserId(userId);
        if (customerObj == null) {
            return customerRepository.save(modelMapper.map(customer, Customer.class));
        }
        // update
        customerObj.setName(customer.getName());
        customerObj.setSurname(customer.getSurname());
        customerObj.setContactInformation(customer.getContactInformation());

        return customerRepository.save(customerObj);

    }


    @Transactional
    @GetMapping("/auth/user")
    public Customer getCustomer(HttpServletRequest request) {
        KeycloakAuthenticationToken principal = (KeycloakAuthenticationToken) request.getUserPrincipal();
        String userId = principal.getAccount().getKeycloakSecurityContext().getToken().getSubject().intern();
        return customerRepository.findCustomerByUserId(userId);
    }


}
