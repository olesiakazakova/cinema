package com.example.cinema.cinema_app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import jakarta.validation.Valid;

//Fix me: убраны лишние комментарии

/**
 * Контроллер для управления фильмами в системе.
 * Обрабатывает HTTP-запросы, связанные с операциями CRUD для сущности Film.
 * Базовый URL: "/films"
 */
@Controller
@RequestMapping("/films")
public class FilmController {

    @Autowired
    private FilmService filmService;

    @Autowired
    private GenreService genreService;

    @Autowired
    private DirectorService directorService;

    @Autowired
    private ActorService actorService;

    @Autowired
    private FilmsActorsService filmsActorsService;

    @Autowired
    private FilmsDirectorsService filmsDirectorsService;

    @Autowired
    private FilmsGenresService filmsGenresService;

    /**
     * Отображает список всех фильмов.
     * @param model Модель для передачи данных в представление
     * @return Имя шаблона для отображения списка ("film/list")
     */
    //Fix me: изменено название метода (Методы должны быть глаголами)
    @GetMapping
    public String showListFilms(Model model) {
        List<Film> films = filmService.findAll();
        model.addAttribute("films", films);
        return "film/list";
    }

    /**
     * Отображает форму для добавления нового фильма.
     * @param model Модель для передачи данных в представление
     * @return Имя шаблона формы добавления ("film/add")
     */
    @GetMapping("/add")
    public String showAddFilmForm(Model model) {
        model.addAttribute("film", new Film());
        model.addAttribute("allGenres", genreService.findAll());
        model.addAttribute("allDirectors", directorService.getAllDirectors());
        model.addAttribute("allActors", actorService.getAllActors());
        return "film/add";
    }

    /**
     * Обрабатывает отправку формы добавления фильма.
     * @param film Объект фильма из формы
     * @param bindingResult Результаты валидации
     * @param genres Список ID выбранных жанров
     * @param actors Список ID выбранных актеров
     * @param directors Список ID выбранных режиссеров
     * @param newGenre Название нового жанра (если создается)
     * @param newDirector Имя нового режиссера (если создается)
     * @param newActor Имя нового актера (если создается)
     * @return Перенаправление на список фильмов или страницу ошибки
     */
    @PostMapping("/add")
    public String addFilm(@Valid @ModelAttribute Film film,
                          BindingResult bindingResult,
                          @RequestParam(required = false) List<Long> genres,
                          @RequestParam(required = false) List<Long> actors,
                          @RequestParam(required = false) List<Long> directors,
                          @RequestParam(required = false) String newGenre,
                          @RequestParam(required = false) String newDirector,
                          @RequestParam(required = false) String newActor) {

        if (bindingResult.hasErrors()) {
            System.out.println("Ошибки валидации: " + bindingResult.getAllErrors());
            return "film/error";
        }

        if (genres != null) {
            List<Genre> selectedGenres = genreService.findAllById(genres);
            film.getGenres().addAll(selectedGenres);
        }

        if (actors != null) {
            List<Actor> selectedActors = actorService.findAllById(actors);
            film.getActors().addAll(selectedActors);
        }
        if (directors != null) {
            List<Director> selectedDirectors = directorService.findAllById(directors);
            film.getDirectors().addAll(selectedDirectors);
        }

        if (newGenre != null && !newGenre.trim().isEmpty()) {
            Genre genre = genreService.findByName(newGenre)
                    .orElseGet(() -> {
                        Genre newGenreObj = new Genre();
                        newGenreObj.setGenre(newGenre);
                        genreService.createGenre(newGenreObj);
                        return newGenreObj;
                    });
                film.getGenres().add(genre);
        }
        if (newDirector != null && !newDirector.trim().isEmpty()) {
            Director director = directorService.findByName(newDirector)
                    .orElseGet(() -> {
                        Director newDirectorObj = new Director();
                        newDirectorObj.setName(newDirector);
                        directorService.saveDirector(newDirectorObj);
                        return newDirectorObj;
                    });
            film.getDirectors().add(director);
        }
        if (newActor != null && !newActor.trim().isEmpty()) {
            Actor actor = actorService.findByName(newActor)
                    .orElseGet(() -> {
                        Actor newActorObj = new Actor();
                        newActorObj.setName(newActor);
                        actorService.createActor(newActorObj);
                        return newActorObj;
                    });
            film.getActors().add(actor);
        }

        filmService.save(film);
        return "redirect:/films";
    }

