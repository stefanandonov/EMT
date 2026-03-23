CREATE OR REPLACE VIEW book_view AS
SELECT
    b.id AS id,
    b.name AS name,
    c.name AS category,
    s.state AS state,
    b.available_copies as availableCopies,
    CONCAT(a.name, ' ', a.surname) AS authorFullName,
    co.name AS countryName
FROM books b
         JOIN books_authors ba ON b.id = ba.book_id
         JOIN authors a ON ba.author_id = a.id
         JOIN countries co ON a.country_id = co.id
         JOIN categories c ON b.category_id = c.id
         JOIN states s ON b.state_id = s.id;