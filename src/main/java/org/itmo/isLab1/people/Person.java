package org.itmo.isLab1.people;

import lombok.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.hibernate.annotations.ColumnTransformer;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.itmo.isLab1.common.framework.CrudEntity;
import org.itmo.isLab1.locations.Location;
import org.itmo.isLab1.people.enums.Color;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "people")
public class Person extends CrudEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "people_id_seq")
    @SequenceGenerator(name = "people_id_seq", sequenceName = "people_id_seq", allocationSize = 1)
    private int id;

    @NotNull
    @NotBlank
    @Column(name = "name", nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @ColumnTransformer(write="?::color")
    @Column(name = "eye_color")
    private Color eyeColor;
    
    @NotNull
    @Enumerated(EnumType.STRING)
    @ColumnTransformer(write="?::color")
    @Column(name = "hair_color", nullable = false)
    private Color hairColor;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;

    @Past
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "birthday", columnDefinition = "TIMESTAMP")
    private LocalDateTime birthday;

    @NotNull
    @Min(0)
    @Column(name = "height", nullable = false)
    private Float height;

    @Min(0)
    @Column(name = "weight")
    private Integer weight;

    @NotNull
    @NotBlank
    @Length(max = 23)
    @Column(name = "passport_id", nullable = false)
    private String passportId;
}