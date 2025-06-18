package com.example.cinema.cinema_app;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

/**
 * Контроллер для управления режиссерами в системе.
 * Обрабатывает HTTP-запросы, связанные с операциями CRUD для сущности Director.
 * Базовый URL: "/directors"
 */
@Controller
@RequestMapping("/directors")
public class DirectorController {
    @Autowired
    private DirectorService directorService;

    @Autowired
    private FilmsDirectorsService filmsDirectorsService;

    /**
     * Отображает список всех режиссеров.
     * @param model Модель для передачи данных в представление
     * @return Имя шаблона для отображения списка режиссеров ("film/listDirectors")
     */
    //Fix me: изменено название метода (Методы должны быть глаголами)
    @GetMapping
    public String showListDirectors(Model model) {
        List<Director> directors = directorService.getAllDirectors();
        model.addAttribute("directors", directors);
        return "film/listDirectors";
    }

    /**
     * Отображает форму для добавления нового режиссера.
     * @param model Модель для передачи данных в представление
     * @return Имя шаблона формы добавления режиссеров ("film/addDirectors")
     */
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("director", new Director());
        return "film/addDirectors";
    }


    /**
     * Обрабатывает отправку формы добавления режиссера.
     * @param director Данные режиссера из формы
     * @param bindingResult Результаты валидации
     * @return Перенаправление на список режиссеров или страницу ошибки
     */
    @PostMapping("/add")
    public String addDirector(@Valid @ModelAttribute Director director, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            System.out.println("Ошибки валидации: " + bindingResult.getAllErrors());
            return "film/error";
        }
        if (!directorService.findByName(director.getName()).isPresent())
            directorService.saveDirector(director);
        return "redirect:/directors";
    }

    /**
     * Отображает форму редактирования режиссера.
     * @param directorId ID режиссера для редактирования
     * @param model Модель для передачи данных в представление
     * @return Имя шаблона формы редактирования или страницы ошибки ("film/editDirectors")
     */
    @GetMapping("/edit")
    public String showEditForm(@RequestParam("directorId") Long directorId, Model model) {
        Director director = directorService.getDirectorById(directorId);
        model.addAttribute("director", director);
        return "film/editDirectors";
    }

    @PostMapping("/edit")
    public String editDirector(@RequestParam Long directorId, @Valid @ModelAttribute Director director,  BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            System.out.println("Ошибки валидации: " + bindingResult.getAllErrors());
            return "film/error";
        }
        directorService.updateDirector(directorId, director);
        return "redirect:/directors";
    }

    /**
     * Обрабатывает отправку формы редактирования режиссера.
     * @param directorId ID редактируемого режиссера
     * @param director Обновленные данные режиссера
     * @return Перенаправление на список режиссеров или страницу ошибки
     */
    @PostMapping("/delete")
    public String deleteDirector(@RequestParam Long directorId, @ModelAttribute Director director) {
        if (director != null) {
            List<FilmsDirectors> films = director.getFilmsDirectors();
            if (films!=null) {
                for (FilmsDirectors film : films) {
                    Long filmId = film.getFilm().getFilmId();
                    filmsDirectorsService.deleteFilmsDirectors(directorId, filmId);
                }
            }
            directorService.deleteDirector(directorId);
        }
        return "redirect:/directors";
    }
}
