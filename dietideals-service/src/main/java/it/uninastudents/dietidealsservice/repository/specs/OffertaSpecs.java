package it.uninastudents.dietidealsservice.repository.specs;

import it.uninastudents.dietidealsservice.model.entity.Offerta;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OffertaSpecs {

    public static Specification<Offerta> none() {
        return Specification.where(null);
    }

    public static Specification<Offerta> hasAsta(UUID target) {
        return target != null ? ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("asta").get("id"), target)) : none();
    }

    public static Specification<Offerta> isVincente() {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("vincente"), true));
    }
}
