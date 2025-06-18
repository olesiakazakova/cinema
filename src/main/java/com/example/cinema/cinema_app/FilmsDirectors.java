package com.example.cinema.cinema_app;

import jakarta.persistence.*;

/**
 * Сущность-связка для отношения многие-ко-многим между фильмами и режиссерами.
 * Представляет составной ключ и связи между таблицами films и directors.
 * Соответствует таблице "films_directors" в базе данных.
 */
@Entity
@Table(name = "films_directors")
public class FilmsDirectors {
    /**
     * Составной идентификатор связи.
     * Содержит идентификаторы фильма и режиссера.
     */
    @EmbeddedId
    private FilmsDirectorsId id;

    /**
     * Ссылка на сущность режиссера.
     * Формирует часть составного ключа через поле director_id.
     */
    @ManyToOne
    @MapsId("directorId")
    @JoinColumn(name = "director_id", nullable = false)
    private Director director;

    /**
     * Связанная сущность фильма.
     * Формирует часть составного ключа через поле film_id.
     */
    @ManyToOne
    @MapsId("filmId")
    @JoinColumn(name = "film_id", nullable = false)
    private Film film;

    /**
     * Возвращает связанного режиссера.
     * @return сущность Director
     */
    public Director getDirector() {
        return director;
    }

    /**
     * Устанавливает связь с режиссером.
     * @param director сущность Director для связи
     * @throws IllegalArgumentException если director равен null
     */
    public void setDirector(Director director) {
        //Fix me: добавлена проверка режиссера
        if (director == null) {
            throw new IllegalArgumentException("Director cannot be null");
        }
        this.director = director;
        if (this.id == null) {
            this.id = new FilmsDirectorsId();
        }
        this.id.setDirectorId(director.getDirectorId());
    }

    /**
     * Возвращает связанный фильм.
     * @return сущность Film, никогда не null
     */
    public Film getFilm() {
        return film;
    }

    /**
     * Устанавливает связь с фильмом.
     * @param film сущность Film для связи
     * @throws IllegalArgumentException если film равен null
     */
    public void setFilm(Film film) {
        //Fix me: добавлена проверка фильма
        if (film == null) {
            throw new IllegalArgumentException("Film cannot be null");
        }
        this.film = film;
        if (this.id == null) {
            this.id = new FilmsDirectorsId();
        }
        this.id.setFilmId(film.getFilmId());
    }
}


