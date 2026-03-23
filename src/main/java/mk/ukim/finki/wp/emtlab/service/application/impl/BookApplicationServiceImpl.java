package mk.ukim.finki.wp.emtlab.service.application.impl;

import mk.ukim.finki.wp.emtlab.model.domain.Author;
import mk.ukim.finki.wp.emtlab.model.domain.Book;
import mk.ukim.finki.wp.emtlab.model.domain.Category;
import mk.ukim.finki.wp.emtlab.model.domain.State;
import mk.ukim.finki.wp.emtlab.model.dto.CreateBookDTO;
import mk.ukim.finki.wp.emtlab.model.dto.DisplayBookDTO;
import mk.ukim.finki.wp.emtlab.repository.CategoryRepository;
import mk.ukim.finki.wp.emtlab.repository.StateRepository;
import mk.ukim.finki.wp.emtlab.service.application.BookApplicationService;
import mk.ukim.finki.wp.emtlab.service.domain.AuthorService;
import mk.ukim.finki.wp.emtlab.service.domain.BookService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookApplicationServiceImpl implements BookApplicationService {

    private final BookService bookService;
    private final CategoryRepository categoryRepository;
    private final StateRepository stateRepository;
    private final AuthorService authorService;

    public BookApplicationServiceImpl(BookService bookService, CategoryRepository categoryRepository, StateRepository stateRepository, AuthorService authorService) {
        this.bookService = bookService;
        this.categoryRepository = categoryRepository;
        this.stateRepository = stateRepository;
        this.authorService = authorService;
    }

    @Override
    public List<DisplayBookDTO> findAll() {
        return DisplayBookDTO.from(bookService.findAll());
    }

    @Override
    public Optional<DisplayBookDTO> findById(Long id) {
        return bookService.findById(id)
                .map(DisplayBookDTO::from);
    }

    @Override
    public DisplayBookDTO create(CreateBookDTO createBookDTO) {
        Category category = categoryRepository.findById(createBookDTO.categoryId())
                .orElseThrow(() -> new IllegalStateException("Didnt find category with id: " + createBookDTO.categoryId()));
        List<Author> authors = authorService.findAllByIds(createBookDTO.authorIds());
        State state = stateRepository.findById(createBookDTO.stateId())
                .orElseThrow(() -> new IllegalStateException("Didnt find state with id: " + createBookDTO.stateId()));
        return DisplayBookDTO.from(bookService.create(createBookDTO.toBook(category, authors, state)));
    }


    @Override
    public Optional<DisplayBookDTO> update(Long id, CreateBookDTO createBookDTO) {
        Category category = categoryRepository.findById(createBookDTO.categoryId())
                .orElseThrow(() -> new IllegalStateException("Didnt find category with id: " + createBookDTO.categoryId()));
        List<Author> authors = authorService.findAllByIds(createBookDTO.authorIds());
        State state = stateRepository.findById(createBookDTO.stateId())
                .orElseThrow(() -> new IllegalStateException("Didnt find state with id: " + createBookDTO.stateId()));
        return bookService.update(id, createBookDTO.toBook(category, authors, state)).map(DisplayBookDTO::from);
    }

    @Override
    public Optional<DisplayBookDTO> deleteById(Long id) {
        return bookService.deleteById(id).map(DisplayBookDTO::from);
    }

    @Override
    public Optional<DisplayBookDTO> rent(Long bookId) {
        return bookService.rent(bookId).map(DisplayBookDTO::from);
    }

    @Override
    public Optional<DisplayBookDTO> returnBook(Long bookId) {
        return bookService.returnBook(bookId).map(DisplayBookDTO::from);
    }

    @Override
    public Page<DisplayBookDTO> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        return bookService.findAll(pageable)
                .map(DisplayBookDTO::from);
    }
}
