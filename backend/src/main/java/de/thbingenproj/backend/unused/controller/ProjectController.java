package de.thbingenproj.backend.unused.controller;

import de.thbingenproj.backend.unused.controller.projections.ProjectInformation;
import de.thbingenproj.backend.unused.entities.prices.Project;
import de.thbingenproj.backend.unused.entities.prices.ProjectDTO;
import de.thbingenproj.backend.unused.entities.repositories.CustomerRepository;
import de.thbingenproj.backend.unused.entities.repositories.ProjectRepository;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.modelmapper.ModelMapper;
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
    @Autowired
    ModelMapper modelMapper;

    /**
     * @param id to read the Project Information
     * @return
     */
    @GetMapping("create/projects/{id}")
    public Project getProjects(@PathVariable long id) {
        return this.projectRepository.findById(id).orElse(null);
    }

    /**
     * @param id      put project
     * @param project project to save
     * @return
     */
    @PutMapping("create/projects/{id}")
    public Project putProjects(@PathVariable long id, @RequestBody ProjectDTO project) {
        project.setId(id);
        return this.projectRepository.save(modelMapper.map(project, Project.class));
    }

    /**
     * @param project project to create
     * @return saved project
     */
    @PostMapping("create/projects")
    public Project postProjects(@RequestBody ProjectDTO project) {
        return this.projectRepository.save(modelMapper.map(project, Project.class));
    }

    @GetMapping("/projectInformation")
    public List<ProjectInformation> getAllProjects(HttpServletRequest request) {
        KeycloakAuthenticationToken principal = (KeycloakAuthenticationToken) request.getUserPrincipal();
        String userId = principal.getAccount().getKeycloakSecurityContext().getToken().getSubject().intern();
        var customer = customerRepository.findCustomerByUserId(userId);
        if(customer == null){
            return new ArrayList<>();
        } else {
            List<ProjectInformation> projectInformation = new ArrayList<>();
            for (Project project : customer.getProjects()) {
                projectInformation.add(new ProjectInformation(project.getName(), project.getDescription(), project.getId()));
            }
            return projectInformation;
        }

    }
}
