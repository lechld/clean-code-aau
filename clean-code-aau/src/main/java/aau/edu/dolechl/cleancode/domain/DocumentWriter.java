package aau.edu.dolechl.cleancode.domain;

import java.net.URL;

public interface DocumentWriter {

    void write(URL url, int depth, String targetLanguage, Document document);
}
