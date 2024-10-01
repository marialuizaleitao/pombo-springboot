package com.maria.pombo.model.selector;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.maria.pombo.model.entity.Message;
import lombok.EqualsAndHashCode;
import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.Data;

@EqualsAndHashCode(callSuper = true)
@Data
public class MessageSelector extends BaseSelector implements Specification<Message> {

    private String text;
    private String userId;
    private LocalDate startAt;
    private LocalDate endAt;

    public boolean hasFilters() {
        return (stringValidator(this.text))
                || (stringValidator(this.userId))
                || (startAt != null)
                || (endAt != null)
                ;
    }

    @Override
    public Predicate toPredicate(Root<Message> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        List<Predicate> filters = new ArrayList<>();

        if(this.getText() != null && !this.getText().trim().isEmpty()) {
            filters.add(cb.like(root.get("text"), "%" + this.getText() + "%"));
        }

        if(this.getUserId() != null && !this.getUserId().trim().isEmpty()) {
            filters.add(cb.like(root.get("user").get("id"), "%" + this.getUserId() + "%"));
        }

        applyPeriodFilter(root, cb, filters, this.getStartAt(), this.getEndAt(), "createdAt");

        return cb.and(filters.toArray(new Predicate[0]));
    }

}