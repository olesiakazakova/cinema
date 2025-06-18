package com.example.cinema.cinema_app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Сервис для работы со связями между фильмами и актерами.
 * Обеспечивает бизнес-логику операций CRUD для сущности FilmsActors.
 * Использует FilmsActorsRepository для взаимодействия с базой данных.
 */
@Service
public class FilmsActorsService {

    @Autowired
    private FilmsActorsRepository filmsActorsRepository;

    /**
     * Получает все связи между фильмами и актерами.
     * @return список всех связей (может быть пустым)
     */
    public List<FilmsActors> getAllFilmsActors() {
        return filmsActorsRepository.findAll();
    }

    /**
     * Находит связь между конкретным актером и фильмом.
     * @param actorId идентификатор актера
     * @param filmId идентификатор фильма
     * @return Optional с найденной связью или пустой, если связь не найдена
     */
    public Optional<FilmsActors> getFilmsActorsById(Long actorId, Long filmId) {
        return filmsActorsRepository.findById(new FilmsActorsId(actorId, filmId));
    }

    /**
     * Сохраняет новую или обновляет существующую связь.
     * @param filmsActors объект связи для сохранения
     * @return сохраненный объект связи
     */
    public FilmsActors saveFilmsActors(FilmsActors filmsActors) {
        return filmsActorsRepository.save(filmsActors);
    }

    /**
     * Удаляет связь между актером и фильмом.
     * @param actorId идентификатор актера
     * @param filmId идентификатор фильма
     */
    public void deleteFilmsActors(Long actorId, Long filmId) {
        filmsActorsRepository.deleteById(new FilmsActorsId(actorId, filmId));
    }

}



