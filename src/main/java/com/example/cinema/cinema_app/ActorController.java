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
 * Контроллер для управления актерами в системе.
 * Обрабатывает HTTP-запросы, связанные с операциями CRUD для сущности Actor.
 * Базовый URL: "/actors"
 */
@Controller
@RequestMapping("/actors")
public class ActorController {
    @Autowired
    private ActorService actorService;

    @Autowired
    private FilmsActorsService filmsActorsService;

    /**
     * Отображает список всех актеров.
     * @param model Модель для передачи данных в представление
     * @return Имя шаблона для отображения списка актеров ("film/listActors")
     */
    //Fix me: изменено название метода (Методы должны быть глаголами)
    @GetMapping
    public String showListActors(Model model) {
        List<Actor> actors = actorService.getAllActors();
        model.addAttribute("actors", actors);
        return "film/listActors";
    }

    /**
     * Показывает форму для добавления нового актера.
     * @param model Модель для передачи данных в представление
     * @return Имя шаблона формы добавления актера ("film/addActor")
     */
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("actor", new Actor());
        return "film/addActor";
    }

    /**
     * Обрабатывает отправку формы добавления актера.
     * @param actor Данные актера из формы
     * @param bindingResult Результаты валидации
     * @return Перенаправление на список актеров или страницу ошибки
     */
    @PostMapping("/add")
    public String addActor(@Valid @ModelAttribute Actor actor,  BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            System.out.println("Ошибки валидации: " + bindingResult.getAllErrors());
            return "film/error";
        }
        if (!actorService.findByName(actor.getName()).isPresent())
            actorService.createActor(actor);
        return "redirect:/actors";
    }

    /**
     * Показывает форму редактирования актера.
     * @param actorId ID актера для редактирования
     * @param model Модель для передачи данных в представление
     * @return Имя шаблона формы редактирования или страницы ошибки ("film/editActor")
     */
    @GetMapping("/edit")
    public String showEditForm(@RequestParam("actorId") Long actorId, Model model) {
        Optional<Actor> optionalActor = actorService.getActorById(actorId);
        if (optionalActor.isPresent()) {
            Actor actor = optionalActor.get();
            model.addAttribute("actor", actor);
            return "film/editActor";
        }
        else return "films/error";
    }

    /**
     * Обрабатывает отправку формы редактирования актера.
     * @param actorId ID редактируемого актера
     * @param actor Обновленные данные актера
     * @param bindingResult Результаты валидации
     * @return Перенаправление на список актеров или страницу ошибки
     */
    @PostMapping("/edit")
    public String editActor(@RequestParam Long actorId, @Valid @ModelAttribute Actor actor, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            System.out.println("Ошибки валидации: " + bindingResult.getAllErrors());
            return "film/error";
        }
        actorService.updateActor(actorId, actor);
        return "redirect:/actors";
    }

    /**
     * Удаляет актера и все его связи с фильмами.
     * @param actorId ID актера для удаления
     * @param actor Данные актера (используется для проверки)
     * @return Перенаправление на список актеров с параметром deleted=true
     */
    @PostMapping("/delete")
    public String deleteActor(@RequestParam Long actorId, @ModelAttribute Actor actor) {
        if (actor != null) {
            List<FilmsActors> films = actor.getFilmsActors();
            if (films!=null) {
                for (FilmsActors film : films) {
                    Long filmId = film.getFilm().getFilmId();
                    filmsActorsService.deleteFilmsActors(actorId, filmId);
                }
            }
            actorService.deleteActor(actorId);
        }
        return "redirect:/actors?deleted=true";
    }
}


