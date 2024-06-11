package aau.edu.dolechl.cleancode.domain;

import java.io.Closeable;
import java.net.URL;

public interface DocumentWriter extends Closeable {

    void write(URL url, int depth, String targetLanguage, Document document);
}
