package de.test.backend.controller.keycloak;

import de.test.backend.entities.repositories.CustomerRepository;
import de.test.backend.entities.user.Customer;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

@RestController
public class Authentificator {
    @Autowired
    CustomerRepository customerRepository;

    @Transactional
    @PostMapping("/auth/update")
    Customer updateUserData(@RequestBody Customer customer, HttpServletRequest request) {
        KeycloakAuthenticationToken principal = (KeycloakAuthenticationToken) request.getUserPrincipal();
        String userId = principal.getAccount().getKeycloakSecurityContext().getToken().getSubject().intern();
        customer.setUserId(userId);
        Customer customerObj = customerRepository.findCustomerByUserId(userId);
        if (customerObj == null) {
            return customerRepository.save(customer);
        }
        // update
        customerObj.setName(customer.getName());
        customerObj.setSurname(customer.getSurname());
        customerObj.setContactInformation(customer.getContactInformation());

        return customerRepository.save(customerObj);

    }

    @Transactional
    @GetMapping("/auth/user")
    Customer getCustomer(HttpServletRequest request) {
        KeycloakAuthenticationToken principal = (KeycloakAuthenticationToken) request.getUserPrincipal();
        String userId = principal.getAccount().getKeycloakSecurityContext().getToken().getSubject().intern();
        Customer customer = customerRepository.findCustomerByUserId(userId);

        return customer;
    }


}
