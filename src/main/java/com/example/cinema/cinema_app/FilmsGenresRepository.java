package com.example.cinema.cinema_app;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;


/**
 * Репозиторий для работы со связями между фильмами и жанрами.
 * Обеспечивает стандартные CRUD-операции для сущности FilmsGenres
 * с использованием составного ключа FilmsGenresId.
 * Наследует функциональность JpaRepository.
 */
@Repository
public interface FilmsGenresRepository extends JpaRepository<FilmsGenres, Long> {
    /**
     * Находит связь между конкретным жанром и фильмом.
     * @param genre сущность жанра
     * @param film сущность фильма
     * @return Optional с найденной связью или пустой Optional, если связь не найдена
     */
    Optional<FilmsGenres> findByGenreAndFilm(Genre genre, Film film);

    //Fix me: добавлен для решения проблемы удаления жанров, связанных с фильмами
    /**
     * Удаляет все связи по ID жанра.
     * @param genreId ID жанра
     * @return количество удаленных связей
     */
    @Modifying
    @Query("DELETE FROM FilmsGenres fg WHERE fg.genreId = :genreId")
    int deleteByGenreId(@Param("genreId") Long genreId);
}

