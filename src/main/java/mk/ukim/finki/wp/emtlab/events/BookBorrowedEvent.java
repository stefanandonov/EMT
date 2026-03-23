package mk.ukim.finki.wp.emtlab.events;

import mk.ukim.finki.wp.emtlab.model.domain.Book;
import org.springframework.context.ApplicationEvent;

public class BookBorrowedEvent extends ApplicationEvent {

    private final Book book;

    public BookBorrowedEvent(Object source, Book book) {
        super(source);
        this.book = book;
    }

    public Book getBook(){
        return book;
    }
}
