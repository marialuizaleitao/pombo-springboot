package com.maria.pombo.model.selector;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.Data;

@Data
public abstract class BaseSelector {
    private int page;
    private int limit;

    public BaseSelector() {
        this.limit = 0;
        this.page = 0;
    }

    public boolean hasPage() {
        return this.limit > 0 && this.page > 0;
    }

    public boolean stringValidator(String text) {
        return text != null && !text.isBlank();
    }

    public static void applyPeriodFilter(Root<?> root, CriteriaBuilder cb, List<Predicate> predicates,
                                            LocalDate startAt, LocalDate endAt, String string) {

        if (startAt != null && endAt != null) {
            predicates.add(cb.between(root.get(string), startAt, endAt));
        } else if (startAt != null) {
            predicates.add(cb.greaterThanOrEqualTo(root.get(string), startAt));
        } else if (endAt != null) {
            predicates.add(cb.lessThanOrEqualTo(root.get(string), endAt));
        }
    }
}
