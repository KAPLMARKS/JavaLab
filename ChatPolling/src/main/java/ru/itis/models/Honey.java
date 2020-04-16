package ru.itis.models;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "honey")
public class Honey {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "price")
    private int cost;
    private String description;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "honeyId")
    List<Backet> backets = new ArrayList<>();
}
