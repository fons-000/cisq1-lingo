package nl.hu.cisq1.lingo.words;

import nl.hu.cisq1.lingo.words.data.SpringWordRepository;
import nl.hu.cisq1.lingo.words.domain.WordEntity;
import org.springframework.boot.CommandLineRunner;

public class WordTestDataFixtures implements CommandLineRunner {
    private final SpringWordRepository repository;

    public WordTestDataFixtures(SpringWordRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... args) throws Exception {
        this.repository.save(new WordEntity("pizza"));
        this.repository.save(new WordEntity("oranje"));
        this.repository.save(new WordEntity("wanorde"));
    }
}
