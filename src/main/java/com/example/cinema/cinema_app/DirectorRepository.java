package com.example.cinema.cinema_app;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Репозиторий для работы с сущностями Director в базе данных.
 * Обеспечивает стандартные CRUD-операции и дополнительные методы для доступа к данным режиссеров.
 * Наследует функциональность JpaRepository.
 */
@Repository
public interface DirectorRepository extends JpaRepository<Director, Long> {
    /**
     * Находит режиссера по точному совпадению имени.
     * @param name полное имя режиссера для поиска (чувствительно к регистру)
     * @return Optional, содержащий найденного режиссера или пустой, если не найден
     * @throws org.springframework.dao.DataAccessException при ошибках доступа к данным
     */
    Optional<Director> findByName(String name);
}
