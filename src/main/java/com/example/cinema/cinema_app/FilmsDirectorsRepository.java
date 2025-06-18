package com.example.cinema.cinema_app;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

/**
 * Репозиторий для работы со связями между фильмами и режиссерами.
 * Обеспечивает стандартные CRUD-операции для сущности FilmsDirectors
 * с использованием составного ключа FilmsDirectorsId.
 * Наследует функциональность JpaRepository.
 */
public interface FilmsDirectorsRepository extends JpaRepository<FilmsDirectors, FilmsDirectorsId> {
    Optional<FilmsDirectors> findByDirector_DirectorIdAndFilm_FilmId(Long directorId, Long filmId);
}







