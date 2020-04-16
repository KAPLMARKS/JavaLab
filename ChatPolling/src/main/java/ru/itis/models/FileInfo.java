package ru.itis.models;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "files")
public class FileInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "storage_file_name")
    private String storageFileName;
    @Column(name = "original_file_name")
    private String originalFileName;
    private Long size;
    private String type;
    private String url;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}