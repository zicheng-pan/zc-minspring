package com.minispring.testbean;

public class ReferenceAClass {

    private String id = "A";

    private ReferenceBClass referenceBClass;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ReferenceBClass getReferenceBClass() {
        return referenceBClass;
    }

    public void setReferenceBClass(ReferenceBClass referenceBClass) {
        this.referenceBClass = referenceBClass;
    }
}
