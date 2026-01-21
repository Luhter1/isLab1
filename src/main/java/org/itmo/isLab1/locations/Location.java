package org.itmo.isLab1.locations;

import lombok.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.Length;
import org.itmo.isLab1.common.framework.CrudEntity;
import org.hibernate.annotations.Cache;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "locations")
public class Location extends CrudEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "locations_id_seq")
    @SequenceGenerator(name = "locations_id_seq", sequenceName = "locations_id_seq", allocationSize = 1)
    private int id;

    @NotNull
    @Column(name = "x", nullable = false)
    private Long x;

    @Column(name = "y")
    private Integer y;

    @NotNull
    @Column(name = "z", nullable = false)
    private Double z;

    @NotNull
    @Length(max = 240)
    @Column(name = "name", nullable = false)
    private String name;
}
