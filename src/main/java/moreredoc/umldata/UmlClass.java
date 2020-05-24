package moreredoc.umldata;

import java.util.ArrayList;
import java.util.List;

public class UmlClass {
    private String name;
    private List<String> attributes = new ArrayList<>();
    private List<String> methods = new ArrayList<>();

    public UmlClass(String name, List<String> attributes, List<String> methods) {
        super();
        this.name = name;
        this.attributes = attributes;
        this.methods = methods;
    }

    public UmlClass(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getAttributes() {
        return attributes;
    }

    public void addAttribute(String attribute) {
        this.attributes.add(attribute);
    }

    public List<String> getMethods() {
        return methods;
    }

    public void addMethod(String method) {
        methods.add(method);
    }

    public String toPlantUmlClassString() {
        StringBuilder resultBuilder = new StringBuilder("class " + this.name + " { \n");

        for (String attribute : attributes) {
            resultBuilder.append("\t" + attribute + "\n");
        }

        for (String method : methods) {
            if (method.contains("(") && method.contains(")")) {
                resultBuilder.append("\t" + method + "\n");
            } else {
                resultBuilder.append("\t" + method + "()\n");
            }
        }

        resultBuilder.append("}");
        return resultBuilder.toString();
    }

    @Override
    public String toString() {
        return "[Class:" + this.name + ";Attributes:" + this.attributes + ";Methods:" + this.methods;
    }

}
