package ha.thanh.oxfordmodule.requests;


import org.json.JSONObject;

import ha.thanh.oxfordmodule.OxfordDictionary;
import ha.thanh.oxfordmodule.TranslationPair;
import ha.thanh.oxfordmodule.impl.OxfordDictionaryImpl;

public class SearchRequestBuilder extends RequestBuilder {

    private boolean prefix;
    private int limit = 5000;
    private int offset = 0;

    public SearchRequestBuilder(String word, OxfordDictionaryImpl dict) {
        super(word, dict);
    }

    public SearchRequestBuilder setTranslationPair(TranslationPair pair) {
        if (pair == null) throw new IllegalArgumentException("LanguagePair cannot be null");
        this.pair = pair;
        return this;
    }

    public SearchRequestBuilder setLimit(int limit) {
        if (limit <= 0 || limit > 5000) throw new IllegalArgumentException("The limit must be between `1` and `5000` inclusive. The default limit is 5000");
        this.limit = limit;
        return this;
    }

    public SearchRequestBuilder setOffset(int offset) {
        if (offset < 0 || offset > limit - 1)
            throw new IllegalArgumentException("The offset must be between `0` and `limit - 1` inclusive. The default limit is 5000");
        this.offset = offset;
        return this;
    }

    public SearchRequestBuilder setPrefix(boolean prefix) {
        this.prefix = prefix;
        return this;
    }

    public JSONObject build() {
        return dict.request(OxfordDictionary.BASE_URL + "/" + OxfordDictionary.Endpoint.SEARCH.getEndpoint() + "/" +
                                    ((pair == null) ? dict.getLang() : pair.getSourceIANAcode()) +
                                    ((pair == null) ? "" : "/translations=" + pair.getTargetIANAcode()) + "?q=" + word +
                                    (prefix ? "&prefix=true" : "&prefix=false") + "&limit=" + limit + "&offset=" + offset);
    }
}
