package de.thbingenproj.backend.controller;

import de.thbingenproj.backend.controller.projections.ProjectInformation;
import de.thbingenproj.backend.entities.prices.Project;
import de.thbingenproj.backend.entities.repositories.CustomerRepository;
import de.thbingenproj.backend.entities.repositories.ProjectRepository;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
public class ProjectController {
    @Autowired
    ProjectRepository projectRepository;
    @Autowired
    CustomerRepository customerRepository;

    /**
     * @param id to read the Project Information
     * @return
     */
    @GetMapping("create/projects/{id}")
    public Project getContactInformations(@PathVariable long id) {
        return this.projectRepository.findById(id).orElse(null);
    }

    /**
     * @param id      put project
     * @param project project to save
     * @return
     */
    @PutMapping("create/projects/{id}")
    public Project putProjects(@PathVariable long id, @RequestBody Project project) {
        project.setId(id);
        return this.projectRepository.save(project);
    }

    /**
     * @param project project to create
     * @return saved project
     */
    @PostMapping("create/projects")
    public Project postProjects(@RequestBody Project project) {
        return this.projectRepository.save(project);
    }

    @GetMapping("/projectInformations")
    public List<ProjectInformation> getProjects(HttpServletRequest request) {
        KeycloakAuthenticationToken principal = (KeycloakAuthenticationToken) request.getUserPrincipal();
        String userId = principal.getAccount().getKeycloakSecurityContext().getToken().getSubject().intern();
        var customer = customerRepository.findCustomerByUserId(userId);
        List<ProjectInformation> projectInformations = new ArrayList<>();
        for (Project project : customer.getProjects()) {
            projectInformations.add(new ProjectInformation(project.getName(), project.getDescription(), project.getId()));
        }
        return projectInformations;
    }
}
