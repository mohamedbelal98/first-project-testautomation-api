package org.example;

import io.restassured.http.ContentType;
import org.json.JSONException;
import org.json.JSONObject;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.equalTo;

public class Main {

//random test
    @Test
    public void printer(){
        System.out.println("test");
    }
//validate status code is equal 200
    @Test
    public void validateStatusCode(){
        given().
                get("http://zippopotam.us/us/90210").
        then().
                statusCode(200);
    }
//validate content type is json
    @Test
    public void validateContentType(){
        given().
                get("http://zippopotam.us/us/90210").
        then().
                contentType(ContentType.JSON);
    }
//print body of link
    @Test
    public void requestLog(){
        given().
                log().all().
                get("http://zippopotam.us/us/90210").
        then().
                log().body();
    }
//validate the data of zip code
    @Test
    public void validateZipCode(){
        given().
                get("http://zippopotam.us/us/90210").
        then().
                body("'post code'", equalTo("90210")).
                body("country", equalTo("United States")).
                body("'country abbreviation'", equalTo("US")).
                body("places[0].'place name'", equalTo("Beverly Hills")).
                body("places[0].'longitude'", equalTo("-118.4065")).
                body("places[0].state", equalTo("California")).
                body("places[0].'state abbreviation'", equalTo("CA")).
                body("places[0].latitude", equalTo("34.0901"));
    }
//data provider to pass data to the test
    @DataProvider(name = "data_provider")
    public Object[][] zipCodeAndPlace() {
        return new Object[][]{
                {"us", "90210", "Beverly Hills"},
                {"us", "12345", "Schenectady"},
                {"ca", "B2R", "Waverley"}
        };
    }
//reserved data from data provider and test it
    @Test(dataProvider = "data_provider")
    public void getDataFromDataProvider(String countryCode, String zipCode, String expectedPlaceName){
        given().
                pathParams("countryCode",countryCode).
                pathParams("zipCode", zipCode).
        when().
                get("http://zippopotam.us/{countryCode}/{zipCode}").
        then().
                body("places[0].'place name'", equalTo(expectedPlaceName));
    }
//post data
    @Test
    public void post() throws JSONException {

        JSONObject requestPost = new JSONObject();
        requestPost.put("name","morpheus");
        requestPost.put("job","leader");

        given().
                header("Content-Type","application/json").
        when().
                post("https://reqres.in/api/users").
        then().
                statusCode(201);
    }
//patch data
    @Test
    public void patch() throws JSONException {

        JSONObject requestPatch = new JSONObject();
        requestPatch.put("name","morpheus");

        given().
                header("Content-Type","application/json").
        when().
                patch("https://reqres.in/api/users/2").
        then().
                statusCode(200).
                log().all();
    }
//put data
    @Test
    public void put() throws JSONException {

        JSONObject requestPatch = new JSONObject();
        requestPatch.put("name","morpheus");

        given().
                header("Content-Type","application/json").
        when().
                put("https://reqres.in/api/users/2").
        then().
                statusCode(200).
                log().all();
    }
//delete data
    @Test
    public void delete(){

        when().
                delete("https://reqres.in/api/users/2").
        then().
                statusCode(204);
    }

}
