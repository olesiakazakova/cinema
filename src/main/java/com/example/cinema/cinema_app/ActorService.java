package com.example.cinema.cinema_app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

/**
 * Сервис для работы с актерами.
 * Обеспечивает бизнес-логику операций CRUD для сущности Actor.
 * Использует ActorRepository для взаимодействия с базой данных.
 */
@Service
public class ActorService {
    @Autowired
    private ActorRepository actorRepository;

    /**
     * Получает список всех актеров из базы данных.
     * @return список объектов Actor (может быть пустым)
     */
    public List<Actor> getAllActors() {
        return actorRepository.findAll();
    }

    /**
     * Находит актера по его идентификатору.
     * @param id уникальный идентификатор актера
     * @return Optional с найденным актером или пустой, если не найден
     */
    public Optional<Actor> getActorById(Long id) {
        return actorRepository.findById(id);
    }

    /**
     * Создает нового актера в базе данных.
     * @param actor объект Actor для сохранения
     * @return сохраненный объект Actor
     */
    public Actor createActor(Actor actor) {
        return actorRepository.save(actor);
    }

    /**
     * Обновляет данные существующего актера.
     * @param id идентификатор актера для обновления
     * @param actorDetails объект с новыми данными актера
     * @return обновленный объект Actor или null, если актер не найден
     */
    public Actor updateActor(Long id, Actor actorDetails) {
        return actorRepository.findById(id)
                .map(actor -> {
                    actor.setName(actorDetails.getName());
                    actor.setFilmsActors(actorDetails.getFilmsActors());
                    return actorRepository.save(actor);
                }).orElse(null);
    }

    /**
     * Удаляет актера из базы данных по идентификатору.
     * @param id идентификатор актера для удаления
     */
    public void deleteActor(Long id) {
        actorRepository.deleteById(id);
    }

    /**
     * Находит актера по точному совпадению имени.
     * @param name полное имя актера для поиска
     * @return Optional с найденным актером или пустой, если не найден
     */
    public Optional<Actor> findByName(String name) {
        return actorRepository.findByName(name);
    }

    /**
     * Получает список актеров по списку их идентификаторов.
     * @param selectedActors список ID актеров
     * @return список найденных актеров
     */
    public List<Actor> findAllById(List<Long> selectedActors) {
        return actorRepository.findAllById(selectedActors);
    }

}

