package com.example.cinema.cinema_app;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Репозиторий для работы с сущностями Actor в базе данных.
 * Обеспечивает стандартные CRUD-операции и дополнительные методы для доступа к данным актеров.
 * Наследует функциональность JpaRepository.
 */
@Repository
public interface ActorRepository extends JpaRepository<Actor, Long> {
    /**
     * Находит актера по точному совпадению имени.
     * @param name Имя актера для поиска (чувствительно к регистру)
     * @return Optional, содержащий найденного актера или пустой, если актер не найден
     */
    Optional<Actor> findByName(String name);
}


