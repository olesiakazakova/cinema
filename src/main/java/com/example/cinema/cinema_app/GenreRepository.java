package com.example.cinema.cinema_app;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Репозиторий для работы с сущностями Genre в базе данных.
 * Обеспечивает стандартные CRUD-операции и дополнительные методы для доступа к данным жанров.
 * Наследует функциональность JpaRepository.
 */
@Repository
public interface GenreRepository extends JpaRepository<Genre, Long> {
    /**
     * Находит жанр по точному совпадению названия.
     * @param genre Название жанра для поиска (чувствительно к регистру)
     * @return Optional, содержащий найденный жанр или пустой, если жанр не найден
     */
    Optional<Genre> findByGenre(String genre);
}
