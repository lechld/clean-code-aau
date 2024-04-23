package aau.edu.dolechl.cleancode.translator;

import com.deepl.api.TextResult;
import com.deepl.api.Translator;

public class DeepLTranslationWrapper {

    private static final String TRANSLATION_FAILED_SUFFIX = " (translation failed)";

    private static final String DEEP_L_AUTH_KEY_ENV_VAR = "DEEPL_AUTH";

    private final Translator translator;

    public DeepLTranslationWrapper() {
        String authKey = System.getenv(DEEP_L_AUTH_KEY_ENV_VAR);

        if (authKey != null && !authKey.isBlank()) {
            translator = new Translator(authKey);
        } else {
            System.out.println("No DeepL auth key specified in environment variables.");
            translator = null;
        }
    }

    public String translate(String text, String targetLanguage) {
        if (translator == null) {
            return text;
        }

        try {
            TextResult result = translator.translateText(text, null, targetLanguage);

            return result.getText();
        } catch (Exception e) {
            return text + TRANSLATION_FAILED_SUFFIX;
        }
    }
}
