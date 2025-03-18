package com.juaracoding.coretan;

import com.juaracoding.utils.DataGenerator;

public class TestDataGenerator {

    public static void main(String[] args) {
        DataGenerator gen = new DataGenerator();
//        System.out.println("Nama\t\tAlamat\t\tNo HP\t\tEmail");
//        for (int i = 0; i < 10; i++) {
//            System.out.println(gen.dataNamaLengkap()+"\t\t"+gen.dataAlamat()+"\t\t"+gen.dataNoHp()+"\t\t"+ gen.dataEmail()+"\t\t");
//        }
        for (int i = 0; i < 10; i++) {
            System.out.println(gen.dataTanggalLahir());
        }
    }
}
