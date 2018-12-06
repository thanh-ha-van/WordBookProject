package ha.thanh.oxfordmodule;

public enum TranslationPair {
    EN_ES(SourceLanguage.EN, TargetLanguage.ES),
    ES_EN(SourceLanguage.ES, TargetLanguage.EN),
    EN_ZU(SourceLanguage.EN, TargetLanguage.ZU),
    ZU_EN(SourceLanguage.ZU, TargetLanguage.EN),
    EN_NSO(SourceLanguage.EN, TargetLanguage.NSO),
    NSO_EN(SourceLanguage.NSO, TargetLanguage.EN),
    EN_RO(SourceLanguage.EN, TargetLanguage.RO),
    UR_EN(SourceLanguage.UR, TargetLanguage.EN),
    EN_MS(SourceLanguage.EN, TargetLanguage.MS),
    MS_EN(SourceLanguage.MS, TargetLanguage.EN),
    EN_TN(SourceLanguage.EN, TargetLanguage.TN),
    TN_EN(SourceLanguage.TN, TargetLanguage.EN),
    EN_ID(SourceLanguage.EN, TargetLanguage.ID),
    ID_EN(SourceLanguage.ID, TargetLanguage.EN),
    EN_DE(SourceLanguage.EN, TargetLanguage.DE),
    DE_EN(SourceLanguage.DE, TargetLanguage.EN),
    EN_PT(SourceLanguage.EN, TargetLanguage.PT),
    PT_EN(SourceLanguage.PT, TargetLanguage.EN);

    private SourceLanguage source;
    private TargetLanguage target;

    TranslationPair(SourceLanguage source, TargetLanguage target) {
        this.source = source;
        this.target = target;
    }

    public String getSourceIANAcode() {
        return source.getIANAcode();
    }

    public String getSourceName() {
        return source.getName();
    }

    public String getTargetIANAcode() {
        return target.getIANAcode();
    }

    public String getTargetName() {
        return target.getName();
    }

    private enum SourceLanguage {
        EN("en", "English"),
        ES("es", "Spanish"),
        ZU("zu", "isiZulu"),
        NSO("nso", "Northern Sotho"),
        UR("ur", "Urdu"),
        MS("ms", "Malay"),
        TN("tn", "Setswana"),
        ID("id", "Indonesian"),
        DE("de", "German"),
        PT("pt", "Portuguese");

        private String name;
        private String IANAcode;

        SourceLanguage(String IANAcode, String name) {
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

    private enum TargetLanguage {
        EN("en", "English"),
        ES("es", "Spanish"),
        ZU("zu", "isiZulu"),
        NSO("nso", "Northern Sotho"),
        RO("ro", "Romanian"),
        MS("ms", "Malay"),
        TN("tn", "Setswana"),
        ID("id", "Indonesian"),
        DE("de", "German"),
        PT("pt", "Portuguese");

        private String name;
        private String IANAcode;

        TargetLanguage(String IANAcode, String name) {
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

}