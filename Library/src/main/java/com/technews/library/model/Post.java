package com.technews.library.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "posts", uniqueConstraints = @UniqueConstraint(columnNames = {"name", "image"}))
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;
    private String name;
    @Column(columnDefinition = "TEXT")
    private String content;
    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private String image;
    private String time;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id", referencedColumnName = "category_id")
    private Category category;
    private boolean is_deleted;
    private boolean is_activated;
}
