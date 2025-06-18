package com.example.cinema.cinema_app;

import jakarta.persistence.*;

import java.util.List;

/**
 * Сущность-связка для отношения многие-ко-многим между фильмами и жанрами.
 * Представляет составной ключ и связи между таблицами films и genres.
 * Соответствует таблице "films_genres" в базе данных.
 */
@Entity
@IdClass(FilmsGenresId.class)
@Table(name = "films_genres")
public class FilmsGenres {
    /**
     * Идентификатор фильма (часть составного ключа).
     * Связывается с полем film_id в таблице films_genres.
     */
    @Id
    @JoinColumn(name = "film_id", nullable = false)
    private Long filmId;

    /**
     * Идентификатор жанра (часть составного ключа).
     * Связывается с полем genre_id в таблице films_genres.
     */
    @Id
    @JoinColumn(name = "genre_id", nullable = false)
    private Long genreId;

    /**
     * Связанная сущность фильма.
     * Отображается на часть составного ключа через filmId.
     */
    @ManyToOne
    @MapsId("filmId")
    @JoinColumn(name = "film_id", referencedColumnName = "filmId", insertable = false, updatable = false)
    private Film film;

    /**
     * Связанная сущность жанра.
     * Отображается на часть составного ключа через genreId.
     */
    @ManyToOne
    @MapsId("genreId")
    @JoinColumn(name = "genre_id", referencedColumnName = "genreId", insertable = false, updatable = false)
    private Genre genre;

    /**
     * Возвращает связанный жанр.
     * @return сущность Genre
     */
    public Genre getGenre() {
        return genre;
    }

    /**
     * Устанавливает связь с жанром.
     * @param genre сущность Genre для связи
     * @throws IllegalArgumentException если genre равен null
     */
    public void setGenre(Genre genre) {
        //Fix me: добавлена проверка жанра
        if (genre == null) {
            throw new IllegalArgumentException("Genre cannot be null");
        }
        this.genre = genre;
        this.genreId = genre.getGenreId();
    }

    /**
     * Устанавливает список жанров для фильма.
     * @param genres список жанров
     */
    public void setGenres(List<Genre> genres) {
        if (genres != null) {
            for (Genre genre : genres) {
                setGenre(genre);
            }
        }
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
        this.filmId = film.getFilmId();
    }
}


