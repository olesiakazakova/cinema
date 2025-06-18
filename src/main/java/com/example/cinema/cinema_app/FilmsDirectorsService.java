package com.example.cinema.cinema_app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Сервис для работы со связями между фильмами и режиссерами.
 * Обеспечивает бизнес-логику операций CRUD для сущности FilmsDirectors.
 * Использует FilmsDirectorsRepository для взаимодействия с базой данных.
 */
@Service
public class FilmsDirectorsService {

    @Autowired
    private FilmsDirectorsRepository filmsDirectorsRepository;

    /**
     * Получает все связи между фильмами и режиссерами.
     * @return список всех связей (может быть пустым)
     */
    public List<FilmsDirectors> getAllFilmsDirectors() {
        return filmsDirectorsRepository.findAll();
    }

    /**
     * Находит связь между конкретным режиссером и фильмом.
     * @param directorId идентификатор режиссера
     * @param filmId идентификатор фильма
     * @return Optional с найденной связью или пустой, если связь не найдена
     */
    public Optional<FilmsDirectors> getFilmsDirectorsById(Long directorId, Long filmId) {
        return filmsDirectorsRepository.findByDirector_DirectorIdAndFilm_FilmId(directorId, filmId);
    }

    /**
     * Сохраняет новую или обновляет существующую связь.
     * @param filmsDirectors объект связи для сохранения
     * @return сохраненный объект связи
     */
    public FilmsDirectors saveFilmsDirectors(FilmsDirectors filmsDirectors) { return filmsDirectorsRepository.save(filmsDirectors);}

    /**
     * Удаляет связь между режиссером и фильмом.
     * @param directorId идентификатор режиссера
     * @param filmId идентификатор фильма
     */
    public boolean deleteFilmsDirectors(Long directorId, Long filmId) {
        Optional<FilmsDirectors> filmsDirectors = filmsDirectorsRepository.findByDirector_DirectorIdAndFilm_FilmId(directorId, filmId);
        if (filmsDirectors.isPresent()) {
            filmsDirectorsRepository.delete(filmsDirectors.get());
            return true;
        }
        return false;
    }
}


