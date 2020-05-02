package moreredoc.application;

/**
 * POJO for representing an output configuration.
 * Specified, which types of files are to be generated
 */
public class MoreRedocOutputConfiguration {
    private boolean xmiRaw;
    private boolean xmiArgo;
    private boolean xmiStar;

    private boolean outputPng;
    private boolean outputSvg;

    public MoreRedocOutputConfiguration(boolean xmiRaw, boolean xmiArgo, boolean xmiStar, boolean outputPng, boolean outputSvg) {
        this.xmiRaw = xmiRaw;
        this.xmiArgo = xmiArgo;
        this.xmiStar = xmiStar;
        this.outputPng = outputPng;
        this.outputSvg = outputSvg;
    }

    public boolean isXmiRaw() {
        return xmiRaw;
    }

    public void setXmiRaw(boolean xmiRaw) {
        this.xmiRaw = xmiRaw;
    }

    public boolean isXmiArgo() {
        return xmiArgo;
    }

    public void setXmiArgo(boolean xmiArgo) {
        this.xmiArgo = xmiArgo;
    }

    public boolean isXmiStar() {
        return xmiStar;
    }

    public void setXmiStar(boolean xmiStar) {
        this.xmiStar = xmiStar;
    }

    public boolean isOutputPng() {
        return outputPng;
    }

    public void setOutputPng(boolean outputPng) {
        this.outputPng = outputPng;
    }

    public boolean isOutputSvg() {
        return outputSvg;
    }

    public void setOutputSvg(boolean outputSvg) {
        this.outputSvg = outputSvg;
    }
}
