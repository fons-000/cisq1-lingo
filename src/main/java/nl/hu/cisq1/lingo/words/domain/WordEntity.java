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

    public void setValue(String value) {
        this.value = value;
    }

    public Integer getLength() {
        return length;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WordEntity word = (WordEntity) o;
        return value.equals(word.value) && length.equals(word.length);
    }
}