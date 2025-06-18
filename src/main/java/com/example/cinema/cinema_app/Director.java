package com.example.cinema.cinema_app;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Сущность, представляющая режиссера в системе.
 * Соответствует таблице "directors" в базе данных.
 * Содержит информацию о режиссере и его связи с фильмами через промежуточную сущность FilmsDirectors.
 */
@Entity
@Table(name = "directors")
public class Director {

    /**
     * Уникальный идентификатор режиссера.
     * Генерируется автоматически через sequence в БД.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "director_seq", sequenceName = "director_sequence", allocationSize = 1)
    private Long directorId;

    /**
     * Полное имя режиссера.
     * Должно быть уникальным и не может быть null.
     */
    @Column(nullable = false, unique = true)
    private String name;

    /**
     * Список связей режиссера с фильмами.
     * Используется для связи многие-ко-многим с сущностью Film через FilmsDirectors.
     * Каскадное сохранение (PERSIST) применяется автоматически.
     */
    @OneToMany(mappedBy = "director", cascade = CascadeType.PERSIST)
    private List<FilmsDirectors> filmsDirectors;

    /**
     * Возвращает идентификатор режиссера.
     * @return уникальный ID режиссера
     */
    public Long getDirectorId() {
        return directorId;
    }

    /**
     * Устанавливает идентификатор режиссера.
     * @param directorId новый ID режиссера
     */
    public void setDirectorId(Long directorId) {
        this.directorId = directorId;
    }

    /**
     * Возвращает имя режиссера.
     * @return текущее имя режиссера
     */
    public String getName() { return name; }

    /**
     * Устанавливает имя режиссера.
     * @param name новое имя режиссера
     * @throws IllegalArgumentException если передано null значение
     */
    public void setName(String name) {
        //Fix me: добавлена проверка имени
        if (name == null) {
            throw new IllegalArgumentException("Имя режиссера не может быть null");
        }
        this.name = name;
    }

    /**
     * Возвращает список фильмов режиссера.
     * @return список связей FilmsDirectors (может быть пустым)
     */
    public List<FilmsDirectors> getFilmsDirectors() { return filmsDirectors; }

    /**
     * Устанавливает список фильмов режиссера.
     * @param filmsDirectors новый список связей с фильмами
     */
    public void setFilmsDirectors(List<FilmsDirectors> filmsDirectors) {
        this.filmsDirectors = filmsDirectors;
    }
}


