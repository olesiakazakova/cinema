package com.example.cinema.cinema_app;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;
import jakarta.persistence.*;

/**
 * Сущность, представляющая фильм в системе.
 * Соответствует таблице "films" в базе данных.
 */
@Entity
@Table(name = "films")
public class Film {
    /**
     * Уникальный идентификатор фильма.
     * Генерируется автоматически с использованием IDENTITY стратегии.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long filmId;

    /**
     * Название фильма.
     * Не может быть null и максимальная длина 255 символов
     */
    @Column(nullable = false, length = 255)
    private String name;

    /**
     * Описание фильма.
     * Хранится в формате TEXT без ограничения длины.
     */
    @Column(columnDefinition = "TEXT")
    private String description;

    /**
     * Дата выхода фильма.
     * Формат даты: "yyyy-MM-dd"
     */
    @Column(name = "release_date")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date releaseDate;

    /**
     * Продолжительность фильма в минутах.
     * Не может быть null и должна быть больше 0
     */
    @Column(nullable = false)
    private Integer duration;

    /**
     * Список жанров фильма.
     * Реализация связи многие-ко-многим через таблицу films_genres.
     * Каскадное сохранение (PERSIST) применяется автоматически.
     */
    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "films_genres",
            joinColumns = @JoinColumn(name = "film_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    private List<Genre> genres = new ArrayList<>();

    /**
     * Список режиссеров фильма.
     * Реализация связи многие-ко-многим через таблицу films_directors.
     * Каскадное сохранение (PERSIST) применяется автоматически.
     */
    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "films_directors",
            joinColumns = @JoinColumn(name = "film_id"),
            inverseJoinColumns = @JoinColumn(name = "director_id")
    )
    private List<Director> directors = new ArrayList<>();

    /**
     * Список актеров фильма.
     * Реализация связи многие-ко-многим через таблицу films_actors.
     * Каскадное сохранение (PERSIST) применяется автоматически.
     */
    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "films_actors",
            joinColumns = @JoinColumn(name = "film_id"),
            inverseJoinColumns = @JoinColumn(name = "actor_id")
    )
    private List<Actor> actors = new ArrayList<>();

    /**
     * Возвращает идентификатор фильма.
     * @return уникальный ID фильма
     */
    public Long getFilmId() {
        return filmId;
    }

    /**
     * Устанавливает идентификатор фильма.
     * @param filmId новый ID фильма
     */
    public void setFilmId(Long filmId) {
        this.filmId = filmId;
    }

    /**
     * Возвращает название фильма.
     * @return название фильма
     */
    public String getName() {
        return name;
    }

    /**
     * Устанавливает название фильма.
     * @param name новое название фильма
     * @throws IllegalArgumentException если название null или пустое
     */
    public void setName(String name) {
        //Fix me: добавлена проверка названия
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Название фильма не может быть пустым");
        }
        this.name = name;
    }

    /**
     * Возвращает описание фильма.
     * @return описание фильма (может быть null)
     */
    public String getDescription() {
        return description;
    }

    /**
     * Устанавливает описание фильма.
     * @param description новое описание фильма
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Возвращает дату выхода фильма.
     * @return дата выхода
     */
    public Date getReleaseDate() {
        return releaseDate;
    }

    /**
     * Устанавливает дату выхода фильма.
     * @param releaseDate дата выхода
     */
    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    /**
     * Возвращает продолжительность фильма в минутах.
     * @return продолжительность фильма
     */
    public Integer getDuration() {
        return duration;
    }

    /**
     * Устанавливает продолжительность фильма.
     * @param duration продолжительность в минутах
     * @throws IllegalArgumentException если продолжительность меньше или равна 0
     */
    public void setDuration(Integer duration) {
        if (duration <= 0) {
            throw new IllegalArgumentException("Продолжительность должна быть больше 0");
        }
        this.duration = duration;
    }

    /**
     * Добавляет жанр к фильму.
     * @param genre жанр для добавления
     */
    public void addGenre(Genre genre) {
        genres.add(genre);
    }

    /**
     * Возвращает список жанров фильма.
     * @return список жанров (может быть пустым)
     */
    public List<Genre> getGenres() {
        return genres;
    }

    /**
     * Добавляет режиссера к фильму.
     * @param director режиссер для добавления
     */
    public void addDirectors(Director director) {
        directors.add(director);
    }

    /**
     * Возвращает список режиссеров фильма.
     * @return список режиссеров (может быть пустым)
     */
    public List<Director> getDirectors() {
        return directors;
    }

    /**
     * Добавляет актера к фильму.
     * @param actor актер для добавления
     */
    public void addActors(Actor actor) {
        actors.add(actor);
    }


    /**
     * Возвращает список актеров фильма.
     * @return список актеров (может быть пустым)
     */
    public List<Actor> getActors() {
        return actors;
    }

}






