package com.mycompany.testprojekt;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Kai
 */
public class TestJavaKlasse {

    public static void main(String[] args) {
        MeineNeueKlasse test = new MeineNeueKlasse();
        
        test.tuEtwas();
        test.etwasAnderes();
        
        MeineNeueKlasse kind = new KindKlasse();
        kind.etwasAnderes();
        
        KindKlasse kind2 = new KindKlasse();
        kind.etwasAnderes();
    }
}
