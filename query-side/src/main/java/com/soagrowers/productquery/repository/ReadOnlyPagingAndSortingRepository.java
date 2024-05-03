package com.soagrowers.productquery.repository;


import com.soagrowers.productquery.domain.Product;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RestResource;

/**
 * offers save and delete methods for Product entities with restrictions on the
 * capabilities offered through the use of exported = false annotations in the
 * RestResource annotations.
 */
@NoRepositoryBean
public interface ReadOnlyPagingAndSortingRepository extends PagingAndSortingRepository<Product, String> {

    @Override
    @SuppressWarnings("unchecked")
    @RestResource(exported = false)//true means the capability will be offered
    Product save(Product entity);

    @Override
    @RestResource(exported = false)//false restricts the capability
    void delete(String aLong);

    @Override
    @RestResource(exported = false)
    void delete(Product entity);
}
