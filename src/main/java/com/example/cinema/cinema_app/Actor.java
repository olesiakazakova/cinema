package com.example.cinema.cinema_app;

import jakarta.persistence.*;
import java.util.List;

//Fix me: убраны лишние комментарии

/**
 * Сущность, представляющая актера в системе.
 * Соответствует таблице "actors" в базе данных.
 * Содержит информацию об актере и его связи с фильмами через промежуточную сущность FilmsActors.
 */
@Entity
@Table(name = "actors")
public class Actor {
    /**
     * Уникальный идентификатор актера.
     * Генерируется автоматически с использованием sequence в БД.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "actor_seq", sequenceName = "actor_sequence", allocationSize = 1)
    private Long actorId;

    /**
     * Полное имя актера.
     * Должно быть уникальным и не может быть null.
     */
    @Column(nullable = false, unique = true)
    private String name;


    /**
     * Список связей актера с фильмами.
     * Реализовано через связующую сущность FilmsActors.
     * Каскадное сохранение (PERSIST) применяется автоматически.
     */
    @OneToMany(mappedBy = "actor", cascade = CascadeType.PERSIST)
    private List<FilmsActors> filmsActors;

    /**
     * Возвращает идентификатор актера.
     * @return уникальный ID актера
     */
    public Long getActorId() {
        return actorId;
    }

    /**
     * Устанавливает идентификатор актера.
     * @param actorId новый ID актера
     */
    public void setActorId(Long actorId) {
        this.actorId = actorId;
    }

    /**
     * Возвращает имя актера.
     * @return полное имя актера
     */
    public String getName() {
        return name;
    }

    /**
     * Устанавливает имя актера.
     * @param name новое имя актера (не может быть null)
     * @throws IllegalArgumentException если передано null значение
     */
    public void setName(String name) {
        //Fix me: добавлена проверка имени
        if (name == null) {
            throw new IllegalArgumentException("Имя актера не может быть null");
        }
        this.name = name;
    }

    /**
     * Возвращает список фильмов с участием актера.
     * @return список связей FilmsActors
     */
    public List<FilmsActors> getFilmsActors() {
        return filmsActors;
    }

    /**
     * Устанавливает список фильмов с участием актера.
     * @param filmsActors новый список связей
     */
    public void setFilmsActors(List<FilmsActors> filmsActors) {
        this.filmsActors = filmsActors;
    }
}

