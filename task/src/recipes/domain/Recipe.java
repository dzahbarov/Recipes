package recipes.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author dzahbarov
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table()
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    private Integer id;

    @NotBlank private String name;
    @NotBlank private String description;
    @NotBlank private String category;

    @Column
    @UpdateTimestamp
    private LocalDateTime date;

    @ElementCollection
    @Size(min=1)
    @NotNull
    private List<@NotBlank String> ingredients;

    @ElementCollection
    @Size(min=1)
    @NotNull
    private List<@NotBlank String> directions;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User author;

}
