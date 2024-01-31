package it.uninastudents.dietidealsservice.repository.specs;

import it.uninastudents.dietidealsservice.model.entity.Notifica;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class NotificaSpecs {

    public static Specification<Notifica> none() {
        return Specification.where(null);
    }

    public static Specification<Notifica> hasUtente(UUID target) {
        return target != null ? ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("utente").get("id"), target)) : none();
    }
}
