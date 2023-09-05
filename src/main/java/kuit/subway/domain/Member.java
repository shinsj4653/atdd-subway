package kuit.subway.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;
    private Integer age;
    private String email;
    private String password;

    public Member(Integer age, String email, String password) {
        this.age = age;
        this.email = email;
        this.password = password;
    }

    public void updateMember(Member member) {
        this.age = member.getAge();
        this.email = member.getEmail();
        this.password = member.getPassword();
    }

    public boolean isInvalidPassword(String password) {
        return !this.password.equals(password);
    }
}
