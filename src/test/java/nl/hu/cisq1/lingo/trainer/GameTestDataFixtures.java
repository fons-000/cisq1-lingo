package nl.hu.cisq1.lingo.trainer;

import nl.hu.cisq1.lingo.trainer.data.SpringGameRepository;
import nl.hu.cisq1.lingo.trainer.data.SpringGameRepositoryTest;
import nl.hu.cisq1.lingo.trainer.domain.Game;
import nl.hu.cisq1.lingo.trainer.domain.Person;
import nl.hu.cisq1.lingo.trainer.domain.Role;
import nl.hu.cisq1.lingo.trainer.domain.Word;
import nl.hu.cisq1.lingo.words.data.SpringWordRepository;
import org.springframework.boot.CommandLineRunner;

public class GameTestDataFixtures implements CommandLineRunner {
    private final SpringGameRepository repository;

    public GameTestDataFixtures(SpringGameRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... args) throws Exception {
        Person person = new Person("FS Fons", "1234", "Fons Thijssen", Role.PLAYER);
        Person person1 = new Person("fons-001", "5678", "Fons Thijssen", Role.ADMINISTRATOR);
        Game game = new Game();
        Game game1 = new Game();
        game1.setScore(200);
        Game game2 = new Game();
        game2.setScore();
        Game game3 = new Game();
        Game game4 = new Game();

    }
}