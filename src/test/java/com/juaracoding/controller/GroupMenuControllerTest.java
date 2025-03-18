package com.juaracoding.controller;


import com.juaracoding.model.GroupMenu;
import com.juaracoding.repo.GroupMenuRepo;
import com.juaracoding.utils.DataGenerator;
import com.juaracoding.utils.TokenGenerator;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import java.io.File;
import java.util.*;
import static io.restassured.RestAssured.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class GroupMenuControllerTest extends AbstractTestNGSpringContextTests {


    @Autowired
    private GroupMenuRepo groupMenuRepo;
    private JSONObject req;
    private GroupMenu groupMenu;
    private Random rand;//optional (KTP 16 Digit rand.nextLong(1111111111111111L,9999999999999999L) )
    private String token;
    private DataGenerator dataGenerator;

//    passed
    @BeforeClass
    private void init(){
        token = new TokenGenerator(AuthControllerTest.authorization).getToken();
        rand = new Random();
        req = new JSONObject();
        groupMenu = new GroupMenu();
        dataGenerator  = new DataGenerator();
        Optional<GroupMenu> op = groupMenuRepo.findTopByOrderByIdDesc();
        groupMenu = op.get();
    }

    @BeforeTest
    private void setup(){
        /** OPTIONAL == untuk kebutuh environment protocol */
    }

    @Test(priority = 0)
    void save(){
        req.put("nama",dataGenerator.dataNamaLengkap());
        req.put("deskripsi",dataGenerator.dataNamaDepan()+dataGenerator.dataNamaBelakang());

        String pathVariable = "/group-menu";
        Response response = given().
                header(AuthControllerTest.AUTH_HEADER, token).
                header("Content-Type", "application/json").
                header("Accept", "*/*").
                body(req).request(Method.POST,pathVariable);
        int responseCode = response.statusCode();
        JsonPath jsonPath = response.jsonPath();//body
//        ResponseBody responseBody = response.getBody();
//        System.out.println(responseBody.asPrettyString());//mau print isi dari response body nya dijadiin prety string
        Assert.assertTrue(Boolean.parseBoolean(jsonPath.get("success").toString()));// kalau true ini lolos
        Assert.assertEquals(responseCode,201);//kalau 201
        Assert.assertEquals(Integer.parseInt(jsonPath.get("status").toString()),201);
        Assert.assertEquals(jsonPath.get("message"),"Data Berhasil Disimpan");
        Assert.assertEquals(jsonPath.get("data"),"");
        Assert.assertNotNull(jsonPath.get("timestamp"));
    }


    @Test(priority = 10)
    void update(){
        String reqNama = dataGenerator.dataNamaTim();
        String reqDeskripsi = dataGenerator.dataNamaDepan()+dataGenerator.dataNamaBelakang();
        groupMenu.setNama(reqNama);
        groupMenu.setDeskripsi(reqDeskripsi);

        req.put("nama",reqNama);
        req.put("deskripsi",reqDeskripsi);

        String pathVariable = "/group-menu/"+groupMenu.getId();
        Response response = given().
                header(AuthControllerTest.AUTH_HEADER, token).
                header("Content-Type", "application/json").
                header("Accept", "*/*").
                body(req).request(Method.PUT,pathVariable);
        int responseCode = response.statusCode();
        JsonPath jsonPath = response.jsonPath();//body
//        ResponseBody responseBody = response.getBody();
//        System.out.println(responseBody.asPrettyString());//mau print isi dari response body nya dijadiin prety string
        Assert.assertTrue(Boolean.parseBoolean(jsonPath.get("success").toString()));// kalau true ini lolos
        Assert.assertEquals(responseCode,200);//kalau 201
        Assert.assertEquals(Integer.parseInt(jsonPath.get("status").toString()),200);
        Assert.assertEquals(jsonPath.get("message"),"Data Berhasil Diubah");
        Assert.assertEquals(jsonPath.get("data"),"");
        Assert.assertNotNull(jsonPath.get("timestamp"));
    }

    @Test(priority = 20)
    void findById(){
        String pathVariable = "/group-menu/"+groupMenu.getId();
        Response response = given().
                header(AuthControllerTest.AUTH_HEADER, token).
                header("Content-Type", "application/json").
                header("Accept", "*/*").
                request(Method.GET,pathVariable);
        int responseCode = response.statusCode();
        JsonPath jsonPath = response.jsonPath();//body
//        ResponseBody responseBody = response.getBody();
//        System.out.println(responseBody.asPrettyString());//mau print isi dari response body nya dijadiin prety string
        Assert.assertTrue(Boolean.parseBoolean(jsonPath.get("success").toString()));// kalau true ini lolos
        Assert.assertEquals(responseCode,200);//kalau 201
        Assert.assertEquals(Integer.parseInt(jsonPath.get("status").toString()),200);
        Assert.assertEquals(jsonPath.get("message"),"Data Ditemukan");
        Assert.assertNotNull(jsonPath.get("timestamp"));
        /** compare isi data kembalian dari server dengan data yang diquery di fungsi init */
        Assert.assertEquals(Long.parseLong(jsonPath.get("data.id").toString()),groupMenu.getId());
        Assert.assertEquals(jsonPath.get("data.nama"),groupMenu.getNama());
        Assert.assertEquals(jsonPath.get("data.deskripsi"),groupMenu.getDeskripsi());
    }
    @Test(priority = 30)
    void findAll(){
        String pathVariable = "/group-menu";
        Response response = given().
                header(AuthControllerTest.AUTH_HEADER, token).
                header("Content-Type", "application/json").
                header("Accept", "*/*").
                request(Method.GET,pathVariable);
        int responseCode = response.statusCode();
        JsonPath jsonPath = response.jsonPath();//body
//        ResponseBody responseBody = response.getBody();
//        System.out.println(responseBody.asPrettyString());//mau print isi dari response body nya dijadiin prety string
        /** untuk case ini pengambilan datanya menggunakan List , Jadi dipassing ke object List<Map<String,Object>> */
        List<Map<String,Object>> lt = jsonPath.getList("data.content");

        Assert.assertEquals(Integer.parseInt(jsonPath.get("data.total-data").toString()),lt.size());//info total Data dengan content yang dikirim harus sama
        Assert.assertEquals(Integer.parseInt(jsonPath.get("data.current-page").toString()),0);//default untuk findAll
        Assert.assertEquals(jsonPath.get("data.sort").toString(),"asc");//default untuk findAll
        Assert.assertEquals(jsonPath.get("data.sort-by").toString(),"id");//default untuk findAll
        Assert.assertEquals(jsonPath.get("data.column-name").toString(),"");//default untuk findAll
        Assert.assertEquals(jsonPath.get("data.value").toString(),"");//default untuk findAll


        Assert.assertTrue(Boolean.parseBoolean(jsonPath.get("success").toString()));// kalau true ini lolos
        Assert.assertEquals(responseCode,200);//kalau 200
        Assert.assertEquals(Integer.parseInt(jsonPath.get("status").toString()),200);
        Assert.assertEquals(jsonPath.get("message"),"Data Ditemukan");
        Assert.assertNotNull(jsonPath.get("timestamp"));
    }

    @Test(priority = 40)
    void findByParam(){
        String pathVariable = "/group-menu/asc/id/0";
        String strValue = groupMenu.getNama();
        Response response = given().
                header(AuthControllerTest.AUTH_HEADER, token).
                header("Content-Type", "application/json").
                header("Accept", "*/*").
                param("column","nama").//menggunakan kolom nama , karena unik
                param("value",strValue).
                param("size",10).
                request(Method.GET,pathVariable);

        int responseCode = response.statusCode();
        JsonPath jsonPath = response.jsonPath();//body
//        ResponseBody responseBody = response.getBody();
//        System.out.println(responseBody.asPrettyString());//mau print isi dari response body nya dijadiin prety string
        /** untuk case ini pengambilan datanya menggunakan List , Jadi dipassing ke object List<Map<String,Object>> */
        List<Map<String,Object>> lt = jsonPath.getList("data.content");

        /** karena isi nya hanya 1 data berdasarkan pencarian maka value nya kita tampung ke object Map<String,Object> */
        Map<String,Object> map = lt.get(0);

        Assert.assertEquals(Integer.parseInt(jsonPath.get("data.total-data").toString()),lt.size());//info total Data dengan content yang dikirim harus sama

        Assert.assertEquals(Integer.parseInt(jsonPath.get("data.current-page").toString()),0);//sudah di set di path variable
        Assert.assertEquals(jsonPath.get("data.sort").toString(),"asc");//sudah di set di path variable
        Assert.assertEquals(jsonPath.get("data.sort-by").toString(),"id");//sudah di set di path variable
        Assert.assertEquals(Integer.parseInt(jsonPath.get("data.size-per-page").toString()),10);//sudah di set di query param nya
        Assert.assertEquals(jsonPath.get("data.column-name").toString(),"nama");//sudah di set di query param nya
        Assert.assertEquals(jsonPath.get("data.value").toString(),strValue);//sudah di set di query param nya

        /** compare content data nya */
        Assert.assertEquals(map.get("nama"),strValue);
        Assert.assertEquals(Long.parseLong(map.get("id").toString()),groupMenu.getId());
        Assert.assertEquals(map.get("deskripsi"),groupMenu.getDeskripsi());

        Assert.assertTrue(Boolean.parseBoolean(jsonPath.get("success").toString()));// kalau true ini lolos
        Assert.assertEquals(responseCode,200);//kalau 200
        Assert.assertEquals(Integer.parseInt(jsonPath.get("status").toString()),200);
        Assert.assertEquals(jsonPath.get("message"),"Data Ditemukan");
        Assert.assertNotNull(jsonPath.get("timestamp"));
    }

    @Test(priority = 50)
    private void uploadSheet(){
        String pathVariable = "/group-menu/upload-excel";
        Response response = given().
                header("Content-Type","multipart/form-data").
                header("accept","*/*").
                header(AuthControllerTest.AUTH_HEADER,token).
                /** parameter ke tiga harus spesifik, type content nya apa, kalau tidak nanti dijadikan default application/octet-stream padahal sudah divalidasi dengan content excel*/
                multiPart("file",new File(System.getProperty("user.dir")+"/src/test/resources/data-test/group-menu.xlsx"),"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet").
                request(Method.POST, pathVariable);

        int responseCode = response.statusCode();
        JsonPath jsonPath = response.jsonPath();//body
//        ResponseBody responseBody = response.getBody();
//        System.out.println(responseBody.asPrettyString());//mau print isi dari response body nya dijadiin prety string
        Assert.assertTrue(Boolean.parseBoolean(jsonPath.get("success").toString()));// kalau true ini lolos
        Assert.assertEquals(responseCode,201);
        Assert.assertEquals(Integer.parseInt(jsonPath.get("status").toString()),201);
        Assert.assertEquals(jsonPath.get("message"),"Upload File Excel Berhasil");
        Assert.assertEquals(jsonPath.get("data"),"");
        Assert.assertNotNull(jsonPath.get("timestamp"));
    }

    @Test(priority = 60)
    private void downloadExcel(){
        String pathVariable = "/group-menu/excel";
        Response response = given().
                header("accept","application/vnd.openxmlformats-officedocument.spreadsheetml.sheet").
                param("column","nama").
                header(AuthControllerTest.AUTH_HEADER,token).
                param("value",groupMenu.getNama()).
                request(Method.GET, pathVariable);
        int responseCode = response.statusCode();
//        ResponseBody responseBody = response.getBody();
//        System.out.println(responseBody.asPrettyString());//mau print isi dari response body nya dijadiin prety string
        Assert.assertEquals(responseCode,200);
        /** khusus untuk download file harus di cek header nya */
        Assert.assertTrue(response.getHeader("Content-Disposition").contains(".xlsx"));// file nya memiliki extension .xlsx
        Assert.assertEquals(response.getHeader("Content-Type"),"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");// content type wajib ini untuk excel
    }

    @Test(priority = 70)
    private void downloadPDF(){
        String pathVariable = "/group-menu/pdf";
        Response response = given().
                header("accept","application/pdf").
                param("column","nama").
                header(AuthControllerTest.AUTH_HEADER,token).
                param("value",groupMenu.getNama()).
                request(Method.GET, pathVariable);
        int responseCode = response.statusCode();
//        ResponseBody responseBody = response.getBody();
//        System.out.println(responseBody.asPrettyString());//mau print isi dari response body nya dijadiin prety string
        Assert.assertEquals(responseCode,200);
        /** khusus untuk download file harus di cek header nya */
        Assert.assertTrue(response.getHeader("Content-Disposition").contains(".pdf"));// file nya memiliki extension .xlsx
        Assert.assertEquals(response.getHeader("Content-Type"),"application/pdf");// content type wajib ini untuk excel
    }

    @Test(priority = 999)
    private void delete(){
        String pathVariable = "/group-menu/"+groupMenu.getId();
        /** jika ingin menjalankan suite / integration test fungsional delete di taruh pada urutan paling akhir , karena data yang dipilih di awal di gunakan untuk validasi di fungsi-fungsi sebelumnya */
        Response response = given().
                header("Content-Type","application/json").
                header("accept","*/*").
                header(AuthControllerTest.AUTH_HEADER,token).
                request(Method.DELETE, pathVariable);
//        ResponseBody responseBody = response.getBody();
//        System.out.println(responseBody.asPrettyString());//mau print isi dari response body nya dijadiin prety string
        int responseCode = response.statusCode();
        JsonPath jsonPath = response.jsonPath();
        Assert.assertTrue(Boolean.parseBoolean(jsonPath.get("success").toString()));// kalau true ini lolos
        Assert.assertEquals(responseCode,200);
        Assert.assertEquals(Integer.parseInt(jsonPath.get("status").toString()),200);
        Assert.assertEquals(jsonPath.get("message"),"Data Berhasil Dihapus");
        Assert.assertEquals(jsonPath.get("data"),"");
        Assert.assertNotNull(jsonPath.get("timestamp"));
    }
}