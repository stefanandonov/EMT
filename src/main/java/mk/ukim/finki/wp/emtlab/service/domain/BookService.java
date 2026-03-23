package mk.ukim.finki.wp.emtlab.service.domain;

import mk.ukim.finki.wp.emtlab.model.domain.Book;
import mk.ukim.finki.wp.emtlab.model.domain.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface BookService {
    List<Book> findAll();

    Optional<Book> findById(Long id);

    Book create(Book book);

    Optional<Book> update (Long id, Book book);

    Optional<Book> deleteById(Long id);

    Optional<Book> rent(Long id);

    Optional<Book> returnBook(Long id);

    Page<Book> findAll(Pageable pageable);

    Page<Book> getBooks(Category category, String author, Boolean available, Pageable pageable);
}
