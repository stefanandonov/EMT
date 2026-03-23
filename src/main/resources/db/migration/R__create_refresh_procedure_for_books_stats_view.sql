CREATE OR REPLACE PROCEDURE refresh_books_stats_view()
LANGUAGE SQL
AS $$
    REFRESH MATERIALIZED VIEW books_stats_view;
$$;