package mk.ukim.finki.wp.emtlab.model.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "books")
public class Book extends BaseAuditableEntity {
    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "books_authors",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    private List<Author> authors;

    @ManyToOne
    @JoinColumn(name = "state_id", nullable = false)
    private State state;

    @Column
    private Integer availableCopies;


    public Book(String name, Category category, List<Author> authors, State state, Integer availableCopies) {
        this.name = name;
        this.category = category;
        this.authors = authors;
        this.state = state;
        this.availableCopies = availableCopies;
    }
}
