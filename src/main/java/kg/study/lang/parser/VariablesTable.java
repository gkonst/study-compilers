package kg.study.lang.parser;

import java.util.HashMap;
import java.util.Map;

public class VariablesTable {

    private Map<String, Variable> variables = new HashMap<>();

    public boolean hasVariable(String name) {
        return variables.containsKey(name);
    }

    public void addVariable(String name, Class type) {
        variables.put(name, new Variable(type));
    }

    public VariablesTable withVariable(String name, Class type) {
        variables.put(name, new Variable(type));
        return this;
    }

    public Variable getVariable(String name) {
        return variables.get(name);
    }

    public static class Variable {
        private Class type;

        Variable(Class type) {
            this.type = type;
        }

        public Class getType() {
            return type;
        }
    }
}
