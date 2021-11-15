package recipes.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @author dzahbarov
 */


@Getter
@Setter
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue
    private Integer id;

    @Pattern(regexp = "(?=(.*@.*)).*\\..*")
    @NotBlank
    private String email;

    @Size(min=8)
    @NotBlank
    private String password;

    @OneToMany
    private List<Recipe> recipes;
}
