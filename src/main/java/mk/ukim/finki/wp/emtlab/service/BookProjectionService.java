//package mk.ukim.finki.wp.emtlab.service;
//
//import mk.ukim.finki.wp.emtlab.model.projection.BookDetailsProjection;
//import mk.ukim.finki.wp.emtlab.model.projection.BookShortProjection;
//import mk.ukim.finki.wp.emtlab.repository.BookRepository;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class BookProjectionService {
//
//    private final BookRepository bookRepository;
//
//    public BookProjectionService(BookRepository bookRepository) {
//        this.bookRepository = bookRepository;
//    }
//
//    public List<BookShortProjection> getAllBooksSummary() {
//        return bookRepository.findAll();
//    }
//
//    public List<BookDetailsProjection> getAllBooksDetail() {
//        return bookRepository.findAllProjectedByDeletedFalse();
//    }
//}
