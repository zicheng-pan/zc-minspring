package com.minispring.beans.property;

import java.util.*;

public class ArgumentValues {
    private final Map<Integer, ArgumentValue> indexedAregumentValues = new HashMap<>();
    private final List<ArgumentValue> genericArgumentValues = new LinkedList<>();

    private void addArgumentValue(Integer key, ArgumentValue argumentValue) {
        this.indexedAregumentValues.put(key, argumentValue);
    }

    public boolean hasIndexedArgumentValue(int index) {
        return this.indexedAregumentValues.containsKey(index);
    }

    public ArgumentValue getIndexedArgument(int index) {
        return this.indexedAregumentValues.get(index);
    }

    public void addGenericArgumentValue(Object value, String type) {
        this.genericArgumentValues.add(new ArgumentValue(value, type));
    }

    public void addGenericArgumentValue(ArgumentValue argumentValue) {
        if (argumentValue.getName() != null) {

            /*
            this.genericArgumentValues.stream().forEach(
                    it -> {
                        if(argumentValue.getName().equals(it.getName())){
                            genericArgumentValues.remove(it);
                        }
                    }
            );
            */

            for (Iterator<ArgumentValue> it = this.genericArgumentValues.iterator(); it.hasNext(); ) {
                ArgumentValue oldArgumentVaule = it.next();
                if (argumentValue.getName().equals(oldArgumentVaule.getName())) {
                    it.remove();
                }
            }
        }
        this.genericArgumentValues.add(argumentValue);
    }

    public ArgumentValue getGenericArgumentValue(String requiredName) {
        for (ArgumentValue valueHolder : this.genericArgumentValues) {
            /**
             *  valueHolder.getName()!=null && requiredName!= null && valueHolder.getName().equals(requiredName)
             */
            if (valueHolder.getName() != null && (requiredName == null || !valueHolder.getName().equals(requiredName))) {
                continue;
            }
            return valueHolder;
        }
        return null;
    }

    public int getArgumentCount() {
        return this.genericArgumentValues.size();
    }

    public boolean isEmpty() {
        return this.genericArgumentValues.isEmpty();
    }
}
