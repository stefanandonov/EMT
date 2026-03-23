CREATE MATERIALIZED VIEW books_stats_view AS
SELECT
    c.name AS category,
    COUNT(b.id) AS total_books,
    SUM(b.available_copies) AS total_available_copies,
    SUM(
            CASE
                WHEN s.state != 'GOOD' THEN 1
                ELSE 0
                END
    ) AS bad_condition_books
FROM books b
         JOIN categories c ON b.category_id = c.id
         JOIN states s ON b.state_id = s.id
GROUP BY c.name;

CREATE UNIQUE INDEX idx_books_stats_category
    ON books_stats_view(category);