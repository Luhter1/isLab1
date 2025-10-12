package org.itmo.isLab1.dragons;

import lombok.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import org.hibernate.annotations.ColumnTransformer;
import org.hibernate.annotations.JdbcType;
import org.hibernate.dialect.PostgreSQLEnumJdbcType;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.itmo.isLab1.common.framework.CrudEntity;
import org.itmo.isLab1.coordinates.Coordinate;
import org.itmo.isLab1.dragoncaves.DragonCave;
import org.itmo.isLab1.dragonheads.DragonHead;
import org.itmo.isLab1.people.Person;
import org.itmo.isLab1.people.enums.Color;
import org.itmo.isLab1.dragons.enums.DragonCharacter;
import org.itmo.isLab1.dragons.enums.DragonType;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "dragons")
public class Dragon extends CrudEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dragons_id_seq")
    @SequenceGenerator(name = "dragons_id_seq", sequenceName = "dragons_id_seq", allocationSize = 1)
    private int id;

    @NotNull
    @NotBlank
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "coordinates_id", nullable = false)
    private Coordinate coordinates;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cave_id")
    private DragonCave cave;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "killer_id")
    private Person killer;

    @Min(0)
    @Column(name = "age")
    private Integer age;

    @Enumerated(EnumType.STRING)
    @ColumnTransformer(write="?::color")
    @Column(name = "color")
    private Color color;
  
    @Enumerated(EnumType.STRING)
    @JdbcType(PostgreSQLEnumJdbcType.class)
    @ColumnTransformer(write="?::dragon_type")
    @Column(name = "type")
    private DragonType type;

    @NotNull
    @Enumerated(EnumType.STRING)
    @ColumnTransformer(write="?::dragon_character")
    @JoinColumn(name = "character", nullable = false)
    private DragonCharacter character;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "head_id")
    private DragonHead head;
}
