package com.example.cinema.cinema_app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Контроллер для управления жанрами в системе.
 * Обрабатывает HTTP-запросы, связанные с операциями CRUD для сущности Genre.
 * Базовый URL: "/genres"
 */
@Controller
@RequestMapping("/genres")
public class GenreController {
    @Autowired
    private GenreService genreService;

    @Autowired
    private FilmsGenresService filmsGenresService;

    @Autowired
    private GenreService genreRepository;

    /**
     * Отображает список всех жанров.
     * @param model Модель для передачи данных в представление
     * @return Имя шаблона для отображения списка жанров ("film/listGenres")
     */
    //Fix me: изменено название метода (Методы должны быть глаголами)
    @GetMapping
    public String showListGenres(Model model) {
        List<Genre> genres = genreService.findAll();
        model.addAttribute("genres", genres);
        return "film/listGenres";
    }

    /**
     * Показывает форму для добавления нового жанра.
     * @param model Модель для передачи данных в представление
     * @return Имя шаблона формы добавления жанра ("film/addGenres")
     */
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("genre", new Genre());
        return "film/addGenres";
    }

    /**
     * Обрабатывает добавление нового жанра.
     * @param newGenre Название нового жанра
     * @return Перенаправление на список жанров или страницу ошибки
     */
    @PostMapping("/add")
    public String addGenre(@RequestParam(required = false) String newGenre) {
        if (newGenre != null && !newGenre.trim().isEmpty()) {
            Genre genre = genreService.findByName(newGenre)
                    .orElseGet(() -> {
                        Genre newGenreObj = new Genre();
                        newGenreObj.setGenre(newGenre);
                        genreService.createGenre(newGenreObj);
                        return newGenreObj;
                    });
        } else return "film/error";
        return "redirect:/genres";
    }

    /**
     * Показывает форму редактирования жанра.
     * @param genreId ID редактируемого жанра
     * @param model Модель для передачи данных в представление
     * @return Имя шаблона формы редактирования или страницы ошибки
     */
    @GetMapping("/edit")
    public String showEditForm(@RequestParam("genreId") Long genreId, Model model) {
        Optional<Genre> optionalGenre = genreService.getGenre(genreId);
        if (optionalGenre.isPresent()) {
            Genre genre = optionalGenre.get();
            model.addAttribute("genre", genre);
            return "film/editGenre";
        }
        return "films/error";
    }

    /**
     * Обрабатывает обновление данных жанра.
     * @param genreId ID редактируемого жанра
     * @param genre Новое название жанра
     * @return Перенаправление на список жанров или страницу ошибки
     */
    @PostMapping("/edit")
    public String editGenre(@RequestParam Long genreId, @RequestParam(required = false) String genre) {
        Optional<Genre> optionalGenre = genreService.getGenre(genreId);
        if (optionalGenre.isPresent()) {
            Genre newGenre = optionalGenre.get();
            newGenre.setGenre(genre);
            genreService.updateGenre(genreId, newGenre);
            return "redirect:/genres";
        }
        return "films/error";
    }

    /**
     * Удаляет жанр и все его связи с фильмами.
     * @param genreId ID удаляемого жанра
     * @return Перенаправление на список жанров
     */
    @PostMapping("/delete")
    public String deleteGenre(@RequestParam Long genreId) {
        Optional<Genre> optionalGenre = genreService.getGenre(genreId);
        if (optionalGenre.isPresent()) {
            Genre genre = optionalGenre.get();
            List<FilmsGenres> films = genre.getFilmsGenres();
            if (films!=null) {
                for (FilmsGenres film: films) {
                    Film filmId = film.getFilm();
                    filmsGenresService.deleteFilmsGenres(genre, filmId);
                }
            }
            genreService.deleteGenre(genreId);
        }
        return "redirect:/genres";
    }
}




