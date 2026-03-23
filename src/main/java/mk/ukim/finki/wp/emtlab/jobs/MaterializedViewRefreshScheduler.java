package mk.ukim.finki.wp.emtlab.jobs;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import mk.ukim.finki.wp.emtlab.repository.BooksStatsRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MaterializedViewRefreshScheduler {

    private final BooksStatsRepository booksStatsRepository;

    public MaterializedViewRefreshScheduler(BooksStatsRepository booksStatsRepository) {
        this.booksStatsRepository = booksStatsRepository;
    }

    @Scheduled(cron="0 * * * * *")//sekoja min
    @Transactional
    public void refreshBooksStatsView(){
        log.info("Refreshing BOOKS_STATS_VIEW...");

        booksStatsRepository.refresh();

        log.info("BOOKS_STATS_VIEW successfully refreshed.");
    }
}
