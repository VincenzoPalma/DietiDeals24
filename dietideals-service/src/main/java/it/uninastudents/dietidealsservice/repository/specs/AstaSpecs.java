package it.uninastudents.dietidealsservice.repository.specs;

import it.uninastudents.dietidealsservice.model.entity.Asta;
import it.uninastudents.dietidealsservice.model.entity.enums.CategoriaAsta;
import it.uninastudents.dietidealsservice.model.entity.enums.TipoAsta;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

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
}
