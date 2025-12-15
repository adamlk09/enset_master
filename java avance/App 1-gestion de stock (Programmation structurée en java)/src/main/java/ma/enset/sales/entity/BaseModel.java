package ma.enset.sales.entity;

import jakarta.annotation.PreDestroy;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@MappedSuperclass
public abstract class BaseModel implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "uuid", unique = true)
    private UUID uuid;

    @CreationTimestamp
    private LocalDateTime createDateTime;

    @UpdateTimestamp
    private LocalDateTime updateDateTime;

    @PrePersist
    public void prePersist() {
        if (this.uuid == null) {
            this.uuid = UUID.randomUUID();
        }
    }

    @PreUpdate
    public void preUpdate() {

    }

    @PreRemove
    public void preRemove() {

    }

    @PreDestroy
    public void preDestroy() {

    }

    @PostPersist
    public void postPersist() {
        this.setUuid(UUID.randomUUID());

    }
}
