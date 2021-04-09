package nl.hu.cisq1.lingo.trainer;

import nl.hu.cisq1.lingo.trainer.data.SpringGameRepository;
import nl.hu.cisq1.lingo.trainer.data.SpringGameRepositoryTest;
import nl.hu.cisq1.lingo.trainer.domain.*;
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
        System.out.println("This is game.getRounds.size in the run of the GameTestDataFixtures: ");
        System.out.println(game.getRounds().size());
        Round round = new Round(Word.createValidWord("AAGJE"), game.getRounds().size() + 1);
    }
}