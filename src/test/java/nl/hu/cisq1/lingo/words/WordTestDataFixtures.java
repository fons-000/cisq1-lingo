//package nl.hu.cisq1.lingo.words;
//
//import nl.hu.cisq1.lingo.words.data.SpringWordRepository;
//import nl.hu.cisq1.lingo.words.domain.Word;
//import org.springframework.boot.CommandLineRunner;
//
//public class WordTestDataFixtures implements CommandLineRunner {
//    //Servicetest will be fixed after implementation of Hibernate in the next
//    //sub-assignment
//    private final SpringWordRepository repository;
//
//    public WordTestDataFixtures(SpringWordRepository repository) {
//        this.repository = repository;
//    }
//
//    @Override
//    public void run(String... args) throws Exception {
//        this.repository.save(Word.createWord("pizza"));
//        this.repository.save(Word.createWord("oranje"));
//        this.repository.save(Word.createWord("wanorde"));
//    }
//}
