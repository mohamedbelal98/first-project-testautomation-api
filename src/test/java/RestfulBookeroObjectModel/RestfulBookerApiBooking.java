package RestfulBookeroObjectModel;

import com.shaft.driver.SHAFT;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.simple.JSONObject;


public class RestfulBookerApiBooking {

    private SHAFT.API apiObject;

    public RestfulBookerApiBooking(SHAFT.API apiObject) {
        this.apiObject = apiObject;
    }

    public Response getBookIds() {
        return apiObject.get("booking").setContentType(ContentType.JSON).perform();
    }

    public Response getBooking(String bookingId) {
        return apiObject.get("booking/" + bookingId).setContentType(ContentType.JSON).perform();
    }

    public Response createBooking(String firstname, String additionalneeds, String checkin, String checkout,
                                  int totalprice, boolean depositpaid,String lastname) {

        return apiObject.post("booking").
                setContentType(ContentType.JSON).
                setRequestBody(createBookingBody(firstname,additionalneeds,checkin,checkout,totalprice,depositpaid,lastname)).perform();
    }

    public Response deleteBooking(String bookingId){
        return apiObject.delete("booking/" + bookingId).
        setTargetStatusCode(201).perform();


    }

    private JSONObject createBookingBody(String firstname, String additionalneeds, String checkin, String checkout,
                                         int totalprice, boolean depositpaid,String lastname){
        JSONObject createBookingBody = new JSONObject();
        createBookingBody.put("firstname", firstname);
        createBookingBody.put("additionalneeds", additionalneeds);
        JSONObject bookingDates = new JSONObject();
        bookingDates.put("checkin", checkin);
        bookingDates.put("checkout", checkout);
        createBookingBody.put("bookingdates", bookingDates);
        createBookingBody.put("totalprice", totalprice);
        createBookingBody.put("depositpaid", depositpaid);
        createBookingBody.put("lastname", lastname);

        return createBookingBody;
    }

}