package com.juaracoding.controller;

import com.juaracoding.utils.CredentialsAuth;
import com.juaracoding.utils.DataGenerator;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import jakarta.servlet.http.HttpServletResponse;
import org.json.simple.JSONObject;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Random;

import static io.restassured.RestAssured.config;
import static io.restassured.RestAssured.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class AuthControllerTest extends AbstractTestNGSpringContextTests {

    private JSONObject req = new JSONObject();
    private String token;
    private Random rand = new Random();
    public static String authorization;
    private DataGenerator dataGenerator;
    private String username;//nanti ini digunakan untuk flow regis -> verify -> login
    private String password;//nanti ini digunakan untuk flow regis -> verify -> login
    private String email;//nanti ini digunakan untuk flow verify -> login
    private Integer otp;//nanti ini digunakan untuk flow verify -> login
    private Boolean isOk = false;//untuk menjaga setiap step estafet, jika maju ke langkah berikutnya tetapi isOk == false maka proses nya di skip

    /** karena gak ada standard yang pasti untuk header key token
     * maka key nya dibuat variable agar suatu saat jika berubah semisal menjadi JwtToken atuapun jwt_token
     * tinggal diubah disini saja value AUTH_HEADER nya.....
     */
    public static final String AUTH_HEADER = "Authorization";

    @BeforeClass
    void init(){
        RestAssured.baseURI = "http://localhost:8083";
        dataGenerator = new DataGenerator();
    }

    @Test(priority = 0)
    void loginAdmin(){
        /** masukkan credentials admin sebagai default untuk proses login */
        req.put("username", CredentialsAuth.ADMIN_USER_NAME);
        req.put("password", CredentialsAuth.ADMIN_PASSWORD);
        Response response =  given().
                header("Content-Type","application/json").
                header("accept","*/*").body(req).
                request(Method.POST, "auth/login");
        int intResponse = response.getStatusCode();
        JsonPath jPath = response.jsonPath();
        this.token = "Bearer "+jPath.getString("token");
        if(intResponse != 200 || token==null){
            System.out.println("ADMIN USERNAME DAN PASSWORD TIDAK ADA, SISTEM AKAN DIHENTIKAN PROSES NYA");
            System.exit(0);
        }
        setToken();
    }

    void setToken(){
        authorization = this.token;
    }

    @Test(priority = 10)
    void regis(){
        isOk = false;
        username = dataGenerator.dataUsername();
        password = dataGenerator.dataPassword();
        email = dataGenerator.dataEmail();

        /** masukkan credentials admin sebagai default untuk proses login */
        req.put("username", username);
        req.put("password", password);
        req.put("email", email);
        req.put("alamat", dataGenerator.dataAlamat());
        req.put("no-hp", dataGenerator.dataNoHp());
        req.put("tanggal-lahir", dataGenerator.dataTanggalLahir());
        req.put("nama", dataGenerator.dataNamaLengkap());

        Response response =  given().
                header("Content-Type","application/json").
                header("accept","*/*").body(req).
                request(Method.POST, "auth/regis");
        int intResponse = response.getStatusCode();
        JsonPath jPath = response.jsonPath();
        ResponseBody responseBody = response.getBody();
        System.out.println(responseBody.asPrettyString());//mau print isi dari response body nya dijadiin prety string
        otp = Integer.parseInt(jPath.get("otp").toString());
        if(otp!=null && intResponse == 200 && email!=null){
            isOk=true;
        }
        Assert.assertEquals(intResponse,200);
        Assert.assertEquals(jPath.getString("email"),email);
    }

    @Test(priority = 20)
    void verifyRegis(){
        if(!isOk){//artinya step sebelumnya gagal, jadi step ini tidak perlu dieksekusi
            return;
        }
        isOk = false;
        /** masukkan credentials admin sebagai default untuk proses login */
        req.put("otp", otp==null?rand.nextInt(111111,999999):otp);
        req.put("email", email==null?dataGenerator.dataEmail():email);

        Response response =  given().
                header("Content-Type","application/json").
                header("accept","*/*").body(req).
                request(Method.POST, "auth/verify-regis");
        int intResponse = response.getStatusCode();
        ResponseBody responseBody = response.getBody();
        System.out.println(responseBody.asPrettyString());//mau print isi dari response body nya dijadiin prety string
        if(intResponse == 200){
            isOk=true;
        }
        Assert.assertEquals(responseBody.asPrettyString(),"Registrasi Berhasil");
        Assert.assertEquals(intResponse,200);
    }

    @Test(priority = 30)
    void loginEstafet(){
        /** masukkan credentials admin sebagai default untuk proses login */
        req.put("username", username);//melakukan login dari proses estafet registrasi
        req.put("password",password);//melakukan login dari proses estafet registrasi
        Response response =  given().
                header("Content-Type","application/json").
                header("accept","*/*").body(req).
                request(Method.POST, "auth/login");
        int intResponse = response.getStatusCode();
        JsonPath jPath = response.jsonPath();
        Assert.assertNotNull(jPath.getString("token"));
        Assert.assertNotNull(jPath.getString("menu"));
        Assert.assertEquals(intResponse,200);
    }
}
