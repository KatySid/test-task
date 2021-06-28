package com.haulmont.testtask.repositories;

import com.haulmont.testtask.models.Credit;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.MultiValueMap;


public class CreditSpecifications {

    private static Specification<Credit> limitationLesserOrEqualsThan(int maxLimitation) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("limitation"), maxLimitation );
    }

    private static Specification<Credit> limitationGreaterThanOrEqualTo(int minLimitation) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("limitation"), minLimitation);
    }

    private static Specification<Credit> percentLesserOrEqualsThan(int maxPercent) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("percent"), maxPercent );
    }

    private static Specification<Credit> percentGreaterThanOrEqualTo(int minPercent) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("percent"), minPercent);
    }


    public static Specification<Credit> build(MultiValueMap<String, String> params) {
        Specification<Credit> spec = Specification.where(null);

        if (params.containsKey("min_limitation") && !params.getFirst("min_limitation").isBlank()) {
            spec = spec.and(CreditSpecifications.limitationGreaterThanOrEqualTo(Integer.parseInt(params.getFirst("min_limitation"))));
        }
        if (params.containsKey("max_limitation") && !params.getFirst("max_limitation").isBlank()) {
            spec = spec.and(CreditSpecifications.limitationLesserOrEqualsThan(Integer.parseInt(params.getFirst("max_limitation"))));
        }
        if (params.containsKey("min_percent") && !params.getFirst("min_percent").isBlank()) {
            spec = spec.and(CreditSpecifications.percentGreaterThanOrEqualTo(Integer.parseInt(params.getFirst("min_percent"))));
        }
        if (params.containsKey("max_percent") && !params.getFirst("max_percent").isBlank()) {
            spec = spec.and(CreditSpecifications.percentLesserOrEqualsThan(Integer.parseInt(params.getFirst("max_percent"))));
        }
        return spec;
    }

}
