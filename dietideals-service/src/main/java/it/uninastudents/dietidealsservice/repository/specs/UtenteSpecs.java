package it.uninastudents.dietidealsservice.repository.specs;

import it.uninastudents.dietidealsservice.model.entity.Asta;
import it.uninastudents.dietidealsservice.model.entity.Utente;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UtenteSpecs {

    public static Specification<Utente> none() {
        return Specification.where(null);
    }

    public static Specification<Utente> hasEmail(String target) {
        return target != null ? ((root, query, criteriaBuilder) -> criteriaBuilder.equal(criteriaBuilder.upper(root.get("email")), target)) : none();
    }
}
