package com.example.cinema.cinema_app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;


import org.springframework.transaction.annotation.Transactional;

/**
 * Сервис для работы с фильмами.
 * Обеспечивает бизнес-логику операций CRUD для сущности Film.
 * Использует FilmRepository для взаимодействия с базой данных.
 */
@Service
public class FilmService {
    @Autowired
    private FilmRepository filmRepository;

    /**
     * Получает список всех фильмов из базы данных.
     * @return список объектов Film (может быть пустым)
     */
    public List<Film> findAll() {
        return filmRepository.findAll();
    }

    /**
     * Находит фильм по его идентификатору.
     * @param id уникальный идентификатор фильма
     * @return объект Film или null, если фильм не найден
     */
    public Film findById(Long id) {
        return filmRepository.findById(id).orElse(null);
    }

    /**
     * Сохраняет фильм в базе данных.
     * @param film объект Film для сохранения
     * @return сохраненный объект Film
     */
    public Film save(Film film) {
        return filmRepository.save(film);
    }

    /**
     * Удаляет фильм из базы данных по идентификатору.
     * @param id идентификатор фильма для удаления
     */
    public void delete(Long id) {filmRepository.deleteById(id);}

}
