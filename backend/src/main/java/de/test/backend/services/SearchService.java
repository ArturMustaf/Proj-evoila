package de.test.backend.services;

import de.test.backend.entities.prices.Project;
import de.test.backend.entities.ressources.ResourceType;
import de.test.backend.entities.user.Customer;
import de.test.backend.entities.user.contact_information.ContactInformation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.lucene.search.Query;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
/**
 * Implementation of fulltext search requests over diffrent Spring Entities
 */
public class SearchService {

    private final EntityManager entityManager;

    /**
     * @param query string which should be searched
     * @param from  pagination
     * @param size  pagination
     * @return results with pagination
     */
    public SearchResult<Customer> getUserOnQuery(String query, int from, int size) {
        var fullTextEntityManager =
                Search.getFullTextEntityManager(entityManager);
        QueryBuilder qb = fullTextEntityManager
                .getSearchFactory()
                .buildQueryBuilder()
                .forEntity(Customer.class)
                .get();

        if (query == null || Objects.equals(query, "") || Objects.equals(query, " ")) {
            var fullTextQuery = fullTextEntityManager
                    .createFullTextQuery(qb.all().createQuery(), Customer.class);
            fullTextQuery.setSort(qb.sort().byScore().createSort());
            fullTextQuery.setFirstResult(from);
            fullTextQuery.setMaxResults(size);
            List<Customer> result = fullTextQuery.getResultList();
            result.forEach(customer -> customer.setProjects(null));
            return new SearchResult<>(result, fullTextQuery.getResultSize());
        }
        final String contactEmail = "contactInformation.email";
        final String contactNumber = "contactInformation.numbers.number";
        final String contactPLZ = "contactInformation.address.plz";
        final String contactCountry = "contactInformation.address.country.countryName";
        final String contactFullName = "fullName";
        final String contactCity = "contactInformation.address.city";
        final String contactAddress = "contactInformation.address.address";
        var allQuery = qb.simpleQueryString().onFields(contactEmail, contactNumber, contactPLZ, contactCity, contactCountry, contactAddress, contactFullName)
                .boostedTo(8f).withAndAsDefaultOperator().matching(query).createQuery();
        var mailQuery = qb.keyword()
                .onFields(contactEmail, contactNumber)
                .matching(query).createQuery();
        var addressExact = qb.keyword()
                .onFields(contactPLZ, contactCity, contactCountry)
                .matching(query).createQuery();
        var address = qb.keyword().fuzzy().withEditDistanceUpTo(2).withPrefixLength(9).boostedTo(3f)
                .onFields(contactAddress)
                .matching(query).createQuery();
        var nameQuery = qb.keyword().fuzzy().withPrefixLength(5).withEditDistanceUpTo(2)
                .boostedTo(5f).onField(contactFullName).matching(query).createQuery();

        var phraseQuery = qb.phrase().withSlop(5).onField(contactFullName).andField(contactAddress)
                .andField(contactPLZ).andField(contactEmail).andField(contactPLZ)
                .andField(contactCity).andField(contactCountry).sentence(query).createQuery();

        var searchQuery = qb.bool()
                .should(mailQuery).should(nameQuery)
                .should(addressExact).should(address)
                .should(phraseQuery).should(allQuery).minimumShouldMatchNumber(1).createQuery();


        var fullTextQuery = fullTextEntityManager
                .createFullTextQuery(searchQuery, Customer.class);
        fullTextQuery.setSort(qb.sort().byScore().createSort());
        fullTextQuery.setFirstResult(from);
        fullTextQuery.setMaxResults(size);

        List<Customer> result = fullTextQuery.getResultList();
        result.forEach(customer -> customer.setProjects(null));

        return new SearchResult<>(fullTextQuery.getResultList(), fullTextQuery.getResultSize());
    }

