package ru.itis.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String username;
    String email;
    String password;
    @Column(name = "state")
    @Enumerated(EnumType.STRING)
    State state;
    @Column(name = "code")
    String code;
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    Role role;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
    List<FileInfo> files = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "owner")
    List<Backet> backets = new ArrayList<>();
}