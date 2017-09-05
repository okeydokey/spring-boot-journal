package com.okeydokey.journal.controller;

import com.okeydokey.journal.domain.Journal;
import com.okeydokey.journal.repository.JournalRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JournalControllerTest {

    @InjectMocks
    private JournalController journalController;

    @Mock
    private JournalRepository journalRepository;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(journalController).build();
    }

    @Test
    public void index() throws Exception {
        this.mockMvc.perform(get("/"))
                .andDo(print())
                .andExpect(view().name("index"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("journal"));
    }
}
