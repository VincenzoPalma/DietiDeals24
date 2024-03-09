package it.uninastudents.dietidealsservice.repository.specs;

import it.uninastudents.dietidealsservice.model.entity.Utente;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UtenteSpecs {

    public static Specification<Utente> none() {
        return Specification.where(null);
    }

    public static Specification<Utente> hasIdAuth(String target) {
        return target != null ? ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("idAuth"), target)) : none();
    }

    public static Specification<Utente> hasId(UUID target) {
        return target != null ? ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("id"), target)) : none();
    }

    public static Specification<Utente> hasEmail(String target) {
        return target != null ? ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("email"), target)) : none();
    }

}
