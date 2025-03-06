package com.juaracoding.coretan;

import com.juaracoding.security.Crypto;

public class ContohASCII {


    public static void main(String[] args) {
        // hanya boleh angka dan huruf kecil serta titik
        // oiandsoijaldkaokjsoiajeropiheorihweroihworhopwerouiwhrladnlojoijrtoy
        // AES -> Advanced Encryption Standard
        // minimal 6 maksimal 16
//        Scanner sc = new Scanner(System.in);
//        String strInput = sc.nextLine();
//        char[] charArray = strInput.toCharArray();
//
//        for (int i = 0; i < charArray.length ;i++) {
//            int intCh = charArray[i];
//            if(
//                    !((intCh >= 48 && intCh <= 57) &&
//                            (intCh >= 97 && intCh <= 122) &&
//                            intCh == 46)
//            ) {
//                System.out.println("Format Tidak Sesuai !! hanya boleh angka dan huruf kecil serta titik");
//                break;
//            }
//        }
//        if((charArray.length >=6 && charArray.length<=16)) {
//            System.out.println("Format Tidak Sesuai !! Minimal 6 Maksimal 16");
//        }
//"
//        System.out.println("\"");
//        System.out.println("a".equals("r"));
//        System.out.println("г");
//        System.out.println((int)'н');
//        System.out.println("AYAн".equals("Å"));
        System.out.println(System.currentTimeMillis());
//        1741268509590
//        1741268546414
        System.out.println(Crypto.performEncrypt("C1#paul123-poll.chihuy@gmail.com-081286016416#1741269659089"));

    }
}
