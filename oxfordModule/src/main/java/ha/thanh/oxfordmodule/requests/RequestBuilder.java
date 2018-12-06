package ha.thanh.oxfordmodule.requests;


import java.util.*;
import java.util.stream.Collectors;

import ha.thanh.oxfordmodule.TranslationPair;
import ha.thanh.oxfordmodule.impl.OxfordDictionaryImpl;

abstract class RequestBuilder {
    HashMap<String, LinkedList<String>> filterMap = new HashMap<String, LinkedList<String>>();
    OxfordDictionaryImpl dict;
    TranslationPair pair = TranslationPair.EN_ES;
    String word;

    RequestBuilder(String word, OxfordDictionaryImpl dict) {
        this.word = word;
        this.dict = dict;
    }

    void addFiltersToMap(String filterName, String... filters) {
        if (filters.length == 0) throw new IllegalArgumentException("You must enter at least one filter.");
        if (!filterMap.containsKey(filterName)) filterMap.put(filterName, new LinkedList<String>(Arrays.asList(filters)));
        else Collections.addAll(filterMap.get(filterName), filters);
    }

    String filterMapToString() {
        ArrayList<String> filterStrings = new ArrayList<String>();
        for (String filter : filterMap.keySet()) {
            String filterCategory = filter + "=" + filterMap.get(filter)
                                                            .stream()
                                                            .collect(Collectors.joining(","));
            filterStrings.add(filterCategory);
        }
        return filterStrings.stream()
                            .collect(Collectors.joining(";"));
    }

}