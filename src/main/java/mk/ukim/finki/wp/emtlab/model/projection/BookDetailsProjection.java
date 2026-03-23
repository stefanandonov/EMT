package mk.ukim.finki.wp.emtlab.model.projection;

import mk.ukim.finki.wp.emtlab.model.domain.Author;
import mk.ukim.finki.wp.emtlab.model.domain.Category;
import mk.ukim.finki.wp.emtlab.model.domain.State;

import java.util.List;

public interface BookDetailsProjection {

    Long getId();
    String getName();
    Category getCategory();
    State getState();
    int getAvailableCopies();

    List<AuthorInfo> getAuthors();

    interface AuthorInfo {
        String getName();
        String getSurname();
        CountryInfo getCountry();
    }

    interface CountryInfo {
        String getName();
    }
}
