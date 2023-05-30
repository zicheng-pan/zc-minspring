package com.minispring.beans.property;

import java.util.*;

public class ConstructArgumentValues {
    private final List<ConstructArgumentValue> argumentValueList = new ArrayList<ConstructArgumentValue>();


    private void addArgumentValue(Integer key, ConstructArgumentValue constructArgumentValue) {
        this.argumentValueList.add(constructArgumentValue);
    }

    public void addGenericArgumentValue(Object value, String type) {
        this.argumentValueList.add(new ConstructArgumentValue(value, type));
    }

    public void addGenericArgumentValue(ConstructArgumentValue constructArgumentValue) {
        if (constructArgumentValue.getName() != null) {

            /*
            this.genericArgumentValues.stream().forEach(
                    it -> {
                        if(argumentValue.getName().equals(it.getName())){
                            genericArgumentValues.remove(it);
                        }
                    }
            );
            */
            for (Iterator<ConstructArgumentValue> it = this.argumentValueList.iterator(); it.hasNext(); ) {
                ConstructArgumentValue oldArgumentVaule = it.next();
                if (constructArgumentValue.getName().equals(oldArgumentVaule.getName())) {
                    it.remove();
                }
            }
        }
        this.argumentValueList.add(constructArgumentValue);
    }

    public ConstructArgumentValue getGenericArgumentValue(String requiredName) {
        for (ConstructArgumentValue valueHolder : this.argumentValueList) {
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
        return this.argumentValueList.size();
    }

    public boolean isEmpty() {
        return this.argumentValueList.isEmpty();
    }

    public List<ConstructArgumentValue> getArgumentValueList() {
        return argumentValueList;
    }
}
