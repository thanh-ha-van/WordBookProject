package ha.thanh.oxfordmodule.requests;


import org.json.JSONObject;

import ha.thanh.oxfordmodule.OxfordDictionary;
import ha.thanh.oxfordmodule.exceptions.BadRequestException;
import ha.thanh.oxfordmodule.impl.OxfordDictionaryImpl;

public class WordlistRequestBuilder extends RequestBuilder {
    private String prefix;
    private int limit = 5000;
    private int offset = 0;
    private boolean exact;
    private LengthFilter lengthFilter = LengthFilter.setNoLengthFilter();

    public WordlistRequestBuilder(OxfordDictionaryImpl dict) {
        super(null, dict);
    }

    public WordlistRequestBuilder setLimit(int limit) {
        if (limit <= 0 || limit > 5000) throw new IllegalArgumentException("The limit must be between `1` and `5000` inclusive. The default limit is 5000");
        this.limit = limit;
        return this;
    }

    public WordlistRequestBuilder setOffset(int offset) {
        if (offset < 0 || offset > limit - 1)
            throw new IllegalArgumentException("The offset must be between `0` and `limit - 1` inclusive. The default limit is 5000");
        this.offset = offset;
        return this;
    }

    public WordlistRequestBuilder setExact(boolean exact) {
        this.exact = exact;
        return this;
    }

    public WordlistRequestBuilder setPrefix(String prefix) {
        if (prefix == null) throw new IllegalArgumentException("prefix cannot be null");
        if (prefix.equals("")) throw new IllegalArgumentException("prefix cannot be an empty string");
        this.prefix = prefix;
        return this;
    }

    public WordlistRequestBuilder addLexicalCategoryFilters(String... filters) {
        addFiltersToMap("lexicalCategory", filters);
        return this;
    }

    public WordlistRequestBuilder addRegistersFilters(String... filters) {
        addFiltersToMap("registers", filters);
        return this;
    }

    public WordlistRequestBuilder addDomainsFilters(String... filters) {
        addFiltersToMap("domains", filters);
        return this;
    }

    public WordlistRequestBuilder addTranslationsFilters(String... filters) {
        addFiltersToMap("translations", filters);
        return this;
    }

    public WordlistRequestBuilder addRegionsFilters(String... filters) {
        addFiltersToMap("regions", filters);
        return this;
    }

    public WordlistRequestBuilder addWordlengthFilter(LengthFilter lengthFilter) {
        this.lengthFilter = lengthFilter;
        return this;
    }

    public JSONObject build() {
        if (prefix.equals("")) throw new BadRequestException();
        return dict.request(OxfordDictionary.BASE_URL + "/" + OxfordDictionary.Endpoint.WORDLIST.getEndpoint() + "/" + dict.getLang()
                                                                                                                           .getIANAcode() +
                                    (filterMap.isEmpty() ? "?" : "/" + filterMapToString() + "?") + "prefix=" + prefix + "&exact=" + String.valueOf(exact) +
                                    (lengthFilter.toString()
                                                 .equals("") ? "" : "&" + lengthFilter) + "&limit=" + limit + "&offset=" + offset);
    }
}
