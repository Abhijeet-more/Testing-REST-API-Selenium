package com.hellofresh.challenge;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.internal.http.Method;
import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.jayway.restassured.RestAssured.given;

public class api_test {
    private String baseURI;
    public final String PATH = "/iso2code/";

    /**
     * Initializes URI for rest assured
     * @param uri - string containing the URI for GET/POST request
     */
    @Parameters({"uri"})
    @BeforeTest
    public void initSetup(String uri){
        baseURI = uri;
        RestAssured.baseURI = baseURI;
    }

    /**
     * Sends the GET request for fetching details of all the countries,
     * and finds out if US, Germany and Great Britain exist in the list
     */
    @Test
    public void getAllNations(){
        RequestSpecification httpReq = given();
        Response response = httpReq.get("all");

        JsonPath jsonPath = new JsonPath(response.getBody().asString());
        String user_id = jsonPath.getString("RestResponse.result.alpha2_code");
        if(user_id.contains("US") && user_id.contains("DE") && user_id.contains("GB")){
            System.out.println("Response returned US, DE and DB");
            System.out.println("Response code is correct: 200");
        }
        Boolean nationsExist = user_id.contains("US") && user_id.contains("DE") && user_id.contains("GB");
        System.out.println("Name: "+user_id);
        Assert.assertEquals(200, response.getStatusCode(), "Correct response code received.");
        Assert.assertTrue(nationsExist);
    }

    /**
     * Sends a GET request only to fetch data for
     * country code: US
     */
    @Test
    public void getSpecificNationUS(){
        RequestSpecification httpReq = given();
        Response response = httpReq.get(PATH+"US");

        JsonPath jsonPath = new JsonPath(response.getBody().asString());
        String user_id = jsonPath.getString("RestResponse.result.alpha2_code");
        if(user_id.contains("US") && 200 == response.getStatusCode()){
            System.out.println("Response returned US");
            System.out.println("Response code is correct: 200");
        }
        Boolean nationExist = user_id.contains("US");
        System.out.println("Name: "+jsonPath.getString("RestResponse.result.name"));
        Assert.assertEquals(200, response.getStatusCode(), "Correct response code received.");
        Assert.assertTrue(nationExist);
    }

    /**
     * Sends a GET request only to fetch data for
     * country code: DE
     */
    @Test
    public void getSpecificNationDE(){
        RequestSpecification httpReq = given();
        Response response = httpReq.get(PATH+"DE");

        JsonPath jsonPath = new JsonPath(response.getBody().asString());
        String user_id = jsonPath.getString("RestResponse.result.alpha2_code");
        if(user_id.contains("DE") && 200 == response.getStatusCode()){
            System.out.println("Response returned DE");
            System.out.println("Response code is correct: 200");
        }
        Boolean nationExist = user_id.contains("DE");
        System.out.println("Name: "+jsonPath.getString("RestResponse.result.name"));
        Assert.assertEquals(200, response.getStatusCode(), "Correct response code received.");
        Assert.assertTrue(nationExist);
    }

    /**
     * Sends a GET request only to fetch data for
     * country code: GB
     */
    @Test
    public void getSpecificNationGB(){
        RequestSpecification httpReq = given();
        Response response = httpReq.get(PATH+"GB");

        JsonPath jsonPath = new JsonPath(response.getBody().asString());
        String user_id = jsonPath.getString("RestResponse.result.alpha2_code");
        if(user_id.contains("GB") && 200 == response.getStatusCode()){
            System.out.println("Response returned GB");
            System.out.println("Response code is correct: 200");
        }
        Boolean nationExist = user_id.contains("GB");
        System.out.println("Name: "+jsonPath.getString("RestResponse.result.name"));
        Assert.assertEquals(200, response.getStatusCode(), "Correct response code received.");
        Assert.assertTrue(nationExist);
    }

    /**
     * Sends a GET request for the country that doesn't exist
     * in the list.
     */
    @Test
    public void getResponseForInexistentNation(){
        RequestSpecification httpReq = given();
        Response response = httpReq.get("all");
        JsonPath jsonPath = new JsonPath(response.getBody().asString());
        String name = jsonPath.getString("RestResponse.result.name");
        if(!name.contains("Balochistan") && 200 == response.getStatusCode()){
            System.out.println("Balochistan not returned in response");
            System.out.println("Response code is correct: 200");
        }
        Boolean nationExist = name.contains("Balochistan");
        Assert.assertEquals(200, response.getStatusCode(), "Correct response code received.");
        Assert.assertFalse(nationExist);
    }

    /**
     * Sends an incorrect code in GET request to see
     * if the response returns en error.
     */
    @Test
    public void getResponseForIncorrectCode(){
        RequestSpecification httpReq = given();
        Response response = httpReq.get(PATH+"ABCD");
        JsonPath jsonPath = new JsonPath(response.getBody().asString());
        String user_id = jsonPath.getString("RestResponse.messages[0]");
        if(user_id.contains("No matching country found for requested code") && response.getStatusCode() == 200) {
            System.out.println("\'No matching country found for requested code [ABCD]\' found in message.");
            System.out.println("Response code is correct: 200");
        }
        Assert.assertEquals(200, response.getStatusCode(), "Correct response code received.");
    }

    /**
     * Sends POST request , and extracts the content received (will fail for now)
     */
    @Test
    public void getResponseForPostValue(){
        RequestSpecification httpReq = given();
        Response response = httpReq.post(PATH+"US");
        JsonPath jsonPath = new JsonPath(response.getBody().asString());
        String name = jsonPath.getString("RestResponse.result.name");
        System.out.println("get balochistan: "+response.getBody().asString());
        if(name.contains("United States of America") && response.getStatusCode() == 200) {
            System.out.println("Response returned: United States of America");
            System.out.println("Response code is correct: 200");
        }
        Boolean nationExist = jsonPath.getString("RestResponse.result.alpha3_code").contains("USA");
        System.out.println("Name: "+jsonPath.getString("RestResponse.result.name"));
        Assert.assertEquals(200, response.getStatusCode(), "Correct response code received.");
        Assert.assertTrue(nationExist);
    }
}
