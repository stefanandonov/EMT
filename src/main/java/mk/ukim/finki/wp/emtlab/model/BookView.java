package mk.ukim.finki.wp.emtlab.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.Immutable;

@Entity
@Table(name="book_view")
@Immutable
//bieejki e read only
public class BookView {

    @Id
    private Long id;

    private String name;
    private String category;
    private String state;

    @Column(name="available_copies")
    private int availableCopies;

    @Column(name="author_full_name")
    private String authorFullName;

    @Column(name="country_name")
    private String countryName;

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getCategory() { return category; }
    public String getState() { return state; }
    public int getAvailableCopies() { return availableCopies; }
    public String getAuthorFullName() { return authorFullName; }
    public String getCountryName() { return countryName; }
}

