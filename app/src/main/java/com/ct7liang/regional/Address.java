package com.ct7liang.regional;

public class Address {

    private String code;
    private String name;
    private String aId;
    private boolean isSelect;

    public Address() {
        
    }

    public Address(String code, String name, String aId) {
        this.code = code;
        this.name = name;
        this.aId = aId;
    }

    public boolean getIsSelect(){
        return isSelect;
    }

    public void setIsSelect(boolean isSelect){
        this.isSelect = isSelect;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getaId() {
        return aId;
    }

    public void setaId(String aId) {
        this.aId = aId;
    }

    @Override
    public String toString() {
        return "\r\n" + aId + " - " + name + " - " + code;
    }
    
}