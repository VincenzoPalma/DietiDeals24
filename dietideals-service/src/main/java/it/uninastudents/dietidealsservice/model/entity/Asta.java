package it.uninastudents.dietidealsservice.model.entity;

import it.uninastudents.dietidealsservice.model.base.BaseEntity;
import it.uninastudents.dietidealsservice.model.entity.enums.CategoriaAsta;
import it.uninastudents.dietidealsservice.model.entity.enums.StatoAsta;
import it.uninastudents.dietidealsservice.model.entity.enums.TipoAsta;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "asta")
public class Asta extends BaseEntity {

    @NotBlank
    @Column(nullable = false)
    private String nome;

    @NotBlank
    @Size(max = 300)
    @Column(nullable = false, length = 300)
    private String descrizione;

    private String urlFoto;

    @Future
    private OffsetDateTime dataScadenza;

    @Positive
    private BigDecimal sogliaRialzo;

    @Positive
    @Min(30)
    @Max(180)
    private Integer intervalloTempoOfferta;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CategoriaAsta categoria;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoAsta tipo;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatoAsta stato;

    @NotNull
    @ManyToOne
    @JoinColumn(nullable = false)
    private Utente proprietario;

    @OneToMany
    private Set<Offerta> offerte;

}