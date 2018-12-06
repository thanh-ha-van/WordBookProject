package ha.thanh.oxfordmodule;


import org.json.JSONObject;

import ha.thanh.oxfordmodule.requests.EntriesRequestBuilder;
import ha.thanh.oxfordmodule.requests.LemmatronRequestBuilder;
import ha.thanh.oxfordmodule.requests.SearchRequestBuilder;
import ha.thanh.oxfordmodule.requests.TranslationRequestBuilder;
import ha.thanh.oxfordmodule.requests.WordlistRequestBuilder;

public interface OxfordDictionary {

    String BASE_URL = "https://od-api.oxforddictionaries.com/api/v1";

    void setLanguage(Language lang);

    LemmatronRequestBuilder lemmatron(String word);

    EntriesRequestBuilder entries(String word);

    JSONObject thesaurus(String word, boolean antonyms, boolean synonyms);

    SearchRequestBuilder search(String word);

    TranslationRequestBuilder translate(String word);

    WordlistRequestBuilder wordlist();

    JSONObject sentences(String word);

    enum Language {
        EN("en", "English"),
        ES("es", "Spanish"),
        MS("ms", "Malay"),
        SW("sw", "Swahili"),
        TN("tn", "Setswana"),
        NSO("nso", "Northern Sotho"),
        LV("lv", "Latvian"),
        ID("id", "Indonesian"),
        UR("ur", "Urdu"),
        ZU("zu", "isiZulu"),
        RO("ro", "Romanian"),
        HI("hi", "Hindu");

        private String name;
        private String IANAcode;

        Language(String IANAcode, String name) {
            this.name = name;
            this.IANAcode = IANAcode;
        }

        public String getName() {
            return name;
        }

        public String getIANAcode() {
            return IANAcode;
        }
    }

    enum Endpoint {
        ENTRIES("entries"),
        INFLECTIONS("inflections"),
        SEARCH("search"),
        WORDLIST("wordlist"),
        STATS("stats"),
        LANGUAGES("languages"),
        FILTERS("filters"),
        LEXICALCATEGORIES("lexicalcategories"),
        REGISTERS("registers"),
        DOMAINS("domains"),
        REGIONS("regions"),
        GRAMMATICALFEATURES("grammaticalFeatures");

        private String endpoint;

        Endpoint(String endpoint) {
            this.endpoint = endpoint;
        }

        public String getEndpoint() {
            return endpoint;
        }
    }
}
