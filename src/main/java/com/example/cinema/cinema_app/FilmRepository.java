package com.example.cinema.cinema_app;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Репозиторий для работы с сущностями Film в базе данных.
 * Обеспечивает стандартные CRUD-операции и дополнительные методы для доступа к данным фильмов.
 * Наследует функциональность JpaRepository.
 */
@Repository
public interface FilmRepository extends JpaRepository<Film, Long> {
}



