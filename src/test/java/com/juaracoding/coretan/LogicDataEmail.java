package com.juaracoding.coretan;


import java.util.Random;

/**
 *  1. ASCII
 *  2. Random
 */
public class LogicDataEmail {

    public static void main(String[] args) {
        Random rand = new Random();
        /**
         *  1. kombinasi huruf +  angka (8 max 15)
         *  2. @ (1)
         *  3. provider (gmail, yahoo, ymail,rocketmail, dll)
         *  4. . (1)
         *  5. domain (com,edu,co.id)
         */
        String strHuruf = "abcdefghijklmnopqrstuvwxyz";
        String strHurufVokal = "aiueo";
        String strHurufKonsonan = "bcdfghjklmnpqrstvwxyz";
        String strProviderArr [] = {"gmail","ymail","rocketmail","yahoo"};
        String strDomainArr [] = {"com","co.id","edu","sch","gov"};

        for (int i = 0; i < 100000; i++) {
            int intFirst = rand.nextInt(8,15);
//            System.out.println("Random Angka Depan : "+intFirst);
            for (int j = 0; j < intFirst; j++) {
                System.out.print(strHuruf.charAt(rand.nextInt(26)));
            }
            System.out.println(rand.nextInt(1000)+"@"+strProviderArr[rand.nextInt(3)]+"."+strDomainArr[rand.nextInt(5)]);//1-999
        }

    }
}
