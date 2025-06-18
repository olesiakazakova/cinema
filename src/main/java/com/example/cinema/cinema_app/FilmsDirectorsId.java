package com.example.cinema.cinema_app;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

/**
 * Класс FilmsDirectorsId представляет составной первичный ключ для связи "Фильм — Режиссер".
 * Используется в JPA для обозначения идентификаторов режиссера и фильма,
 * которые вместе формируют уникальный ключ в таблице связей между фильмами и режиссерами.
 * Класс реализует Serializable, что необходимо для корректной работы с составными ключами в JPA.
 * Переопределённые методы equals и hashCode обеспечивают правильное сравнение и использование в коллекциях.
 */
@Embeddable
public class FilmsDirectorsId implements Serializable {
    /**
     * Идентификатор режиссера.
     * Часть составного ключа, ссылается на таблицу directors.
     */
    private Long directorId;

    /**
     * Идентификатор фильма.
     * Часть составного ключа, ссылается на таблицу films.
     */
    private Long filmId;

    /**
     * Конструктор по умолчанию.
     * Требуется для JPA.
     */
    public FilmsDirectorsId() {}

    /**
     * Параметризованный конструктор.
     * @param directorId идентификатор режиссера
     * @param filmId идентификатор фильма
     */
    public FilmsDirectorsId(Long directorId, Long filmId) {
        this.directorId = directorId;
        this.filmId = filmId;
    }

    /**
     * Возвращает идентификатор режиссера.
     * @return идентификатор режиссера
     */
    public Long getDirectorId() {
        return directorId;
    }

    /**
     * Устанавливает идентификатор режиссера.
     * @param directorId новый идентификатор режиссера
     */
    public void setDirectorId(Long directorId) {
        this.directorId = directorId;
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
     */
    public void setFilmId(Long filmId) {
        this.filmId = filmId;
    }

    /**
     * Сравнивает объекты по значениям directorId и filmId.
     * @param o объект для сравнения
     * @return true если объекты равны
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FilmsDirectorsId)) return false;
        FilmsDirectorsId that = (FilmsDirectorsId) o;
        return Objects.equals(directorId, that.directorId) &&
                Objects.equals(filmId, that.filmId);
    }

    /**
     * Генерирует хэш-код на основе directorId и filmId.
     * @return хэш-код объекта
     */
    @Override
    public int hashCode() {
        return Objects.hash(directorId, filmId);
    }
}


