package com.example.cinema.cinema_app;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Сервис для работы с жанрами.
 * Обеспечивает бизнес-логику операций CRUD для сущности Genre.
 * Использует GenreRepository для взаимодействия с базой данных.
 */
@Service
public class GenreService {
    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private FilmsGenresRepository filmsGenresRepository;

    /**
     * Создает новый жанр в базе данных.
     * @param genre объект Genre для сохранения
     * @return сохраненный объект Genre
     */
    public Genre createGenre(Genre genre) {
        return genreRepository.save(genre);
    }

    /**
     * Находит жанр по его идентификатору.
     * @param id уникальный идентификатор ажанра
     * @return Optional с найденным жанром или пустой, если не найден
     */
    public Optional<Genre> getGenre(Long id) {
        return genreRepository.findById(id);
    }

    /**
     * Удаляет жанр из базы данных по идентификатору.
     * @param id идентификатор жанра для удаления
     */
    //Fix me: исправление ошибки удаления жанра
    //Fix me: добавлена аннотация (обеспечивает все или ничего)
    @Transactional
    public void deleteGenre(Long id) {
        //Fix me: добавлено удаление связей перед удалением
        genreRepository.findById(id).ifPresent(genre -> {
            filmsGenresRepository.deleteByGenreId(id);
            genreRepository.delete(genre);
        });
    }

    /**
     * Получает список всех жанров.
     * @return список всех жанров
     */
    public List<Genre> findAll() {
        return genreRepository.findAll();
    }

    /**
     * Находит жанр по его названию.
     * @param genre название жанра для поиска
     * @return Optional, содержащий жанр, если он найден, или пустой Optional
     */
    public Optional<Genre> findByName(String genre) {
        return genreRepository.findByGenre(genre);
    }

    /**
     * Получает список жанров по списку их идентификаторов.
     * @param selectedGenres список идентификаторов жанров
     * @return список найденных жанров
     */
    public List<Genre> findAllById(List<Long> selectedGenres) {
        return genreRepository.findAllById(selectedGenres);
    }

    /**
     * Обновляет информацию о жанре.
     * @param genreId идентификатор жанра для обновления
     * @param genreDetails объект с новыми данными жанра
     * @return обновленный жанр или null, если жанр не найден
     */
    public Genre updateGenre(Long genreId,Genre genreDetails) {
        return genreRepository.findById(genreId)
                .map(genre -> {
                    genre.setGenre(genreDetails.getGenre());
                    genre.setFilmsGenres(genreDetails.getFilmsGenres());
                    return genreRepository.save(genre);
                }).orElse(null);
    }
}
