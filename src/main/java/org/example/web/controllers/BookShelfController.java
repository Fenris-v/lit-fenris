package org.example.web.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.app.services.BookService;
import org.example.web.dto.Book;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.regex.Pattern;

@Controller
@RequestMapping(value = "/books")
@Scope("singleton")
public class BookShelfController {
    private final Logger logger = LogManager.getRootLogger();
    private final BookService bookService;

    @Autowired
    public BookShelfController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/shelf")
    public String books(@NotNull Model model) {
        logger.info("got book shelf");
        model.addAttribute("book", new Book());
        model.addAttribute("bookList", bookService.getAllBooks());
        return "book_shelf";
    }

    @PostMapping("/save")
    public String saveBook(@NotNull Book book) {
        if (!book.getAuthor().isBlank() || book.getSize() != null || !book.getTitle().isBlank()) {
            bookService.saveBook(book);
            logger.info("current repository size: " + bookService.getAllBooks().size());
        }

        return "redirect:/books/shelf";
    }

    @PostMapping("/remove")
    public String removeBook(@RequestParam(value = "bookIdToRemove") String bookIdToRemove, Model model) {
        if (bookService.removeBookById(bookIdToRemove)) {
            return "redirect:/books/shelf";
        }

        model.addAttribute("book", new Book());
        model.addAttribute("bookList", bookService.getAllBooks());
        return "book_shelf";
    }

    @PostMapping("/removeByRegex")
    public String removeBooksByRegex(@RequestParam(value = "queryRegex") String regex, Model model) {
        if (bookService.removeBooksByPattern(Pattern.compile(regex))) {
            return "redirect:/books/shelf";
        }

        model.addAttribute("book", new Book());
        model.addAttribute("bookList", bookService.getAllBooks());
        return "book_shelf";
    }
}
