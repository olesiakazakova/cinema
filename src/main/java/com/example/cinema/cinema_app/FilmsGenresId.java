package com.example.cinema.cinema_app;

import java.io.Serializable;
import java.util.Objects;

/**
 * Класс FilmsGenresId представляет составной первичный ключ для связи "Фильм — Жанр".
 * Используется в JPA для обозначения идентификаторов жанра и фильма,
 * которые вместе формируют уникальный ключ в таблице связей между фильмами и жанрами.
 * Класс реализует Serializable, что необходимо для корректной работы с составными ключами в JPA.
 * Переопределённые методы equals и hashCode обеспечивают правильное сравнение и использование в коллекциях.
 */
public class FilmsGenresId implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * Идентификатор фильма.
     * Часть составного ключа, ссылается на таблицу films.
     */
    private Long filmId;
    /**
     * Идентификатор жанра.
     * Часть составного ключа, ссылается на таблицу genres.
     */
    private Long genreId;

    /**
     * Конструктор по умолчанию.
     * Требуется для JPA.
     */
    public FilmsGenresId() {}

    /**
     * Параметризованный конструктор.
     * @param filmId идентификатор фильма (не может быть null)
     * @param genreId идентификатор жанра (не может быть null)
     * @throws IllegalArgumentException если любой из параметров null
     */
    public FilmsGenresId(Long filmId, Long genreId) {
        //Fix me: добавлена проверка
        if (filmId == null || genreId == null) {
            throw new IllegalArgumentException("Ни filmId, ни genreId не могут быть null");
        }
        this.filmId = filmId;
        this.genreId = genreId;
    }

    /**
     * Возвращает идентификатор фильма.
     * @return идентификатор фильма
     */
    public Long getFilmId() {
        return filmId;
    }

    /**
     * Устанавливает идентификатор фильма.
     * @param filmId новый идентификатор фильма
     * @throws IllegalArgumentException если filmId равен null
     */
    public void setFilmId(Long filmId) {
        this.filmId = filmId;
    }

    /**
     * Возвращает идентификатор жанра.
     * @return идентификатор жанра
     */
    public Long getGenreId() {
        return genreId;
    }

    /**
     * Устанавливает идентификатор жанра.
     * @param genreId новый идентификатор жанра
     * @throws IllegalArgumentException если genreId равен null
     */
    public void setGenreId(Long genreId) {
        //Fix me: добавлена проверка
        if (genreId == null) {
            throw new IllegalArgumentException("genreId не может быть null");
        }
        this.genreId = genreId;
    }

    /**
     * Сравнивает объекты по значениям filmId и genreId.
     * @param o объект для сравнения
     * @return true если объекты равны
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FilmsGenresId)) return false;
        FilmsGenresId that = (FilmsGenresId) o;
        return Objects.equals(filmId, that.filmId) && Objects.equals(genreId, that.genreId);
    }

    /**
     * Генерирует хэш-код на основе filmId и genreId.
     * @return хэш-код объекта
     */
    @Override
    public int hashCode() {
        return Objects.hash(filmId, genreId);
    }
}


