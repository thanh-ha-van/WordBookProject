package ha.thanh.oxfordmodule.requests;


import org.json.JSONObject;

import ha.thanh.oxfordmodule.OxfordDictionary;
import ha.thanh.oxfordmodule.TranslationPair;
import ha.thanh.oxfordmodule.impl.OxfordDictionaryImpl;

public class TranslationRequestBuilder extends RequestBuilder {

    public TranslationRequestBuilder(String word, OxfordDictionaryImpl dict) {
        super(word, dict);
    }

    public TranslationRequestBuilder setTranslationPair(TranslationPair pair) {
        if (pair == null) throw new IllegalArgumentException("LanguagePair cannot be null");
        this.pair = pair;
        return this;
    }

    public JSONObject build() {
        return dict.request(OxfordDictionary.BASE_URL + "/" + OxfordDictionary.Endpoint.ENTRIES.getEndpoint() + "/" + pair.getSourceIANAcode() + "/" + word +
                                    "/translations=" + pair.getTargetIANAcode());
    }

}