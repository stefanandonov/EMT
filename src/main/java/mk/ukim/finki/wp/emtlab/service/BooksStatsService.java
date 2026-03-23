package mk.ukim.finki.wp.emtlab.service;

import mk.ukim.finki.wp.emtlab.model.projection.BooksStatsProjection;
import mk.ukim.finki.wp.emtlab.repository.BooksStatsRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BooksStatsService {
    private final BooksStatsRepository repository;

    public BooksStatsService(BooksStatsRepository repository) {
        this.repository = repository;
    }

    public List<BooksStatsProjection> getStats() {
        return repository.findAllStats();
    }
}
