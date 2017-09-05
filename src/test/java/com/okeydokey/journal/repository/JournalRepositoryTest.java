package com.okeydokey.journal.repository;

import com.okeydokey.journal.domain.Journal;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JournalRepositoryTest {

    @Autowired
    JournalRepository journalRepository;

    @Test
    public void save() throws ParseException {
        Journal journal1 = new Journal("스프링 부터 입문", "오늘부터 스프링 부트 공부하기 시작했다", "01/01/2016");
        Journal journal2 = new Journal("간단한 스프링 부트 프로젝트", "스프링 부트 프로젝트를 처음 만들어보았다", "01/02/2016");
        Journal journal3 = new Journal("스프링 부터 해부", "스프링 부트를 자세히 살펴보았다", "02/01/2016");
        Journal journal4 = new Journal("스프링 부터 클라우드", "클라우드 파운드리를 응용한 스프링 부트를 공부했다", "03/01/2016");

        journalRepository.save(journal1);
        journalRepository.save(journal2);
        journalRepository.save(journal3);
        journalRepository.save(journal4);

        assertEquals(journal1, journalRepository.findOne(journal1.getId()));
        assertEquals(journal2, journalRepository.findOne(journal2.getId()));
        assertEquals(journal3, journalRepository.findOne(journal3.getId()));
        assertEquals(journal4, journalRepository.findOne(journal4.getId()));
    }
}
