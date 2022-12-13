package RestfulBookeroObjectModel;

import com.shaft.api.RestActions;
import com.shaft.driver.SHAFT;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class RestfulBooker {

    //variables
    private SHAFT.API apiObject;
    private RestfulBookerApi restfulBookerApi;
    private RestfulBookerApiBooking restfulBookerApiBooking;

    @BeforeClass
    public void beforeClass(){
        apiObject = new SHAFT.API(restfulBookerApi.BASE_URL);
        restfulBookerApi = new RestfulBookerApi(apiObject);
        restfulBookerApiBooking = new RestfulBookerApiBooking(apiObject);

        restfulBookerApi.login("admin","password123");
    }

    //get list of booking ids
    @Test
    public void getBookIds(){
        restfulBookerApiBooking.getBookIds();
    }

    //get specific book with details
    @Test
    public void getBooking(){
        restfulBookerApiBooking.getBooking("7");
    }

    //create booking with json object
    @Test
    public void createBooking(){
        Response createBookingReq = restfulBookerApiBooking.createBooking("Josh","super bowls","2018-01-01","2019-01-01",
                123,true,"Allen");
        String bookingId = RestActions.getResponseJSONValue(createBookingReq,"bookingid");
        restfulBookerApiBooking.getBooking( bookingId);

        apiObject.assertThatResponse().extractedJsonValue("firstname").
                isEqualTo("Josh").perform();
        apiObject.assertThatResponse().extractedJsonValue("lastname").
                isEqualTo("Allen").perform();
        apiObject.assertThatResponse().extractedJsonValue("totalprice").
                isEqualTo("123").perform();
        apiObject.assertThatResponse().extractedJsonValue("bookingdates.checkin").
                isEqualTo("2018-01-01").perform();
        apiObject.assertThatResponse().extractedJsonValue("bookingdates.checkout").
                isEqualTo("2019-01-01").perform();
    }


    //delete book
    @Test
    public void deleteBooking(){
        restfulBookerApiBooking.deleteBooking("8");
    }

}
