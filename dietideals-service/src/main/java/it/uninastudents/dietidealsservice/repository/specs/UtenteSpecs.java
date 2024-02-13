package it.uninastudents.dietidealsservice.repository.specs;

import it.uninastudents.dietidealsservice.model.entity.Asta;
import it.uninastudents.dietidealsservice.model.entity.Carta;
import it.uninastudents.dietidealsservice.model.entity.Offerta;
import it.uninastudents.dietidealsservice.model.entity.Utente;
import jakarta.persistence.criteria.Join;
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

}
