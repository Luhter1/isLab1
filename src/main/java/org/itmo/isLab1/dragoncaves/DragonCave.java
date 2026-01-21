package org.itmo.isLab1.dragoncaves;

import lombok.*;
import jakarta.persistence.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.itmo.isLab1.common.framework.CrudEntity;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;


@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "dragon_caves")
public class DragonCave extends CrudEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dragon_caves_id_seq")
    @SequenceGenerator(name = "dragon_caves_id_seq", sequenceName = "dragon_caves_id_seq", allocationSize = 1)
    private int id;

    @NonNull
    @Column(name = "depth", nullable = false)
    private Integer depth;
}