package com.minispring.testbean;

public class ReferenceCClass {

    private String id = "C";

    private ReferenceAClass referenceAClass;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ReferenceAClass getReferenceAClass() {
        return referenceAClass;
    }

    public void setReferenceAClass(ReferenceAClass referenceAClass) {
        this.referenceAClass = referenceAClass;
    }
}
