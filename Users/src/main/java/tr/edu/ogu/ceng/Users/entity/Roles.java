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
@Table(name = "roles", schema = "users_application")
public class Roles {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String roleName;

    @Column(nullable = false, length = 255)
    private String description;

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

    // Varsayılan değerler ve zaman damgaları atamak için PrePersist metodunu ekliyoruz
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
        if (description == null) {
            this.description = "No description provided"; // Varsayılan açıklama
        }

        // Zaman damgalarını ayarlama
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now(); // Varsayılan güncellenme zamanı
        this.deletedAt = null; // Silinme zamanı başlangıçta null
    }
}
