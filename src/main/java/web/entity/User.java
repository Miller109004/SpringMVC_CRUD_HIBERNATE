package web.entity;


import javax.validation.constraints.*;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "users")
@NoArgsConstructor
@Getter
@Setter
@ToString
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    @NotEmpty(message = "Имя не должно быть пустым")
    @Pattern(regexp = "^[\\p{L}]+(?: [\\p{L}]+)*$", message = "Имя может содержать только буквы")
    @Size(min = 2, max = 20, message = "Имя в пределах от 2 до 10 знаков")
    private String name;

    @Column(name = "sur_name")
    @NotEmpty(message = "Фамилия не должно быть пустым")
    @Pattern(regexp = "^[\\p{L}]+(?: [\\p{L}]+)*$", message = "Фамилия может содержать только буквы")
    @Size(min = 2, max = 20, message = "Фамилия в пределах от 2 до 10 знаков")
    private String surname;

    @Column(name = "email")
    @NotEmpty(message = "Почта не может быть пустой")
    @Email(message = "Почта должна быть валидная")
    private String email;

    @Column(name = "age")
    @Min(value = 1, message = "Возраст должен быть больше 0")
    private int age;

    public User(String name, String surname, String email, int age) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.age = age;
    }
}
