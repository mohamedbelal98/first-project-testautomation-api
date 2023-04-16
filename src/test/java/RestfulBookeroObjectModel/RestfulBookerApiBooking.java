package RestfulBookeroObjectModel;

import com.shaft.driver.SHAFT;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.simple.JSONObject;


public class RestfulBookerApiBooking {

    //variable
    private SHAFT.API apiObject;

    //Constructor
    public RestfulBookerApiBooking(SHAFT.API apiObject) {
        this.apiObject = apiObject;
    }

    /**
     * get book ids api request
     *
     * @return list of books as a response object
     **/
    public Response getBookIds() {

        return apiObject.get("booking").setContentType(ContentType.JSON).perform();
    }

    /**
     * get book api request
     *
     * @return specific book details
     **/
    public Response getBooking(String bookingId) {

        return apiObject.get("booking/" + bookingId).setContentType(ContentType.JSON).perform();
    }

    /**
     * create booking from json object function
     *
     * @return the response body from the book I created
     **/
    public Response createBooking(String firstname, String additionalneeds, String checkin, String checkout,
                                  int totalprice, boolean depositpaid, String lastname) {

        return apiObject.post("booking").
                setContentType(ContentType.JSON).
                setRequestBody(createBookingBody(firstname, additionalneeds, checkin, checkout, totalprice, depositpaid, lastname)).perform();
    }

    /**
     * delete book
     *
     * @return the response "created"
     **/
    public Response deleteBooking(String bookingId) {

        return apiObject.delete("booking/" + bookingId).
                setTargetStatusCode(201).perform();

    }

    //Json Object body to use in creating book
    private JSONObject createBookingBody(String firstname, String additionalneeds, String checkin, String checkout,
                                         int totalprice, boolean depositpaid, String lastname) {

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