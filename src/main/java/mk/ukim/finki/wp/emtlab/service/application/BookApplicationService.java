package mk.ukim.finki.wp.emtlab.service.application;

import mk.ukim.finki.wp.emtlab.model.dto.CreateBookDTO;
import mk.ukim.finki.wp.emtlab.model.dto.DisplayBookDTO;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface BookApplicationService {
    List<DisplayBookDTO> findAll();

    Optional<DisplayBookDTO> findById(Long id);

    DisplayBookDTO create(CreateBookDTO createBookDTO);

    Optional<DisplayBookDTO> update(Long id, CreateBookDTO createBookDTO);

    Optional<DisplayBookDTO> deleteById(Long id);

    Optional<DisplayBookDTO> rent(Long bookId);

    Optional<DisplayBookDTO> returnBook (Long bookId);

    Page<DisplayBookDTO> findAll(int page, int size);
}
