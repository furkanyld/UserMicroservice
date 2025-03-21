package tr.edu.ogu.ceng.User.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "user_roles", schema = "users_application")
public class UserRole {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "role_id", nullable = false)
	private Role role;

	@Column(nullable = false)
	private LocalDateTime assignedAt;

	@Column(nullable = false)
	private LocalDateTime updatedAt;

	@Column(name = "created_by")
	private String createdBy;

	@Column(name = "created_at", nullable = false)
	private LocalDateTime createdAt = LocalDateTime.now(); // Default olarak şu anki zaman

	@Column(name = "updated_by")
	private String updatedBy;

	@Column(name = "deleted_by")
	private String deletedBy;

	@Column(name = "deleted_at")
	private LocalDateTime deletedAt;

	// Constructor, Getter ve Setter'lar Lombok tarafından sağlanacaktır.
}
