package mk.ukim.finki.wp.emtlab.service.domain.impl;

import jakarta.transaction.Transactional;
import mk.ukim.finki.wp.emtlab.events.BookBorrowedEvent;
import mk.ukim.finki.wp.emtlab.model.domain.Author;
import mk.ukim.finki.wp.emtlab.model.domain.Book;
import mk.ukim.finki.wp.emtlab.model.domain.Category;
import mk.ukim.finki.wp.emtlab.model.dto.DisplayBookDTO;
import mk.ukim.finki.wp.emtlab.repository.BookRepository;
import mk.ukim.finki.wp.emtlab.service.domain.BookService;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final ApplicationEventPublisher eventPublisher;

    public BookServiceImpl(BookRepository bookRepository, ApplicationEventPublisher eventPublisher) {
        this.bookRepository = bookRepository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    public Optional<Book> findById(Long id) {
        return bookRepository.findById(id);
    }

    @Override
    public Book create(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public Optional<Book> update(Long id, Book book) {
        return bookRepository.findById(id)
                .map((it) -> {
                    it.setName(book.getName());
                    it.setCategory(book.getCategory());
                    it.setAuthors(book.getAuthors());
                    it.setState(book.getState());
                    it.setAvailableCopies(book.getAvailableCopies());
                    return bookRepository.save(it);
                });
    }

    @Override
    public Optional<Book> deleteById(Long id) {
        return bookRepository.findById(id).map(book -> {
            if (book.getAvailableCopies() == 0 && book.getState().getState().equals("BAD")) {
                bookRepository.delete(book);
                return book;
            } else {
                throw new IllegalStateException("The book cannot be deleted because its condition is still "
                        + book.getState().getState() + " or it still has available copies");
            }
        });
    }

    //izmena
    @Transactional
    public Optional<Book> rent(Long id){
        Book book=bookRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Book not found"));
        if(book.getAvailableCopies()<=0){
            throw new RuntimeException("No copies available");
        }

        book.setAvailableCopies(book.getAvailableCopies()-1);
        bookRepository.save(book);

        eventPublisher.publishEvent(new BookBorrowedEvent(this, book));
        return Optional.of(book);
    }
//
//    @Override
//    public Optional<Book> rent(Long id) {
//        return bookRepository.findById(id).map((book) -> {
//            if (book.getAvailableCopies() > 0 && book.getState().getState().equals("GOOD")) {
//                book.setAvailableCopies(book.getAvailableCopies() - 1);
//            } else {
//                throw new IllegalStateException("The book cannot be rented because it's condition is " + book.getState().getState() + " or it does not have any available copies");
//            }
//            return bookRepository.save(book);
//        });
//    }

    @Override
    public Optional<Book> returnBook(Long id) {
        return bookRepository.findById(id).map((book) -> {
            if (book.getAvailableCopies() > 0 && book.getState().getState().equals("GOOD")) {
                book.setAvailableCopies(book.getAvailableCopies() + 1);
            } else {
                throw new IllegalStateException("The book cannot be rented because it's condition is " + book.getState().getState() + " or it does not have any available copies");
            }
            return bookRepository.save(book);
        });
    }

    @Override
    public Page<Book> findAll(Pageable pageable) {
        return bookRepository.findAll(pageable);
    }

//od ent graf
    public List<Book> getBooksWithAuthorAndCountry() {
        return bookRepository.findAllWithAuthorAndCountry();
    }

    @Override
    public Page<Book> getBooks(Category category, String author, Boolean available, Pageable pageable) {
        return null;
    }

//    @Override
//    public Page<Book> getBooks(Category category, String author, Boolean available, Pageable pageable) {
//        Page<Book> books;
//
//        if (category != null && author != null && available != null) {
//            books = bookRepository.findAllByDeletedFalseAndCategoryAndAuthorContainingIgnoreCaseAndAvailable(
//                    category, author, available, pageable
//            );
//        } else if (category != null && author != null) {
//            books = bookRepository.findAllByDeletedFalseAndCategoryAndAuthorContainingIgnoreCase(category, author, pageable);
//        } else if (category != null && available != null) {
//            books = bookRepository.findAllByDeletedFalseAndCategoryAndAvailable(category, available, pageable);
//        } else if (author != null && available != null) {
//            books = bookRepository.findAllByDeletedFalseAndAuthorContainingIgnoreCaseAndAvailable(author, available, pageable);
//        } else if (category != null) {
//            books = bookRepository.findAllByDeletedFalseAndCategory(category, pageable);
//        } else if (author != null) {
//            books = bookRepository.findAllByDeletedFalseAndAuthorContainingIgnoreCase(author, pageable);
//        } else if (available != null) {
//            books = bookRepository.findAllByDeletedFalseAndAvailable(available, pageable);
//        } else {
//            books = bookRepository.findAllByDeletedFalse(pageable);
//        }
//
//        return books.map(book -> new DisplayBookDTO(
//                book.getName(),
//                book.getAuthors().stream().map(Author::getName).toList(),
//                book.getCategory().getName(),
//                book.getAvailableCopies()>0,
//                book.getCreatedAt()
//        ));
//    }


}
