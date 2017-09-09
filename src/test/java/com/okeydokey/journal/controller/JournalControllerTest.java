package com.okeydokey.journal.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.okeydokey.journal.domain.Journal;
import com.okeydokey.journal.repository.JournalRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.nio.charset.Charset;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.iterableWithSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class JournalControllerTest {

    @InjectMocks
    private JournalController journalController;

    @Mock
    private JournalRepository journalRepository;

    private MockMvc mockMvc;

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("UTF-8"));

    private String TITLE_MATCH = "스프링 부트 클라우드";

    @Before
    public void setup() throws ParseException {
        this.mockMvc = MockMvcBuilders.standaloneSetup(journalController)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .alwaysDo(print())
                .build();

        Mockito.doReturn(mockFindAll()).when(journalRepository).findAll();
        Mockito.doReturn(mockFindAll().stream().filter(journal -> journal.getTitle().equals(TITLE_MATCH)).collect(Collectors.toList())).when(journalRepository).findByTitle(TITLE_MATCH);
    }

    private List<Journal> mockFindAll() throws ParseException {
        Journal journal1 = new Journal("스프링 부트 입문", "오늘부터 스프링 부트 공부하기 시작했다", "01/01/2016");
        Journal journal2 = new Journal("간단한 스프링 부트 프로젝트", "스프링 부트 프로젝트를 처음 만들어보았다", "01/02/2016");
        Journal journal3 = new Journal("스프링 부트 해부", "스프링 부트를 자세히 살펴보았다", "02/01/2016");
        Journal journal4 = new Journal("스프링 부트 클라우드", "클라우드 파운드리를 응용한 스프링 부트를 공부했다", "03/01/2016");

        List<Journal> journals = new ArrayList<>();
        journals.add(journal1);
        journals.add(journal2);
        journals.add(journal3);
        journals.add(journal4);

        return journals;
    }

    @Test
    public void index() throws Exception {
        this.mockMvc.perform(get("/"))
                .andExpect(view().name("index"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("journal"));
    }

    @Test
    public void add() throws Exception {
        Journal journal = new Journal("스프링 부트 테스트", "스프링 부트 테스트를 학습했다.", "04/01/2016");

        mockMvc.perform(post("/journal")
                .content(new ObjectMapper().writeValueAsString(journal))
                .contentType(contentType)).andExpect(status().isOk());
    }

    @Test
    public void getJournal() throws Exception {
        mockMvc.perform(get("/journal"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$", iterableWithSize(4)))
                .andExpect(jsonPath("$[0]['title']", containsString("스프링 부트")));
    }

    @Test
    public void findByTitle() throws Exception {
        mockMvc.perform(get("/journal/findBy/title/" + TITLE_MATCH))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$", iterableWithSize(1)))
                .andExpect(jsonPath("$[0]['title']").value(TITLE_MATCH));
    }
}
