package nl.hu.cisq1.lingo.words;

import nl.hu.cisq1.lingo.words.data.SpringWordRepository;
import nl.hu.cisq1.lingo.trainer.domain.Word;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

public class WordTestDataFixtures implements CommandLineRunner {
    private final SpringWordRepository repository;

    public WordTestDataFixtures(SpringWordRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Komt in de run van WordTestDataFixtures");
        this.repository.save(new Word("pizza"));
        this.repository.save(new Word("oranje"));
        this.repository.save(new Word("wanorde"));
    }
}