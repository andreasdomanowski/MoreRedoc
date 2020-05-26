package moreredoc.integration.tools;

import moreredoc.datainput.InputDataHandler;
import moreredoc.datainput.SoftRedocDataHandler;

public enum SupportedRedocumentationTools {
    SOFTREDOC("softredoc", "SoftRedoc", new SoftRedocDataHandler());

    private final String key;
    private final String displayName;

    private final InputDataHandler dataHandler;

    SupportedRedocumentationTools(String key, String displayName, InputDataHandler dataHandler){
        this.key = key;
        this.displayName = displayName;
        this.dataHandler = dataHandler;
    }

    @Override
    public String toString(){
        return this.displayName;
    }

    public InputDataHandler getDataHandler() {
        return dataHandler;
    }
}