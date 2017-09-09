package com.okeydokey.journal.controller;

import com.okeydokey.journal.domain.Journal;
import com.okeydokey.journal.repository.JournalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class JournalController {
    @Autowired
    JournalRepository journalRepository;

    @GetMapping(value="/journal", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public @ResponseBody
    List<Journal> getJournal() {
        return journalRepository.findAll();
    }

    @GetMapping(value="/journal/findBy/title/{title}", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public @ResponseBody
    List<Journal> findByTitle(@PathVariable String title) {
        return journalRepository.findByTitle(title);
    }

    @PostMapping(value="/journal")
    public @ResponseBody
    Journal add(@RequestBody Journal journal) {
        return journalRepository.save(journal);
    }

    @RequestMapping("/")
    public String index(Model model) {
        model.addAttribute("journal", journalRepository.findAll());
        return "index";
    }

}
