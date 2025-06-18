package com.example.cinema.cinema_app;

import java.io.Serializable;
import java.util.Objects;
import jakarta.persistence.Embeddable;

/**
 * Класс FilmsActorsId представляет составной первичный ключ для связи "Фильм — Актёр".
 * Используется в JPA для обозначения идентификаторов актёра и фильма,
 * которые вместе формируют уникальный ключ в таблице связей между фильмами и актёрами.
 * Класс реализует Serializable, что необходимо для корректной работы с составными ключами в JPA.
 * Переопределённые методы equals и hashCode обеспечивают правильное сравнение и использование в коллекциях.
 */
@Embeddable
public class FilmsActorsId implements Serializable {
    /**
     * Идентификатор актера.
     * Часть составного ключа, ссылается на таблицу actors.
     */
    private Long actorId;

    /**
     * Идентификатор фильма.
     * Часть составного ключа, ссылается на таблицу films.
     */
    private Long filmId;

    /**
     * Конструктор по умолчанию.
     * Требуется для JPA.
     */
    public FilmsActorsId() {}

    /**
     * Параметризованный конструктор.
     * @param actor идентификатор актера
     * @param film идентификатор фильма
     */
    public FilmsActorsId(Long actor, Long film) {
        this.actorId = actor;
        this.filmId = film;
    }

    /**
     * Возвращает идентификатор актера.
     * @return идентификатор актера
     */
    public Long getActor() {
        return actorId;
    }

    /**
     * Устанавливает идентификатор актера.
     * @param actor новый идентификатор актера
     */
    public void setActor(Long actor) {
        this.actorId = actor;
    }

    /**
     * Возвращает идентификатор фильма.
     * @return идентификатор фильма
     */
    public Long getFilm() {
        return filmId;
    }

    /**
     * Устанавливает идентификатор фильма.
     * @param film новый идентификатор фильма
     */
    public void setFilm(Long film) {
        this.filmId = film;
    }

    /**
     * Сравнивает объекты по значениям actorId и filmId.
     * @param o объект для сравнения
     * @return true если объекты равны
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FilmsActorsId)) return false;
        FilmsActorsId that = (FilmsActorsId) o;
        return Objects.equals(actorId, that.actorId) && Objects.equals(filmId, that.filmId);
    }

    /**
     * Генерирует хэш-код на основе actorId и filmId.
     * @return хэш-код объекта
     */
    @Override
    public int hashCode() {
        return Objects.hash(actorId, filmId);
    }
}





