package mk.ukim.finki.wp.emtlab.repository;

import mk.ukim.finki.wp.emtlab.model.domain.Book;
import mk.ukim.finki.wp.emtlab.model.domain.Category;
import mk.ukim.finki.wp.emtlab.model.domain.State;
import mk.ukim.finki.wp.emtlab.model.projection.BookDetailsProjection;
import mk.ukim.finki.wp.emtlab.model.projection.BookShortProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {


    //proekcii
    List<BookShortProjection> findAllProjectedBy();

    List<BookDetailsProjection> findAllExtendedProjectedBy();

    @EntityGraph(attributePaths = {"author", "author.country"})
    @Query("SELECT b FROM Book b JOIN FETCH b.authors a JOIN FETCH a.country")
    List<Book> findAllWithAuthorAndCountry();

//
//    @Query("""
//                select b from Book b
//                where(:category is null or b.category=:category)
//                and (:authors is null or b.authors=:authors)
//                and (:state is null or b.state=:state)
//                and (:availableCopies is null or b.availableCopies=:availableCopies)
//            """)
//    Page<Book> findAllFiltered(
//            @Param("category") Category category,
//            @Param("state") State state,
//            @Param("authors") Long authors,
//            @Param("availableCopies") Integer availableCopies,
//            Pageable pageable
//    );
@Query("""
        SELECT b FROM Book b
        LEFT JOIN b.authors a
        WHERE (:category IS NULL OR b.category = :category)
          AND (:state IS NULL OR b.state = :state)
          AND (:authorId IS NULL OR a.id = :authorId)
          AND (:availableCopies IS NULL OR b.availableCopies >= :availableCopies)
    """)
Page<Book> findAllFiltered(@Param("category") Category category,
                           @Param("state") State state,
                           @Param("authorId") Long authorId,
                           @Param("availableCopies") Integer availableCopies,
                           Pageable pageable);
//    @EntityGraph(attributePaths = {"author", "author.country"})
//    List<BookDetailsProjection> findAll2();

//    Page<Book> findAllByDeletedFalse(Pageable pageable);
//
//    Page<Book> findAllByDeletedFalseAndCategory(Category category, Pageable pageable);
//
//    Page<Book> findAllByDeletedFalseAndAuthorContainingIgnoreCase(String author, Pageable pageable);
//
//    Page<Book> findAllByDeletedFalseAndAvailable(boolean available, Pageable pageable);
//
//    Page<Book> findAllByDeletedFalseAndCategoryAndAuthorContainingIgnoreCase(Category category, String author, Pageable pageable);
//
//    Page<Book> findAllByDeletedFalseAndCategoryAndAvailable(Category category, boolean available, Pageable pageable);
//
//    Page<Book> findAllByDeletedFalseAndAuthorContainingIgnoreCaseAndAvailable(String author, boolean available, Pageable pageable);
//
//    Page<Book> findAllByDeletedFalseAndCategoryAndAuthorContainingIgnoreCaseAndAvailable(Category category, String author, boolean available, Pageable pageable);
}
