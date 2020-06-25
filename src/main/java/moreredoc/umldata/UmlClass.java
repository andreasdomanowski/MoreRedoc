package moreredoc.umldata;

import edu.stanford.nlp.util.StringUtils;

import java.util.Set;
import java.util.TreeSet;

public class UmlClass {
    private String name;
    private Set<String> attributes = new TreeSet<>();
    private Set<String> methods = new TreeSet<>();

    public UmlClass(String name, Set<String> attributes, Set<String> methods) {
        super();
        this.name = name;
        this.attributes = attributes;
        this.methods = methods;
    }

    public UmlClass(String name) {
        this.name = StringUtils.capitalize(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<String> getAttributes() {
        return attributes;
    }

    public void addAttribute(String attribute) {
        this.attributes.add(attribute);
    }

    public Set<String> getMethods() {
        return methods;
    }

    public void addMethod(String method) {
        methods.add(method);
    }

    public String toPlantUmlClassString() {
        StringBuilder resultBuilder = new StringBuilder("class " + this.name + " { \n");

        for (String attribute : attributes) {
            resultBuilder.append("\t").append(attribute).append("\n");
        }

        for (String method : methods) {
            if (method.contains("(") && method.contains(")")) {
                resultBuilder.append("\t").append(method).append("\n");
            } else {
                resultBuilder.append("\t").append(method).append("()\n");
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
