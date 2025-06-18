package com.example.cinema.cinema_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//TODO: Необходимо разработать Spring Web MVC проект с использованием
// базы данных, разработанной по дисциплине Базы данных и СУБД.
// Программа должна иметь интуитивно-понятный
// дружественный интерфейс и проверку вводимых значений.
// В программе должны присутствовать классы.
// Необходимо реализовать функции:
// -просмотр данных из базы;
// -редактирование данных в базе;
// -удаление данных из базы;
// -добавление данных в базу.
// Функции должны быть доступны для каждой таблицы.

/**
 * Главный класс приложения Cinema Management System.
 * Служит точкой входа в Spring Boot приложение. Автоматически настраивает компоненты Spring,
 * выполняет сканирование компонентов и включает авто-конфигурацию.
 */
@SpringBootApplication
public class CinemaAppApplication {
	/**
	 * Точка входа в приложение.
	 * @param args аргументы командной строки (могут быть использованы для настройки приложения)
	 */
	public static void main(String[] args) {
		SpringApplication.run(CinemaAppApplication.class, args);
	}

}
