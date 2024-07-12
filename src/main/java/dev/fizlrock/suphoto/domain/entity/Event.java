package dev.fizlrock.suphoto.domain.entity;


import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.validator.constraints.Length;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Event
 */
@Table(name = "events")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Event extends BaseEntity {

  @Column(name = "title", nullable = false)
  @Length(min = 5, max = 30)
  protected String title;

  @Column(name = "location", nullable = false)
  @Length(min = 5, max = 30)
  protected String location;

  @Column(name = "start_time", nullable = false)
  protected LocalDateTime startTime;

  @Column(name = "end_time", nullable = false)
  protected LocalDateTime endTime;

  @ManyToMany(mappedBy = "events", fetch = FetchType.LAZY, cascade = { CascadeType.ALL })
  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  protected Set<User> staff = new HashSet<>();

  @SuppressWarnings("unused")
  private void setStaff() {
  };

}
