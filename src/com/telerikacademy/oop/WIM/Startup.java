package com.telerikacademy.oop.WIM;

import com.telerikacademy.oop.WIM.core.WIMEngineImpl;


public class Startup {
    
    public static void main(String[] args) {

        WIMEngineImpl engine = new WIMEngineImpl();
        engine.start();
    }
}
