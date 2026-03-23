package mk.ukim.finki.wp.emtlab.repository;

import mk.ukim.finki.wp.emtlab.model.domain.Book;
import mk.ukim.finki.wp.emtlab.model.projection.BooksStatsProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;

import java.util.List;

public interface BooksStatsRepository extends JpaRepository<Book, Long> {
    @Query(value = "SELECT * FROM books_stats_view", nativeQuery = true)
    @Modifying
    List<BooksStatsProjection> findAllStats();


    @Procedure(procedureName = "refresh_books_stats_view")
    void refresh();
}
