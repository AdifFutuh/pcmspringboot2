package com.juaracoding.coretan;

import com.juaracoding.dto.validation.ValAksesDTO;
import com.juaracoding.security.Crypto;

import java.util.HashMap;
import java.util.Map;

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
//        System.out.println(System.currentTimeMillis());
//        1741268509590
//        1741268546414
//        System.out.println(Crypto.performEncrypt("C1#paul123-poll.chihuy@gmail.com-081286016416#1741269659089"));

        String sortBy = "username";
//        if(sortBy.equals("nama")){
//            System.out.println("nama");
//        }else if(sortBy.equals("alamat")){
//            System.out.println("alamat");
//        }else if(sortBy.equals("tanggalLahir")){
//            System.out.println("tanggalLahir");
//        }else if(sortBy.equals("username")){
//            System.out.println("username");
//        }else {
//            System.out.println("id");
//        }
//
//        switch(sortBy){
//            case "alamat":System.out.println("alamat");break;
//            case "nama":System.out.println("nama");break;
//            case "tanggalLahir":System.out.println("tanggalLahir");break;
//            case "username":System.out.println("username");break;
//            default:System.out.println("id");
//        }

        ValAksesDTO valAksesDTO = new ValAksesDTO();
        valAksesDTO.setDeskripsi("HSAUHSAUHSA");
        valAksesDTO.setNama("paskdpadskpadsk");

        Map<String,Object> m = new HashMap<String, Object>();
        for (Map.Entry<String,Object> map:
             m.entrySet()) {
            System.out.println("Key: " + map.getKey() + " Value: " + map.getValue());
        }
    }
}
