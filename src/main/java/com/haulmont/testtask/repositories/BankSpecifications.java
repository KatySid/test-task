package com.haulmont.testtask.repositories;

import com.haulmont.testtask.models.Bank;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.MultiValueMap;


public class BankSpecifications {

    private static Specification<Bank> titleLike(String titlePart) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(root.get("title"), String.format("%%%s%%", titlePart));
    }


    public static Specification<Bank> build(MultiValueMap<String, String> params) {
        Specification<Bank> spec = Specification.where(null);

        if (params.containsKey("title") && !params.getFirst("title").isBlank()) {
            spec = spec.and(BankSpecifications.titleLike(params.getFirst("title")));
        }
        return spec;
    }

}
