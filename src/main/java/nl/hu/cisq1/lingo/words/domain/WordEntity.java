package nl.hu.cisq1.lingo.words.domain;

import javax.persistence.Entity;
import javax.persistence.Column;
import javax.persistence.Id;

@Entity(name = "words")
public class WordEntity {
    @Id
    @Column(name = "word")
    private String value;
    private Integer length;

    public WordEntity() {
    }

    public WordEntity(String word) {
        this.value = word;
        this.length = word.length();
    }

    public String getValue() {
        return value;
    }

    public Integer getLength() {
        return length;
    }
}