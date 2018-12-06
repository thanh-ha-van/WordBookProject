package ha.thanh.oxfordmodule.requests;


import org.json.JSONObject;

import ha.thanh.oxfordmodule.OxfordDictionary;
import ha.thanh.oxfordmodule.impl.OxfordDictionaryImpl;

public class EntriesRequestBuilder extends RequestBuilder {

    public EntriesRequestBuilder(String word, OxfordDictionaryImpl dict) {
        super(word, dict);
    }

    public EntriesRequestBuilder addGrammaticalFeatureFilters(String... filters) {
        addFiltersToMap("grammaticalFeatures", filters);
        return this;
    }

    public EntriesRequestBuilder addLexicalCategoryFilters(String... filters) {
        addFiltersToMap("lexicalCategory", filters);
        return this;
    }

    public EntriesRequestBuilder addVariantFormsFilters(String... filters) {
        addFiltersToMap("variantForms", filters);
        return this;
    }

    public EntriesRequestBuilder addRegistersFilters(String... filters) {
        addFiltersToMap("registers", filters);
        return this;
    }

    public EntriesRequestBuilder addDefinitionsFilters(String... filters) {
        addFiltersToMap("definitions", filters);
        return this;
    }

    public EntriesRequestBuilder addDomainsFilters(String... filters) {
        addFiltersToMap("domains", filters);
        return this;
    }

    public EntriesRequestBuilder addEtymologiesFilters(String... filters) {
        addFiltersToMap("etymologies", filters);
        return this;
    }

    public EntriesRequestBuilder addExamplesFilters(String... filters) {
        addFiltersToMap("examples", filters);
        return this;
    }

    public EntriesRequestBuilder addPronunciationsFilters(String... filters) {
        addFiltersToMap("pronunciations", filters);
        return this;
    }

    public EntriesRequestBuilder addRegionsFilters(String... filters) {
        addFiltersToMap("regions", filters);
        return this;
    }

    public JSONObject build() {
        return dict.request(OxfordDictionary.BASE_URL + "/" + OxfordDictionary.Endpoint.ENTRIES.getEndpoint() + "/" + dict.getLang()
                                                                                                                          .getIANAcode() + "/" + word +
                                    (filterMapToString().equals("") ? "" : "/" + filterMapToString()));
    }
}
