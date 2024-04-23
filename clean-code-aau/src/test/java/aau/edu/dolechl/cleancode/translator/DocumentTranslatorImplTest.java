package aau.edu.dolechl.cleancode.translator;

import aau.edu.dolechl.cleancode.domain.Document;
import aau.edu.dolechl.cleancode.domain.DocumentElement;
import aau.edu.dolechl.cleancode.domain.DocumentHeader;
import aau.edu.dolechl.cleancode.domain.DocumentLink;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class DocumentTranslatorImplTest {

    private DocumentTranslatorImpl translator;

    private DeepLTranslationWrapper deepLTranslator;

    @BeforeEach
    void setUp() {
        this.deepLTranslator = Mockito.mock(DeepLTranslationWrapper.class);
        this.translator = new DocumentTranslatorImpl(deepLTranslator);
    }

    @Test
    void translate_TranslatesDocumentHeader() {
        Document inputDoc = new Document();
        DocumentHeader header = new DocumentHeader(1, 1, "Header");
        inputDoc.addElement(header);

        String targetLanguage = "fr";
        String translatedHeader = "Titre";
        when(deepLTranslator.translate("Header", targetLanguage)).thenReturn(translatedHeader);

        Document translatedDoc = translator.translate(inputDoc, targetLanguage);

        assertEquals(1, translatedDoc.getElements().size());
        DocumentElement translatedElement = translatedDoc.getElements().get(0);
        assertEquals(DocumentHeader.class, translatedElement.getClass());
        assertEquals(translatedHeader, ((DocumentHeader) translatedElement).value());
    }

    @Test
    void translate_KeepsDocumentLinkUntranslated() {
        Document inputDoc = new Document();
        DocumentLink link = new DocumentLink(0, "http://example.com", true);
        inputDoc.addElement(link);

        String targetLanguage = "fr";

        Document translatedDoc = translator.translate(inputDoc, targetLanguage);

        assertEquals(1, translatedDoc.getElements().size());
        DocumentElement translatedElement = translatedDoc.getElements().get(0);
        assertEquals(DocumentLink.class, translatedElement.getClass());
        assertEquals(link, translatedElement);
    }

    @Test
    void translate_TranslatesOnlyDocumentHeaders() {
        // Arrange
        Document inputDoc = new Document();
        DocumentHeader header = new DocumentHeader(0, 1, "Header");
        DocumentLink link = new DocumentLink(0, "http://example.com", true);
        inputDoc.addElement(header);
        inputDoc.addElement(link);

        String targetLanguage = "fr";
        String translatedHeader = "Titre";
        when(deepLTranslator.translate("Header", targetLanguage)).thenReturn(translatedHeader);

        Document translatedDoc = translator.translate(inputDoc, targetLanguage);

        assertEquals(2, translatedDoc.getElements().size());
        assertEquals(translatedHeader, ((DocumentHeader) translatedDoc.getElements().get(0)).value());
        assertEquals(link, translatedDoc.getElements().get(1));
    }
}
