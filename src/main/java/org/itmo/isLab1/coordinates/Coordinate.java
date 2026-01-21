package org.itmo.isLab1.coordinates;

import lombok.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.itmo.isLab1.common.framework.CrudEntity;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "coordinates")
public class Coordinate extends CrudEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "coordinates_id_seq")
    @SequenceGenerator(name = "coordinates_id_seq", sequenceName = "coordinates_id_seq", allocationSize = 1)
    private int id;

    @NotNull
    @Min(-998)
    @Column(name = "x", nullable = false)
    private Integer x;

    @NotNull
    @Max(844)
    @Column(name = "y", nullable = false)
    private Double y;
}