package com.diaco.majazi.Main.Main;

public class APIResult {
    int RESULT;
    String ID;
    String NUMBER;
    String AREACODE;
    String AMOUNT;
    String REPEAT;
    String TIME;


    public int getRESULT() {
        return RESULT;
    }

    public String getID() {
        return ID;
    }

    public String getNUMBER() {
        return NUMBER == null ? "" : NUMBER;
    }

    public String getAREACODE() {
        return AREACODE;
    }

    public String getAMOUNT() {
        return AMOUNT;
    }

    public String getREPEAT() {
        return REPEAT;
    }

    public String getTIME() {
        return TIME==null ? "" : TIME;
    }
}
