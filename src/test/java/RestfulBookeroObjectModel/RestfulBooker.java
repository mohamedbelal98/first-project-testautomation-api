package RestfulBookeroObjectModel;

import com.shaft.api.RestActions;
import com.shaft.driver.SHAFT;
import com.shaft.tools.io.ExcelFileManager;
import com.shaft.tools.io.JSONFileManager;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class RestfulBooker {

    //variables
    private SHAFT.API apiObject;
    private RestfulBookerApi restfulBookerApi;
    private RestfulBookerApiBooking restfulBookerApiBooking;
    ExcelFileManager excelFileManager;
    JSONFileManager jsonFileManager;

    @BeforeClass
    public void beforeClass(){
        apiObject = new SHAFT.API(restfulBookerApi.BASE_URL);
        restfulBookerApi = new RestfulBookerApi(apiObject);
        restfulBookerApiBooking = new RestfulBookerApiBooking(apiObject);
        excelFileManager = new ExcelFileManager(System.getProperty("testDataFolderPath") + "login.xlsx");
        jsonFileManager = new JSONFileManager(System.getProperty("testDataFolderPath") + "jsonfile.json");

        restfulBookerApi.login(excelFileManager.getCellData("username"),
                excelFileManager.getCellData("password"));

//        restfulBookerApi.login(jsonFileManager.getTestData("username"),
//                jsonFileManager.getTestData("password"));
    }


    //get list of booking ids
    @Test(description = "Get all books id",priority = 1)
    public void getBookIds(){
        restfulBookerApiBooking.getBookIds();
    }

    //get specific book with details
    @Test(description = "Get a specific book from id",priority = 2)
    public void getBooking(){
        restfulBookerApiBooking.getBooking("7");
    }

    //create booking with json object
    @Test(description = "create a new Booking and assert the data",priority = 3)
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
    @Test(description = "Delete a specific book from id",priority = 4)
    public void deleteBooking(){
        restfulBookerApiBooking.deleteBooking("8");
    }

}
