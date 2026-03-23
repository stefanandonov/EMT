package mk.ukim.finki.wp.emtlab.web.controller;

import mk.ukim.finki.wp.emtlab.model.BookView;
import mk.ukim.finki.wp.emtlab.model.domain.ActivityLog;
import mk.ukim.finki.wp.emtlab.model.domain.Book;
import mk.ukim.finki.wp.emtlab.model.domain.Category;
import mk.ukim.finki.wp.emtlab.model.dto.CreateBookDTO;
import mk.ukim.finki.wp.emtlab.model.dto.DisplayBookDTO;
import mk.ukim.finki.wp.emtlab.model.projection.BookDetailsProjection;
import mk.ukim.finki.wp.emtlab.model.projection.BookShortProjection;

import mk.ukim.finki.wp.emtlab.model.projection.BooksStatsProjection;
import mk.ukim.finki.wp.emtlab.repository.ActivityLogRepository;
import mk.ukim.finki.wp.emtlab.repository.BookRepository;
//import mk.ukim.finki.wp.emtlab.repository.BookViewRepository;
import mk.ukim.finki.wp.emtlab.repository.BookViewRepository;
import mk.ukim.finki.wp.emtlab.repository.BooksStatsRepository;
import mk.ukim.finki.wp.emtlab.service.BooksStatsService;
import mk.ukim.finki.wp.emtlab.service.application.BookApplicationService;
import mk.ukim.finki.wp.emtlab.service.domain.BookService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/books")
public class BookController {
    private final BookApplicationService bookApplicationService;
    private final BookService bookService;
    private final BookRepository bookRepository;
    private final BookViewRepository bookViewRepository;
    private final BooksStatsService booksStatsService;
    private final ActivityLogRepository activityLogRepository;

    public BookController(BookApplicationService bookApplicationService, BookService bookService, BookRepository bookRepository, BookViewRepository bookViewRepository, BooksStatsService booksStatsService, ActivityLogRepository activityLogRepository) {
        this.bookApplicationService = bookApplicationService;
        this.bookService = bookService;
        this.bookRepository = bookRepository;
        this.bookViewRepository = bookViewRepository;
        this.booksStatsService = booksStatsService;
        this.activityLogRepository = activityLogRepository;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<DisplayBookDTO>> findBookById(@PathVariable Long id) {
        return ResponseEntity.ok(bookApplicationService.findById(id));
    }

    @PostMapping("/add")
    public ResponseEntity<DisplayBookDTO> createBook(@RequestBody CreateBookDTO createBookDTO) {
        return ResponseEntity.ok(bookApplicationService.create(createBookDTO));
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<DisplayBookDTO> updateBook(@PathVariable Long id, @RequestBody CreateBookDTO
            createBookDTO) {
        return bookApplicationService
                .update(id, createBookDTO)
                .map(it -> ResponseEntity.ok().body(it))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/rent/{id}")
    public ResponseEntity<Optional<DisplayBookDTO>> rentBook(@PathVariable Long id) {
        return ResponseEntity.ok(bookApplicationService.rent(id));
    }

    @PostMapping("/return/{id}")
    public ResponseEntity<Optional<DisplayBookDTO>> returnBook(@PathVariable Long id) {
        return ResponseEntity.ok(bookApplicationService.rent(id));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable Long id) {
        return bookApplicationService.deleteById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public Page<DisplayBookDTO> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        return bookApplicationService.findAll(page, size);
    }

    @GetMapping("/pagination")
    public Page<Book> listBooks(
            @RequestParam(required = false) Category category,
            @RequestParam(required = false) String author,
            @RequestParam(required = false) Boolean available,
            @PageableDefault(size = 10, sort = "createdAt") Pageable pageable
    ) {
        return bookService.getBooks(category, author, available, pageable);
    }

    //Да се овозможи барем еден endpoint
    // што ќе враќа податоци преку projection наместо преку ентитет или класичен DTO.
    @GetMapping("/extended")
    public List<BookDetailsProjection> listBooksExtended() {
        return bookRepository.findAllExtendedProjectedBy();
    }

//    4.
    @GetMapping("/view")
    public List<BookView> getBookView(){
        return bookViewRepository.findAll();
    }

    //5.
    @GetMapping("/stats")
    public List<BooksStatsProjection> getStats() {
        return booksStatsService.getStats();
    }

    //9.Activity loggg
    @GetMapping("/activityLog")
    public Page<ActivityLog> getLogs(@RequestParam(defaultValue = "0")int page,
                                    @RequestParam(defaultValue = "10") int size){
        return activityLogRepository.findAll(PageRequest.of(page, size));
    }


}
