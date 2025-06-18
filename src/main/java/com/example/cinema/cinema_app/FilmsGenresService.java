package com.example.cinema.cinema_app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Сервис для работы со связями между фильмами и жанрами.
 * Обеспечивает бизнес-логику операций CRUD для сущности FilmsGenres.
 * Использует FilmsGenresRepository для взаимодействия с базой данных.
 */
@Service
public class FilmsGenresService {
    @Autowired
    private FilmsGenresRepository filmsGenresRepository;

    /**
     * Создает новую связь между фильмом и жанром.
     * @param filmsGenres объект связи для сохранения
     * @return сохраненный объект связи
     * @throws IllegalArgumentException если filmsGenres равен null
     */
    public FilmsGenres createFilmsGenres(FilmsGenres filmsGenres) {
        //Fix me: добавлена проверка
        if (filmsGenres == null) {
            throw new IllegalArgumentException("FilmsGenres object cannot be null");
        }
        return filmsGenresRepository.save(filmsGenres);
    }

    /**
     * Удаляет связь между указанным жанром и фильмом.
     * @param genre жанр для удаления связи
     * @param film фильм для удаления связи
     * @return true если связь была найдена и удалена, false если связь не найдена
     */
    public boolean deleteFilmsGenres(Genre genre, Film film) {
        Optional<FilmsGenres> filmsGenres = filmsGenresRepository.findByGenreAndFilm(genre, film);
        if (filmsGenres.isPresent()) {
            filmsGenresRepository.delete(filmsGenres.get());
            return true;
        }
        return false;
    }
}
