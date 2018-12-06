package ha.thanh.oxfordmodule.requests;


import org.json.JSONObject;

import ha.thanh.oxfordmodule.OxfordDictionary;
import ha.thanh.oxfordmodule.impl.OxfordDictionaryImpl;


public class LemmatronRequestBuilder extends RequestBuilder {

    public LemmatronRequestBuilder(String word, OxfordDictionaryImpl dict) {
        super(word, dict);
    }

    public LemmatronRequestBuilder addGrammaticalFeatureFilters(String... filters) {
        addFiltersToMap("grammaticalFeatures", filters);
        return this;
    }

    public LemmatronRequestBuilder addLexicalCategoryFilters(String... filters) {
        addFiltersToMap("lexicalCategory", filters);
        return this;
    }

    public JSONObject build() {
        return dict.request(OxfordDictionary.BASE_URL + "/" + OxfordDictionary.Endpoint.INFLECTIONS.getEndpoint() + "/" + dict.getLang()
                                                                                                                              .getIANAcode() + "/" + word +
                                    (filterMapToString().equals("") ? "" : "/" + filterMapToString()));
    }
}
