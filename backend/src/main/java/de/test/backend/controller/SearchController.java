package de.test.backend.controller;

import de.test.backend.entities.prices.Project;
import de.test.backend.entities.ressources.ResourceType;
import de.test.backend.entities.user.Customer;
import de.test.backend.entities.user.contact_information.ContactInformation;
import de.test.backend.services.SearchResult;
import de.test.backend.services.SearchService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * Basic Controller for all elastic-search queries
 */
@RestController
public class SearchController {
    SearchService searchService;

    SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    /**
     * @param query query string for full text search
     * @param from  pagination
     * @param size  pagination
     * @return SearchResults
     */
    @GetMapping(path = "/search/customers/{query}/{from}/{size}")
    public SearchResult<Customer> getCustomers(@PathVariable String query, @PathVariable int from, @PathVariable int size) {
        return this.searchService.getUserOnQuery(query, from, size);
    }

    /**
     * @param query query string for full text search
     * @param from  pagination
     * @param size  pagination
     * @return SearchResults
     */
    @GetMapping(path = "/search/contact_information/{query}/{from}/{size}")
    public SearchResult<ContactInformation> getContactInformations(@PathVariable String query, @PathVariable int from, @PathVariable int size) {
        return this.searchService.getContactInformations(query, from, size);
    }

    /**
     * @param query query string for full text search
     * @param from  pagination
     * @param size  pagination
     * @return SearchResults
     */
    @GetMapping(path = "/search/projects/{query}/{from}/{size}")
    public SearchResult<Project> getProjects(@PathVariable String query, @PathVariable int from, @PathVariable int size) {
        return this.searchService.getProjects(query, from, size);
    }

    /**
     * @param query query string for full text search
     * @param from  pagination
     * @param size  pagination
     * @return SearchResults
     */
    @GetMapping(path = "/search/resourcetypes/{query}/{from}/{size}")
    public SearchResult<ResourceType> getResourceType(@PathVariable String query, @PathVariable int from, @PathVariable int size) {
        return this.searchService.getResourceType(query, from, size);
    }

}
