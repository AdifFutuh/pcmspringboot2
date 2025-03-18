package com.juaracoding.controller;


import com.juaracoding.model.GroupMenu;
import com.juaracoding.model.Menu;
import com.juaracoding.repo.MenuRepo;
import com.juaracoding.utils.DataGenerator;
import com.juaracoding.utils.TokenGenerator;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.Optional;
import java.util.Random;

import static io.restassured.RestAssured.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class MenuControllerTest extends AbstractTestNGSpringContextTests {


    @Autowired
    private MenuRepo menuRepo;
    private JSONObject req;
    private Menu menu;
    private Random rand;//optional (KTP 16 Digit rand.nextLong(1111111111111111L,9999999999999999L) )
    private String token;
    private Integer status;
    private Boolean success;
    private String message;
    private String data;
    private DataGenerator dataGenerator;

//    passed
    @BeforeClass
    private void init(){
        token = new TokenGenerator(AuthControllerTest.authorization).getToken();
        rand = new Random();
        req = new JSONObject();
        menu = new Menu();
        dataGenerator  = new DataGenerator();
        Optional<Menu> op = menuRepo.findTopByOrderByIdDesc();
        menu = op.get();
    }

    @BeforeTest
    private void setup(){
        /** OPTIONAL == untuk kebutuh environment protocol */
    }

    @Test(priority = 0)
    void save(){
        String dataNama = dataGenerator.dataNamaMenu();
        req.put("nama",dataNama);
        req.put("path","/"+dataNama.toLowerCase().replaceAll(" ","-"));
        req.put("groupMenu",menu.getGroupMenu());

        String pathVariable = "/menu";
        Response response = given().
                header(AuthControllerTest.AUTH_HEADER, token).
                header("Content-Type", "application/json").
                header("Accept", "*/*").
                body(req).request(Method.POST,pathVariable);
        int responseCode = response.statusCode();
        JsonPath jsonPath = response.jsonPath();//body
        ResponseBody responseBody = response.getBody();
        System.out.println(responseBody.asPrettyString());//mau print isi dari response body nya dijadiin prety string
        Assert.assertTrue(Boolean.parseBoolean(jsonPath.get("success").toString()));// kalau true ini lolos
        Assert.assertEquals(responseCode,201);//kalau 201
        Assert.assertEquals(Integer.parseInt(jsonPath.get("status").toString()),201);
        Assert.assertEquals(jsonPath.get("message"),"Data Berhasil Disimpan");
        Assert.assertEquals(jsonPath.get("data"),"");
        Assert.assertNotNull(jsonPath.get("timestamp"));
    }


    @Test(priority = 10)
    void update(){
        String reqNama = dataGenerator.dataNamaMenu();
        String reqPath = "/"+reqNama.toLowerCase().replaceAll(" ","-");
        menu.setNama(reqNama);
        menu.setPath(reqPath);
        req.put("nama",reqNama);
        req.put("path",reqPath);
        req.put("groupMenu",menu.getGroupMenu());


        String pathVariable = "/menu/"+ menu.getId();
        Response response = given().
                header(AuthControllerTest.AUTH_HEADER, token).
                header("Content-Type", "application/json").
                header("Accept", "*/*").
                body(req).request(Method.PUT,pathVariable);
        int responseCode = response.statusCode();
        JsonPath jsonPath = response.jsonPath();//body
        ResponseBody responseBody = response.getBody();
        System.out.println(responseBody.asPrettyString());//mau print isi dari response body nya dijadiin prety string
        Assert.assertTrue(Boolean.parseBoolean(jsonPath.get("success").toString()));// kalau true ini lolos
        Assert.assertEquals(responseCode,200);//kalau 201
        Assert.assertEquals(Integer.parseInt(jsonPath.get("status").toString()),200);
        Assert.assertEquals(jsonPath.get("message"),"Data Berhasil Diubah");
        Assert.assertEquals(jsonPath.get("data"),"");
        Assert.assertNotNull(jsonPath.get("timestamp"));
    }

//    suite
//    @Test(priority = 999)
//    void delete(){
//
//    }


}
