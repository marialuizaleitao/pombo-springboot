package com.maria.pombo.model.selector;

import java.util.ArrayList;
import java.util.List;

import lombok.EqualsAndHashCode;
import org.springframework.data.jpa.domain.Specification;

import com.maria.pombo.model.entity.User;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.Data;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserSelector extends BaseSelector implements Specification<User> {

    private String name;
    private String email;
    private String cpf;

    public boolean hasFilter() {
        return (this.stringValidator(this.name))
                || (this.stringValidator(this.email))
                || (this.stringValidator(this.cpf))
                ;
    }

    @Override
    public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        List<Predicate> filters = new ArrayList<>();

        if(this.getName() != null && !this.getName().trim().isEmpty()) {
            filters.add(cb.like(root.get("name"), "%" + this.getName() + "%"));
        }

        if(this.getEmail() != null && !this.getEmail().trim().isEmpty()) {
            filters.add(cb.like(root.get("email"), "%" + this.getEmail() + "%"));
        }

        if(this.getCpf() != null && !this.getCpf().trim().isEmpty()) {
            filters.add(cb.like(root.get("cpf"), "%" + this.getCpf() + "%"));
        }

        return cb.and(filters.toArray(new Predicate[0]));
    }

}