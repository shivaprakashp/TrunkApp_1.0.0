package com.opera.app.utils;

import com.opera.app.enums.WalletEnums;

public class OperaManager {

    private static OperaManager operaManager = null;
    private WalletEnums enums;

    private OperaManager(){}

    public static OperaManager createInstance(){
        if (operaManager==null){
            operaManager = new OperaManager();
        }

        return operaManager;
    }

    public void setWalletData(WalletEnums enums){
        this.enums = enums;
    }

    public WalletEnums getEnums(){
        return enums;
    }

}
