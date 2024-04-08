package aau.edu.dolechl.cleancode.domain;

import java.util.ArrayList;
import java.util.List;

public class Document {

    private final List<DocumentElement> elements = new ArrayList<>();

    public void addElement(DocumentElement element) {
        elements.add(element);
    }

    public List<DocumentElement> getElements() {
        return elements;
    }
}
