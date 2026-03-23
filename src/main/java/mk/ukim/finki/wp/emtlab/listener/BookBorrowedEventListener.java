package mk.ukim.finki.wp.emtlab.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mk.ukim.finki.wp.emtlab.events.BookBorrowedEvent;
import mk.ukim.finki.wp.emtlab.model.domain.ActivityLog;
import mk.ukim.finki.wp.emtlab.repository.ActivityLogRepository;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.time.LocalDateTime;

@Component
@Slf4j
@RequiredArgsConstructor
public class BookBorrowedEventListener {

    private final ActivityLogRepository activityLogRepository;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleBookBorrowed(BookBorrowedEvent event){
        log.info("Book borrowed: {}", event.getBook().getName());

        if(event.getBook().getAvailableCopies()==0){
            log.warn("Book '{}' is now out of stock!", event.getBook().getName());
        }

        //zacuvaj vo activity log
        ActivityLog logEntry=new ActivityLog();
        logEntry.setBookTitle(event.getBook().getName());
        logEntry.setEventType("BORROWED");
        logEntry.setEventTime(LocalDateTime.now());
        activityLogRepository.save(logEntry);
    }

}
