package com.example.cinema.cinema_app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

/**
 * Сервисный слой для работы с режиссерами.
 * Обеспечивает бизнес-логику операций CRUD для сущности Director.
 * Использует DirectorRepository для взаимодействия с базой данных.
 */
@Service
public class DirectorService {

    @Autowired
    private DirectorRepository directorRepository;

    /**
     * Сохраняет режиссера в базе данных.
     * @param director объект режиссера для сохранения
     * @return сохраненный объект Director
     */
    public Director saveDirector(Director director) {
        return directorRepository.save(director);
    }

    /**
     * Получает список всех режиссеров.
     * @return список объектов Director (может быть пустым)
     */
    public List<Director> getAllDirectors() {
        return directorRepository.findAll();
    }

    /**
     * Находит режиссера по идентификатору.
     * @param id уникальный идентификатор режиссера
     * @return объект Director или null, если не найден
     */
    public Director getDirectorById(Long id) {
        return directorRepository.findById(id).orElse(null);
    }

    /**
     * Удаляет режиссера по идентификатору.
     * @param id идентификатор режиссера для удаления
     */
    public void deleteDirector(Long id) {
        directorRepository.deleteById(id);
    }

    /**
     * Находит режиссера по точному совпадению имени.
     * @param name полное имя режиссера
     * @return Optional с найденным режиссером
     */
    public Optional<Director> findByName(String name) {
        return directorRepository.findByName(name);
    }

    /**
     * Получает список режиссеров по списку идентификаторов.
     * @param selectedDirectors список ID режиссеров
     * @return список найденных режиссеров
     */
    public List<Director> findAllById(List<Long> selectedDirectors) {
        return directorRepository.findAllById(selectedDirectors);
    }

    /**
     * Обновляет данные режиссера.
     * @param id идентификатор обновляемого режиссера
     * @param directorDetails объект с новыми данными
     * @return обновленный объект Director или null, если режиссер не найден
     */
    public Director updateDirector(Long id, Director directorDetails) {
        return directorRepository.findById(id)
                .map(director -> {
                    director.setName(directorDetails.getName());
                    director.setFilmsDirectors(directorDetails.getFilmsDirectors());
                    return directorRepository.save(director);
                }).orElse(null);
    }
}

