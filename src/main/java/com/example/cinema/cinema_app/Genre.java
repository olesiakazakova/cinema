package com.example.cinema.cinema_app;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
//Fix me: убраны лишние комментарии

/**
 * Сущность, представляющая жанр в системе.
 * Соответствует таблице "genres" в базе данных.
 * Содержит информацию о жанре и его связи с фильмами через промежуточную сущность FilmsGenres.
 */
@Entity
@Table(name = "genres")
public class
Genre {
    /**
     * Уникальный идентификатор жанра.
     * Генерируется автоматически через sequence в БД.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "genre_seq", sequenceName = "genre_sequence", allocationSize = 1)
    private Long genreId;

    /**
     * Название жанра.
     * <p><b>Ограничения:</b>
     * Должно быть уникальным и не может быть null.
     */
    @Column(nullable = false, unique = true)
    private String genre;

    /**
     * Список связей жанра с фильмами.
     * Реализовано через связующую сущность FilmsGenres.
     * Каскадное сохранение (PERSIST) применяется автоматически.
     */
    @OneToMany(mappedBy = "genre", cascade = CascadeType.PERSIST)
    private List<FilmsGenres> filmsGenres;

    /**
     * Возвращает идентификатор жанра.
     * @return уникальный ID жанра
     */
    public Long getGenreId() {
        return genreId;
    }

    /**
     * Устанавливает идентификатор жанра.
     * @param genreId новый ID жанра
     */
    public void setGenreId(Long genreId) {
        this.genreId = genreId;
    }

    /**
     * Возвращает название жанра.
     * @return название жанра
     */
    public String getGenre() {
        return genre;
    }

    /**
     * Устанавливает название жанра.
     * @param genre новое название жанра
     * @throws IllegalArgumentException если genre равен null или пустой
     */
    public void setGenre(String genre) {
        //Fix me: добавлена проверка имени
        if (genre == null || genre.trim().isEmpty()) {
            throw new IllegalArgumentException("Название жанра не может быть пустым");
        }
        this.genre = genre;
    }

    /**
     * Возвращает список связей с фильмами.
     * @return список связей FilmsGenres
     */
    public List<FilmsGenres> getFilmsGenres() {
        return filmsGenres;
    }


    /**
     * Устанавливает список связей с фильмами.
     * @param filmsGenres новый список связей
     */
    public void setFilmsGenres(List<FilmsGenres> filmsGenres) {
        this.filmsGenres = filmsGenres;
    }
}


