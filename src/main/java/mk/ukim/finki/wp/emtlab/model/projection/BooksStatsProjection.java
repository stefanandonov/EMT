package mk.ukim.finki.wp.emtlab.model.projection;

public interface BooksStatsProjection {
    String getCategory();
    Long getTotalBooks();
    Integer getTotalAvailableCopies();
    Long getBadConditionBooks();
}
