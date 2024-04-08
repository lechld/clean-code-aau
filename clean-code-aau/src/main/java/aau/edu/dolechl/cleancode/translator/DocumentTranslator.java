package aau.edu.dolechl.cleancode.translator;

import aau.edu.dolechl.cleancode.domain.Document;

public interface DocumentTranslator {

    Document translate(Document input, String targetLanguage);
}
