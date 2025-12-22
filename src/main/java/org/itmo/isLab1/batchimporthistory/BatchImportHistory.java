package org.itmo.isLab1.batchimporthistory;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnTransformer;
import org.hibernate.annotations.CreationTimestamp;
import org.itmo.isLab1.common.entity.BaseEntity;
import org.itmo.isLab1.users.User;
import org.itmo.isLab1.batchimporthistory.enums.ImportStatus;
import org.itmo.isLab1.utils.datetime.ZonedDateTimeConverter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.ZonedDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "batch_import_history")
public class BatchImportHistory implements BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "batch_import_history_id_seq")
    @SequenceGenerator(name = "batch_import_history_id_seq", sequenceName = "batch_import_history_id_seq", allocationSize = 1)
    private int id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "created_by", nullable = false)
    private User createdBy;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = false)
    @Convert(converter = ZonedDateTimeConverter.class)
    private ZonedDateTime createdAt;

    @Enumerated(EnumType.STRING)
    @ColumnTransformer(write = "?::import_status")
    @Column(name = "status", nullable = false)
    private ImportStatus status;

    @Column(name = "successful_operations")
    private Integer successfulOperations;

    @PrePersist
    private void prePersist() {
        if (createdAt == null) {
            createdAt = ZonedDateTime.now();
        }
    }
}

