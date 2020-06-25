package moreredoc.datainput.tools;

import moreredoc.datainput.DemoDataHandler;
import moreredoc.datainput.InputDataHandler;
import moreredoc.datainput.SoftRedocDataHandler;

public enum SupportedRedocumentationTools {
    SOFTREDOC("softredoc", "SoftRedoc", new SoftRedocDataHandler()),
    DEMO("demo", "DemoTool - paths are not used, specify random files/folders", new DemoDataHandler());

    public final String key;
    public final String displayName;

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
