package tr.edu.ogu.ceng.Users.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.PrePersist;
import lombok.Data;

@Entity
@Data
@Table(name = "settings", schema = "users_application")
public class Setting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "key", nullable = false, length = 255)
    private String key;

    @Column(name = "value", nullable = false, length = 255)
    private String value;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_by")
    private String deletedBy;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    // Varsayılan değerler atamak için PrePersist metodu
    @PrePersist
    protected void onCreateInfo() {
        // Varsayılan değerler atama
        if (createdBy == null) {
            this.createdBy = "system"; // Varsayılan oluşturulma kullanıcı adı
        }
        if (updatedBy == null) {
            this.updatedBy = "system"; // Varsayılan güncelleme kullanıcı adı
        }
        if (deletedBy == null) {
            this.deletedBy = "system"; // Varsayılan silme kullanıcı adı
        }

        // Zaman damgalarını ayarlama
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now(); // Varsayılan güncellenme zamanı
        this.deletedAt = null; // Silinme zamanı başlangıçta null
    }
}
