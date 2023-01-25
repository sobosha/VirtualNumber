package com.diaco.majazi.Main.Main;

public class requestNumber {
    String APICODE;
    int COUNTRY_CODE;
    String OPERATOR_CODE="any";
    int Service_CODE;

    public requestNumber(String APICODE, int COUNTRY_CODE, int service_CODE) {
        this.APICODE = APICODE;
        this.COUNTRY_CODE = COUNTRY_CODE;
        Service_CODE = service_CODE;
    }
}
