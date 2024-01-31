package it.uninastudents.dietidealsservice.repository.specs;

import it.uninastudents.dietidealsservice.model.entity.Asta;
import it.uninastudents.dietidealsservice.model.entity.Offerta;
import it.uninastudents.dietidealsservice.model.entity.enums.CategoriaAsta;
import it.uninastudents.dietidealsservice.model.entity.enums.StatoAsta;
import it.uninastudents.dietidealsservice.model.entity.enums.TipoAsta;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class AstaSpecs {

    public static Specification<Asta> none() {
        return Specification.where(null);
    }


    public static Specification<Asta> hasNome(String target) {
        return target != null ? ((root, query, criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.upper(root.get("nome")), target)) : none();
    }

    public static Specification<Asta> hasTipo(TipoAsta target) {
        return target != null ? ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("tipo"), target)) : none();
    }

    public static Specification<Asta> hasCategoria(CategoriaAsta target) {
        return target != null ? ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("categoria"), target)) : none();
    }

    public static Specification<Asta> hasProprietario(UUID target) {
        return target != null ? ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("proprietario").get("id"), target)) : none();
    }

    public static Specification<Asta> hasStato(StatoAsta target) {
        return target != null ? ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("stato"), target)) : none();
    }

    public static Specification<Asta> hasOfferta(UUID target) {
        return target != null ? (root, query, criteriaBuilder) -> {
            Join<Asta, Offerta> offerteJoin = root.join("offerte");
            return criteriaBuilder.equal(offerteJoin.get("utente").get("id"), target);
        } : none();
    }

    public static Specification<Asta> hasOffertaVincente(UUID target) {
        return target != null ? (root, query, criteriaBuilder) -> {
            Join<Asta, Offerta> offerteJoin = root.join("offerte");
            Predicate predicateUtente = criteriaBuilder.equal(offerteJoin.get("utente").get("id"), target);
            Predicate predicateVincente = criteriaBuilder.equal(offerteJoin.get("vincente"), true);
            return criteriaBuilder.and(predicateUtente, predicateVincente);
        } : none();
    }

}
