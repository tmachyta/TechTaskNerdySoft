package com.example.techtasknerdysoft.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@SQLDelete(sql = "UPDATE books SET is_deleted = true WHERE id=?")
@Where(clause = "is_deleted=false")
@Table(name = "books")
@Data
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    @Size(min = 3)
    @Pattern(regexp = "^[A-Z][a-zA-Z]*$")
    private String title;
    @Column(nullable = false)
    @Pattern(regexp = "^[A-Z][a-zA-Z]+\\s[A-Z][a-zA-Z]+$")
    private String author;
    private int amount;
    @Column(name = "is_deleted")
    private boolean isDeleted;
}
