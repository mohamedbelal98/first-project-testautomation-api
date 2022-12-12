package org.example;

import com.shaft.api.RestActions;
import com.shaft.driver.SHAFT;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.simple.JSONObject;
import org.testng.annotations.Test;


public class RestfulBooker_LinearDesign {

    @Test
    public void getBookingIds(){

        SHAFT.API apiObject  = new SHAFT.API("https://restful-booker.herokuapp.com/");
        apiObject.get("booking").setContentType(ContentType.JSON).perform();
    }

    @Test
    public void getBooking(){

        SHAFT.API apiObject = new SHAFT.API("https://restful-booker.herokuapp.com/");
        apiObject.get("booking/" + "8").setContentType(ContentType.JSON).perform();
    }

    @Test
    public void createBooking(){

        SHAFT.API apiObject = new SHAFT.API("https://restful-booker.herokuapp.com/");

        JSONObject createBookingBody = new JSONObject();
        createBookingBody.put("firstname", "Josh");
        createBookingBody.put("additionalneeds", "super bowls");
        JSONObject bookingDates = new JSONObject();
        bookingDates.put("checkin", "2018-01-01");
        bookingDates.put("checkout", "2019-01-01");
        createBookingBody.put("bookingdates", bookingDates);
        createBookingBody.put("totalprice", 111);
        createBookingBody.put("depositpaid", true);
        createBookingBody.put("lastname", "Allen");

        Response createBookingReq = apiObject.post("booking").setRequestBody(createBookingBody).
                setContentType(ContentType.JSON).perform();

        String bookingId = RestActions.getResponseJSONValue(createBookingReq,"bookingid");
        apiObject.get("booking/"+ bookingId).perform();

        apiObject.assertThatResponse().extractedJsonValue("firstname").
                isEqualTo("Josh").perform();
        apiObject.assertThatResponse().extractedJsonValue("lastname").
                isEqualTo("Allen").perform();
        apiObject.assertThatResponse().extractedJsonValue("totalprice").
                isEqualTo("111").perform();
        apiObject.assertThatResponse().extractedJsonValue("bookingdates.checkin").
                isEqualTo("2018-01-01").perform();
        apiObject.assertThatResponse().extractedJsonValue("bookingdates.checkout").
                isEqualTo("2019-01-01").perform();

        SHAFT.Validations.verifyThat().object("firstname").isEqualTo("Josh").perform();

    }

    @Test
    public void updateBooking(){

        SHAFT.API apiObject = new SHAFT.API("https://restful-booker.herokuapp.com/");

        JSONObject updateBookingBody = new JSONObject();
        updateBookingBody.put("firstname", "Mohamed");
        updateBookingBody.put("additionalneeds", "latte");
        JSONObject updatebookingDates = new JSONObject();
        updatebookingDates.put("checkin", "2020-01-01");
        updatebookingDates.put("checkout", "2021-01-01");
        updateBookingBody.put("bookingdates", updatebookingDates);
        updateBookingBody.put("totalprice", 123);
        updateBookingBody.put("depositpaid", true);
        updateBookingBody.put("lastname", "Belal");

        JSONObject authentication = new JSONObject();
        authentication.put("username" , "admin");
        authentication.put("password" , "password123");

        Response createToken = apiObject.post("auth").
                setContentType(ContentType.JSON).
                setRequestBody(authentication).
                perform();

        String token = RestActions.getResponseJSONValue(createToken,"token");

        apiObject.put("booking/" + "8").setContentType(ContentType.JSON).
                setTargetStatusCode(201).
                addHeader("Cookie", "token=" + token);

//        Response updateBookingReq = apiObject.put("booking/1").setRequestBody(updateBookingBody).
//                setContentType(ContentType.JSON).performRequest();
//
//        String updateId = RestActions.getResponseJSONValue(updateBookingReq,"bookingid");
//        apiObject.get("booking" + updateId).perform();

    }

    @Test
    public void partialUpdateBooking(){
        SHAFT.API apiObject = new SHAFT.API("https://restful-booker.herokuapp.com/");

        JSONObject authentication = new JSONObject();
        authentication.put("username","admin");
        authentication.put("password","password123");
        Response createToken = apiObject.post("auth").
                setContentType(ContentType.JSON).
                setRequestBody(authentication).perform();

        String token = RestActions.getResponseJSONValue(createToken,"token");

        apiObject.patch("booking" + "1").
                setContentType(ContentType.JSON).
                setTargetStatusCode(201).
                addHeader("cookie","token=" + token);

    }

    @Test//(dependsOnMethods = {"createBooking"})
    public void deleteBooking(){
        SHAFT.API apiObject = new SHAFT.API("https://restful-booker.herokuapp.com/");

        JSONObject authentication = new JSONObject();
        authentication.put("username" , "admin");
        authentication.put("password" , "password123");

        Response createToken = apiObject.post("auth").
                setContentType(ContentType.JSON).
                setRequestBody(authentication).
                perform();

        String token = RestActions.getResponseJSONValue(createToken,"token");

//        Response getBookingId = apiObject.get("booking").
//                setContentType(ContentType.JSON).
//                setUrlArguments("firstname=Josh&lastname=Allen").
//                perform();
//
//        String bookingId = RestActions.getResponseJSONValue(getBookingId,"bookingid[0]");

        apiObject.delete("booking/" + "8").
                setContentType(ContentType.JSON).
                setTargetStatusCode(201).
                addHeader("Cookie", "token=" + token);
    }

}