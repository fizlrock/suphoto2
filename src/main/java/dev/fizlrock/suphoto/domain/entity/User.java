package dev.fizlrock.suphoto.domain.entity;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.validator.constraints.Length;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder
@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class User extends BaseEntity {

  public static enum Role {
    None, Client, God, Trainer
  }

  @Column(name = "username", nullable = false, unique = true)
  @Length(min = 5, max = 30)
  protected String username;

  @Column(name = "password", nullable = false)
  protected String password;

  @Column(name = "firstName", nullable = false)
  @Length(min = 5, max = 30)
  protected String firstName;

  @Column(name = "lastName", nullable = false)
  @Length(min = 5, max = 30)
  protected String lastName;

  @Column(name = "patronymic", nullable = true)
  @Length(min = 5, max = 30)
  protected String patronymic;

  @Column(name = "role", nullable = false)
  @Builder.Default
  @Enumerated(EnumType.STRING)
  protected Role role = Role.None;

  @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.ALL })
  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  @Builder.Default
  protected Set<Event> events = new HashSet<>();

  @SuppressWarnings("unused")
  private void setEvents() {
  }

}
