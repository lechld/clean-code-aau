package aau.edu.dolechl.cleancode.translator;

import aau.edu.dolechl.cleancode.domain.Document;
import aau.edu.dolechl.cleancode.domain.DocumentElement;
import aau.edu.dolechl.cleancode.domain.DocumentHeader;
import aau.edu.dolechl.cleancode.domain.DocumentLink;

import java.util.*;

public class DocumentTranslatorImpl implements DocumentTranslator {

    private final DeepLTranslationWrapper deepLTranslator;

    public DocumentTranslatorImpl(DeepLTranslationWrapper deepLTranslator) {
        this.deepLTranslator = deepLTranslator;
    }

    @Override
    public Document translate(Document input, String targetLanguage) {
        Document translatedDoc = new Document();
        List<DocumentElement> elements = input.getElements();

        for (DocumentElement element : elements) {
            if (element instanceof DocumentHeader header) {
                String translated = deepLTranslator.translate(header.value(), targetLanguage);
                DocumentHeader translatedHeader = new DocumentHeader(header.depth(), header.level(), translated);

                translatedDoc.addElement(translatedHeader);
            } else if (element instanceof DocumentLink link) {
                translatedDoc.addElement(link);
            }
        }

        return translatedDoc;
    }
}