    /**
     * @param query string which should be searched
     * @param from  pagination
     * @param size  pagination
     * @return results with pagination
     */
    public SearchResult<Project> getProjects(String query, int from, int size) {
        var fullTextEntityManager =
                Search.getFullTextEntityManager(entityManager);

        QueryBuilder qb = fullTextEntityManager
                .getSearchFactory()
                .buildQueryBuilder()
                .forEntity(Project.class)
                .get();
        if (query == null || Objects.equals(query, "") || Objects.equals(query, " ")) {
            var fullTextQuery = fullTextEntityManager
                    .createFullTextQuery(qb.all().createQuery(), Project.class);
            fullTextQuery.setSort(qb.sort().byScore().createSort());
            fullTextQuery.setFirstResult(from);
            fullTextQuery.setMaxResults(size);
            List<Project> result = fullTextQuery.getResultList();
            result.forEach(project ->
                project.setLogs(null)
            );
            return new SearchResult<>(result, fullTextQuery.getResultSize());
        }

        var searchProjektName = qb.keyword().fuzzy().withEditDistanceUpTo(2).boostedTo(5f)
                .onFields("name")
                .matching(query).createQuery();
        var searchQuery = qb.keyword()
                .fuzzy().withEditDistanceUpTo(1).onFields("description")
                .matching(query)
                .createQuery();
        var mixedQuery = qb.bool().should(searchQuery).should(searchProjektName).createQuery();
        var fullTextQuery = fullTextEntityManager
                .createFullTextQuery(mixedQuery, Project.class);
        fullTextQuery.setSort(qb.sort().byScore().createSort());
        fullTextQuery.setFirstResult(from);
        fullTextQuery.setMaxResults(size);
        List<Project> result = fullTextQuery.getResultList();
        result.forEach(project ->
            project.setLogs(null)
        );
        return new SearchResult<>(result, fullTextQuery.getResultSize());
    }

    /**
     * @param query string which should be searched
     * @param from  pagination
     * @param size  pagination
     * @return results with pagination
     */
    public SearchResult<ContactInformation> getContactInformations(String query, int from, int size) {
        var fullTextEntityManager =
                Search.getFullTextEntityManager(entityManager);

        QueryBuilder qb = fullTextEntityManager
                .getSearchFactory()
                .buildQueryBuilder()
                .forEntity(ContactInformation.class)
                .get();
        if (query == null || Objects.equals(query, "") || Objects.equals(query, " ")) {
            var fullTextQuery = fullTextEntityManager
                    .createFullTextQuery(qb.all().createQuery(), ContactInformation.class);
            fullTextQuery.setSort(qb.sort().byScore().createSort());
            fullTextQuery.setFirstResult(from);
            fullTextQuery.setMaxResults(size);

            return new SearchResult<>((List<ContactInformation>) fullTextQuery.getResultList(), fullTextQuery.getResultSize());
        }
        var dataQuery = qb.keyword()
                .onFields("email", "numbers.number")
                .matching(query).createQuery();
        var addressExact = qb.keyword()
                .onFields("address.plz"
                        , "address.city", "address.country.countryName")
                .matching(query).createQuery();

        var address = qb.keyword().fuzzy().withEditDistanceUpTo(2).withPrefixLength(9).boostedTo(3f)
                .onFields("address.address")
                .matching(query).createQuery();


        var searchQuery = qb.bool().should(addressExact).should(address).should(dataQuery).createQuery();

        var fullTextQuery = fullTextEntityManager
                .createFullTextQuery(searchQuery, ContactInformation.class);
        fullTextQuery.setSort(qb.sort().byScore().createSort());
        fullTextQuery.setFirstResult(from);
        fullTextQuery.setMaxResults(size);

        return new SearchResult<>((List<ContactInformation>) fullTextQuery.getResultList(), fullTextQuery.getResultSize());
    }

    /**
     * @param query string which should be searched
     * @param from  pagination
     * @param size  pagination
     * @return results with pagination
     */
    public SearchResult<ResourceType> getResourceType(String query, int from, int size) {
        var fullTextEntityManager =
                Search.getFullTextEntityManager(entityManager);

        QueryBuilder qb = fullTextEntityManager
                .getSearchFactory()
                .buildQueryBuilder()
                .forEntity(ResourceType.class)
                .get();
        if (query == null || Objects.equals(query, "") || Objects.equals(query, " ")) {
            var fullTextQuery = fullTextEntityManager
                    .createFullTextQuery(qb.all().createQuery(), ResourceType.class);
            fullTextQuery.setSort(qb.sort().byScore().createSort());
            fullTextQuery.setFirstResult(from);
            fullTextQuery.setMaxResults(size);

            return new SearchResult<>((List<ResourceType>) fullTextQuery.getResultList(), fullTextQuery.getResultSize());
        }
        var dataQuery = qb.keyword()
                .onFields("type", "shortDescription", "fullDescription")
                .matching(query).createQuery();

        var searchQuery = qb.bool().should(dataQuery).createQuery();

        var fullTextQuery = fullTextEntityManager
                .createFullTextQuery(searchQuery, ResourceType.class);
        fullTextQuery.setSort(qb.sort().byScore().createSort());
        fullTextQuery.setFirstResult(from);
        fullTextQuery.setMaxResults(size);

        return new SearchResult<>((List<ResourceType>) fullTextQuery.getResultList(), fullTextQuery.getResultSize());
    }

}
