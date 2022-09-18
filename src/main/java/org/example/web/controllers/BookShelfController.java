package org.example.web.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.app.services.BookService;
import org.example.app.services.FileUploadService;
import org.example.web.dto.Book;
import org.example.web.dto.BookIdToRemove;
import org.example.web.dto.RegexToRemove;
import org.example.web.dto.UploadFile;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.io.File;
import java.util.regex.Pattern;

@Controller
@RequestMapping(value = "/books")
@Scope("singleton")
public class BookShelfController {
    private final Logger logger = LogManager.getRootLogger();
    private final BookService bookService;
    private final FileUploadService fileUploadService;

    @Autowired
    public BookShelfController(BookService bookService, FileUploadService fileUploadService) {
        this.bookService = bookService;
        this.fileUploadService = fileUploadService;
    }

    @GetMapping("/shelf")
    public String books(@NotNull Model model) {
        logger.info(this.toString());
        model.addAttribute("book", new Book());
        model.addAttribute("bookIdToRemove", new BookIdToRemove());
        model.addAttribute("bookList", bookService.getAllBooks());
        model.addAttribute("uploadFile", new UploadFile());
        model.addAttribute("regexToRemove", new RegexToRemove());
        return "book_shelf";
    }

    @PostMapping("/save")
    public String saveBook(@Valid @ModelAttribute Book book, @NotNull BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("bookIdToRemove", new BookIdToRemove());
            model.addAttribute("bookList", bookService.getAllBooks());
            model.addAttribute("uploadFile", new UploadFile());
            model.addAttribute("regexToRemove", new RegexToRemove());
            return "book_shelf";
        }

        bookService.saveBook(book);
        logger.info("current repository size: " + bookService.getAllBooks().size());
        return "redirect:/books/shelf";
    }

    @PostMapping("/remove")
    public String removeBook(@Valid @ModelAttribute BookIdToRemove bookIdToRemove, @NotNull BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("book", new Book());
            model.addAttribute("bookList", bookService.getAllBooks());
            model.addAttribute("regexToRemove", new RegexToRemove());
            model.addAttribute("uploadFile", new UploadFile());
            return "book_shelf";
        }

        bookService.removeBookById(bookIdToRemove.getId());
        return "redirect:/books/shelf";
    }

    @PostMapping("/removeByRegex")
    public String removeBooksByRegex(@NotNull @Valid @ModelAttribute RegexToRemove regexToRemove, @NotNull BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("book", new Book());
            model.addAttribute("bookList", bookService.getAllBooks());
            model.addAttribute("bookIdToRemove", new BookIdToRemove());
            model.addAttribute("uploadFile", new UploadFile());
            return "book_shelf";
        }

        bookService.removeBooksByPattern(Pattern.compile(regexToRemove.getRegex()));
        return "redirect:/books/shelf";
    }

    @PostMapping("/uploadFile")
    public String uploadFile(@NotNull @Valid @ModelAttribute UploadFile file, @NotNull BindingResult bindingResult, Model model) throws Exception {
        if (bindingResult.hasErrors()) {
            model.addAttribute("book", new Book());
            model.addAttribute("bookList", bookService.getAllBooks());
            model.addAttribute("bookIdToRemove", new BookIdToRemove());
            model.addAttribute("regexToRemove", new RegexToRemove());
            return "book_shelf";
        }

        File serverFile = fileUploadService.uploadFile(file);
        logger.info("new filed saved at: " + serverFile.getAbsolutePath());
        return "redirect:/books/shelf";
    }
}
