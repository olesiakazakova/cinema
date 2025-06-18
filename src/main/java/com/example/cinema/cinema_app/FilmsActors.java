package com.example.cinema.cinema_app;

import jakarta.persistence.*;

/**
 * Сущность-связка для отношения многие-ко-многим между фильмами и актерами.
 * Представляет составной ключ и связи между таблицами films и actors.
 * Соответствует таблице "films_actors" в базе данных.
 */
@Entity
@Table(name = "films_actors")
public class FilmsActors {
    /**
     * Составной идентификатор связи.
     * Содержит идентификаторы фильма и актера.
     */
    @EmbeddedId
    private FilmsActorsId id;

    /**
     * Ссылка на сущность актера.
     * Формирует часть составного ключа через поле actor_id.
     */
    @ManyToOne
    @MapsId("actorId")
    @JoinColumn(name = "actor_id", nullable = false)
    private Actor actor;

    /**
     * Ссылка на сущность фильма.
     * Формирует часть составного ключа через поле film_id.
     */
    @ManyToOne
    @MapsId("filmId")
    @JoinColumn(name = "film_id", nullable = false)
    private Film film;

    /**
     * Возвращает составной идентификатор связи.
     * @return объект FilmsActorsId
     */
    public FilmsActorsId getId() {
        return id;
    }

    /**
     * Устанавливает составной идентификатор связи.
     * @param id новый идентификатор связи
     */
    public void setId(FilmsActorsId id) {
        this.id = id;
    }

    /**
     * Возвращает связанного актера.
     * @return сущность Actor
     */
    public Actor getActor() {
        return actor;
    }

    /**
     * Устанавливает связь с актером.
     * @param actor сущность Actor для связи
     * @throws IllegalArgumentException если actor равен null
     */
    public void setActor(Actor actor) {
        //Fix me: добавлена проверка актера
        if (actor == null) {
            throw new IllegalArgumentException("Actor cannot be null");
        }
        this.actor = actor;
        if (this.id == null) {
            this.id = new FilmsActorsId();
        }
        this.id.setActor(actor.getActorId());
    }

    /**
     * Возвращает связанный фильм.
     * @return сущность Film
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
            this.id = new FilmsActorsId();
        }
        this.id.setFilm(film.getFilmId());
    }
}