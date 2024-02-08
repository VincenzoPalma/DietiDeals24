package it.uninastudents.dietidealsservice.model.entity;

import it.uninastudents.dietidealsservice.model.base.BaseEntity;
import it.uninastudents.dietidealsservice.model.entity.enums.CategoriaAsta;
import it.uninastudents.dietidealsservice.model.entity.enums.StatoAsta;
import it.uninastudents.dietidealsservice.model.entity.enums.TipoAsta;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import net.minidev.json.annotate.JsonIgnore;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
@Entity
@Table(name = "asta")
public class Asta extends BaseEntity {

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, length = 300)
    private String descrizione;

    private String urlFoto;

    private OffsetDateTime dataScadenza;

    @Column(nullable = false)
    private BigDecimal prezzoBase;

    private BigDecimal sogliaRialzo;

    private Integer intervalloTempoOfferta;

    @Column(nullable = false)
    private CategoriaAsta categoria;

    @Column(nullable = false)
    private TipoAsta tipo;

    @Column(nullable = false)
    private StatoAsta stato;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(nullable = false)
    private Utente proprietario;

    @OneToMany
    private Set<Offerta> offerte;

}