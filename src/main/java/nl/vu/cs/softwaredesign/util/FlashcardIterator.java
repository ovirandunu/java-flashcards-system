package nl.vu.cs.softwaredesign.util;

import nl.vu.cs.softwaredesign.Flashcard;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;

public class FlashcardIterator implements Iterator<Flashcard> {

    private Iterator<Map.Entry<Integer, Flashcard>> entryIterator;

    public FlashcardIterator(Map<Integer, Flashcard> flashcards) {
        this.entryIterator = flashcards.entrySet().iterator();
    }

    @Override
    public boolean hasNext() {
        return entryIterator.hasNext();
    }

    @Override
    public Flashcard next() {
        if (!hasNext()) {
            throw new NoSuchElementException("No more flashcards available.");
        }
        return entryIterator.next().getValue();
    }
}
