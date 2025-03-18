package com.juaracoding.controller;


import com.juaracoding.model.GroupMenu;
import com.juaracoding.repo.GroupMenuRepo;
import com.juaracoding.utils.DataGenerator;
import com.juaracoding.utils.TokenGenerator;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;
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
public class GroupMenuControllerTest extends AbstractTestNGSpringContextTests {


    @Autowired
    private GroupMenuRepo groupMenuRepo;
    private JSONObject req;
    private GroupMenu groupMenu;
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
        ResponseBody responseBody = response.getBody();
        System.out.println(responseBody.asPrettyString());//mau print isi dari response body nya dijadiin prety string
        Assert.assertTrue(Boolean.parseBoolean(jsonPath.get("success").toString()));// kalau true ini lolos
        Assert.assertEquals(responseCode,200);//kalau 201
        Assert.assertEquals(Integer.parseInt(jsonPath.get("status").toString()),200);
        Assert.assertEquals(jsonPath.get("message"),"Data Berhasil Diubah");
        Assert.assertEquals(jsonPath.get("data"),"");
        Assert.assertNotNull(jsonPath.get("timestamp"));
    }

//    @Test(priority = 999)
//    void delete(){
//
//    }


}
