package mk.ukim.finki.wp.emtlab.model.projection;

import mk.ukim.finki.wp.emtlab.model.domain.Category;
import mk.ukim.finki.wp.emtlab.model.domain.State;

public interface BookShortProjection {
    Long getId();
    String getName();
    Category getCategory();
    State getState();
    int getAvailableCopies();
}
