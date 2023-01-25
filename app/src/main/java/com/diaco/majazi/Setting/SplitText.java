package com.diaco.majazi.Setting;


public class SplitText {

    public static String GetOKPrice(String kham)
    {
        String OK = "";
        int count = 0;
        for (int i = (kham.length() - 1); i >= 0 ; i--)
        {
            count++;
            if (count == 4) {
                OK =  "," + OK;
                count = 1;
            }
            OK = kham.charAt(i) + OK;
        }
        return OK;
    }
}
