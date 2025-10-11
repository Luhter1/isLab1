package org.itmo.isLab1.adminrequests;

import lombok.*;
import jakarta.persistence.*;
import org.hibernate.annotations.ColumnTransformer;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcType;
import org.hibernate.dialect.PostgreSQLEnumJdbcType;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.itmo.isLab1.common.entity.BaseEntity;
import org.itmo.isLab1.utils.datetime.ZonedDateTimeConverter;
import org.itmo.isLab1.users.User;
import java.time.ZonedDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "admin_requests")
public class AdminRequest implements BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "admin_requests_id_seq")
    @SequenceGenerator(name = "admin_requests_id_seq", sequenceName = "admin_requests_id_seq", allocationSize = 1)
    private int id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @JdbcType(PostgreSQLEnumJdbcType.class)
    @ColumnTransformer(write="?::request_status")
    @Column(name="status", nullable=false)
    private Status status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "approved_by")
    private User approvedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="approval_date")
    @Convert(converter = ZonedDateTimeConverter.class)
    private ZonedDateTime approvalDate;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="created_at", nullable=false)
    @Convert(converter = ZonedDateTimeConverter.class)
    private ZonedDateTime createdAt;
}