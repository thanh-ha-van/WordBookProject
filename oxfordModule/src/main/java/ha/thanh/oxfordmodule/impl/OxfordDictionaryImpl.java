package ha.thanh.oxfordmodule.impl;


import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import ha.thanh.oxfordmodule.OxfordDictionary;
import ha.thanh.oxfordmodule.exceptions.AuthenticationException;
import ha.thanh.oxfordmodule.exceptions.BadRequestException;
import ha.thanh.oxfordmodule.exceptions.ServiceUnavailableException;
import ha.thanh.oxfordmodule.exceptions.UnsupportedLanguageException;
import ha.thanh.oxfordmodule.exceptions.WordNotFoundException;
import ha.thanh.oxfordmodule.requests.EntriesRequestBuilder;
import ha.thanh.oxfordmodule.requests.LemmatronRequestBuilder;
import ha.thanh.oxfordmodule.requests.SearchRequestBuilder;
import ha.thanh.oxfordmodule.requests.TranslationRequestBuilder;
import ha.thanh.oxfordmodule.requests.WordlistRequestBuilder;

public class OxfordDictionaryImpl implements OxfordDictionary {

    private Language lang = Language.EN;
    private String app_key;
    private String app_id;

    public OxfordDictionaryImpl(String app_key, String app_id) {
        this.app_key = app_key;
        this.app_id = app_id;
    }

    public Language getLang() {
        return lang;
    }

    @Override
    public void setLanguage(Language lang) {
        if (lang == null) throw new IllegalArgumentException("The language cannot be null");
        this.lang = lang;
    }

    @Override
    public LemmatronRequestBuilder lemmatron(String word) {
        if (word == null) throw new IllegalArgumentException("word cannot be null");
        if (word.equals("")) throw new IllegalArgumentException("word cannot be an empty string");
        return new LemmatronRequestBuilder(word, this);
    }

    @Override
    public EntriesRequestBuilder entries(String word) {
        if (word == null) throw new IllegalArgumentException("word cannot be null");
        if (word.equals("")) throw new IllegalArgumentException("word cannot be an empty string");
        return new EntriesRequestBuilder(word, this);
    }

    @Override
    public JSONObject thesaurus(String word, boolean antonyms, boolean synonyms) {
        if (word == null) throw new IllegalArgumentException("word cannot be null");
        if (word.equals("")) throw new IllegalArgumentException("word cannot be an empty string");
        if (!synonyms && !antonyms) throw new IllegalArgumentException("antonyms and synonyms cannot both be false");
        String filters;
        if (antonyms && synonyms) filters = "synonyms;antonyms";
        else if (antonyms) filters = "antonyms";
        else filters = "synonyms";
        return request(BASE_URL + "/" + Endpoint.ENTRIES.getEndpoint() + "/" + lang.getIANAcode() + "/" + word + "/" + filters);
    }

    @Override
    public SearchRequestBuilder search(String word) {
        if (word == null) throw new IllegalArgumentException("word cannot be null");
        if (word.equals("")) throw new IllegalArgumentException("word cannot be an empty string");
        return new SearchRequestBuilder(word, this);
    }

    @Override
    public TranslationRequestBuilder translate(String word) {
        if (word == null) throw new IllegalArgumentException("word cannot be null");
        if (word.equals("")) throw new IllegalArgumentException("word cannot be an empty string");
        return new TranslationRequestBuilder(word, this);
    }

    @Override
    public WordlistRequestBuilder wordlist() {
        return new WordlistRequestBuilder(this);
    }

    @Override
    public JSONObject sentences(String word) {
        if (word == null) throw new IllegalArgumentException("word cannot be null");
        if (word.equals("")) throw new IllegalArgumentException("word cannot be an empty string");
        if (lang != Language.EN) throw new UnsupportedLanguageException("Sentences are only available with English");
        return request(BASE_URL + "/" + Endpoint.ENTRIES.getEndpoint() + "/" + lang.getIANAcode() + "/" + word + "/sentences");
    }

    public JSONObject request(String urlStr) {
        StringBuilder jsonBuilder = new StringBuilder();
        try {
            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("app_id", app_id);
            conn.setRequestProperty("app_key", app_key);

            switch (conn.getResponseCode()) {
                case 200:
                    break; // no errors
                case 403:
                    throw new AuthenticationException("The request failed due to invalid credentials.");
                case 404:
                    throw new WordNotFoundException("No information found, or the requested URL was not found on the server.");
                case 400:
                    throw new BadRequestException("The request was invalid or cannot be otherwise served.");
                case 500:
                case 501:
                case 502:
                case 503:
                case 504:
                    throw new ServiceUnavailableException("The Oxford Dictionary service is currently unreachable.");
                default:
                    // error codes that were't checked for
                    System.err.println(conn.getResponseCode());
                    break;
            }

            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

            String output;
            while ((output = br.readLine()) != null) jsonBuilder.append(output)
                                                                .append("\n");

            conn.disconnect();
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return new JSONObject(jsonBuilder.toString());
    }

}
