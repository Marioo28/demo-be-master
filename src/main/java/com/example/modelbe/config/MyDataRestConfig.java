package com.example.modelbe.config;

import com.example.modelbe.entity.Country;
import com.example.modelbe.entity.Product;
import com.example.modelbe.entity.ProductCategory;
import com.example.modelbe.entity.State;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

@Configuration
public class MyDataRestConfig implements RepositoryRestConfigurer {

    private EntityManager entityManager;

    @Autowired
    public MyDataRestConfig(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
        //List containing restricted HTTP request
        HttpMethod[] theUnsuportedActions = {HttpMethod.PUT, HttpMethod.POST, HttpMethod.DELETE};

        //disable HTTP methods for each Repository Rest Resource: Product, ProductCategory, State, COuntry
        disableHttpMethods(Product.class, config, theUnsuportedActions);
        disableHttpMethods(ProductCategory.class, config, theUnsuportedActions);
        disableHttpMethods(State.class, config, theUnsuportedActions);
        disableHttpMethods(Country.class, config, theUnsuportedActions);


    }

    private void disableHttpMethods(Class theClass, RepositoryRestConfiguration config, HttpMethod[] theUnsuportedActions) {
        config.getExposureConfiguration()
                .forDomainType(theClass)
                .withItemExposure(((metdata, httpMethods) -> httpMethods.disable(theUnsuportedActions)))
                .withCollectionExposure(((metdata, httpMethods) -> httpMethods.disable(theUnsuportedActions)));
    }
}