    /**
     * Отображает форму редактирования фильма.
     * @param filmId ID редактируемого фильма
     * @param model Модель для передачи данных в представление
     * @return Имя шаблона формы редактирования или перенаправление ("film/edit")
     */
    @GetMapping("/edit")
    public String showEditFilmForm(@RequestParam("filmId") Long filmId, Model model) {
        Film film = filmService.findById(filmId);
        if (film == null) {
            return "redirect:/films";
        }
        model.addAttribute("film", film);
        model.addAttribute("allGenres", genreService.findAll());
        model.addAttribute("allDirectors", directorService.getAllDirectors());
        model.addAttribute("allActors", actorService.getAllActors());
        return "film/edit";
    }

    /**
     * Обрабатывает отправку формы редактирования фильма.
     * @param filmId ID редактируемого фильма
     * @param film Обновленные данные фильма
     * @param bindingResult Результаты валидации
     * @param genres Список ID выбранных жанров
     * @param actors Список ID выбранных актеров
     * @param directors Список ID выбранных режиссеров
     * @return Перенаправление на список фильмов или страницу ошибки
     */
    @PostMapping("/edit")
    public String editFilm(@RequestParam Long filmId, @Valid @ModelAttribute Film film,
                           BindingResult bindingResult,
                           @RequestParam(required = false) List<Long> genres,
                           @RequestParam(required = false) List<Long> actors,
                           @RequestParam(required = false) List<Long> directors) {
        if (bindingResult.hasErrors()) {
            System.out.println("Ошибки валидации: " + bindingResult.getAllErrors());
            return "film/error";
        }

        film.setFilmId(filmId);

        if (genres != null) {
            List<Genre> genreList = genreService.findAllById(genres);
            film.getGenres().addAll(genreList);
        }

        if (actors != null) {
            List<Actor> actorList = actorService.findAllById(actors);
            film.getActors().addAll(actorList);
        }

        if (directors != null) {
            List<Director> directorList = directorService.findAllById(directors);
            film.getDirectors().addAll(directorList);
        }

        filmService.save(film);
        return "redirect:/films";
    }

    /**
     * Удаляет фильм и все его связи.
     * Использует оптимистичную блокировку с 3 попытками при возникновении конфликтов.
     * @param filmId ID удаляемого фильма
     * @return Перенаправление на список фильмов или страницу ошибки
     */
    @PostMapping("/delete")
    public String deleteFilm(@RequestParam Long filmId) {
        int maxRetries = 3;
        int attempt = 0;
        Film film = null;

        while (attempt < maxRetries) {
            try {
                film = filmService.findById(filmId);
                if (film != null) {
                    for (Actor actor : film.getActors()) {
                        Long actorId = actor.getActorId();
                        filmsActorsService.deleteFilmsActors(actorId, filmId);
                    }
                    for (Director director : film.getDirectors()) {
                        Long directorId = director.getDirectorId();
                        filmsDirectorsService.deleteFilmsDirectors(directorId, filmId);
                    }
                    for (Genre genre : film.getGenres()) {
                        filmsGenresService.deleteFilmsGenres(genre, film);
                    }
                    filmService.delete(filmId);
                }
                return "redirect:/films";
            } catch (ObjectOptimisticLockingFailureException e) {
                attempt++;
                System.err.println("Попытка " + attempt + " не удалась. Оживление состояния...");
                film = filmService.findById(filmId);
                if (film == null) {
                    break;
                }
                if (attempt >= maxRetries) {
                    return "film/error";
                }
            }
        }
        return "redirect:/films";
    }
}

