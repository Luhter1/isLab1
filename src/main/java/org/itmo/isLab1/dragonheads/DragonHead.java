package org.itmo.isLab1.dragonheads;

import lombok.*;
import jakarta.persistence.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.itmo.isLab1.common.framework.CrudEntity;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "dragon_heads")
public class DragonHead extends CrudEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dragon_heads_id_seq")
    @SequenceGenerator(name = "dragon_heads_id_seq", sequenceName = "dragon_heads_id_seq", allocationSize = 1)
    private int id;

    @Column(name = "size", nullable = false)
    private Float size;
}
