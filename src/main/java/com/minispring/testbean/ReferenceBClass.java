package com.minispring.testbean;

public class ReferenceBClass {

    private String id = "B";

    private ReferenceCClass referenceCClass;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ReferenceCClass getReferenceCClass() {
        return referenceCClass;
    }

    public void setReferenceCClass(ReferenceCClass referenceCClass) {
        this.referenceCClass = referenceCClass;
    }
}
