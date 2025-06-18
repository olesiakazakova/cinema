package com.example.cinema.cinema_app;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Репозиторий для работы со связями между фильмами и актерами.
 * Обеспечивает стандартные CRUD-операции для сущности FilmsActors
 * с использованием составного ключа FilmsActorsId.
 * Наследует функциональность JpaRepository.
 */
@Repository
public interface FilmsActorsRepository extends JpaRepository<FilmsActors, FilmsActorsId> {
}
