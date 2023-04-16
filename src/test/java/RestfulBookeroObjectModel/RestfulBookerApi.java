package RestfulBookeroObjectModel;

import com.shaft.api.RestActions;
import com.shaft.driver.SHAFT;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.simple.JSONObject;

public class RestfulBookerApi {

    //variables
    private SHAFT.API apiObject;
    private static final String auth_serviceName = "auth";
    public static final String BASE_URL = "https://restful-booker.herokuapp.com/";

    //Constructor
    public RestfulBookerApi(SHAFT.API apiObject) {
        this.apiObject = apiObject;
    }

    /*
     * log in function with username and password
     * get token to add cookies to use
     *  */
    public void login(String userName, String password) {

        JSONObject authentication = new JSONObject();
        authentication.put("username", userName);
        authentication.put("password", password);

        Response createToken = apiObject.post(auth_serviceName).
                setContentType(ContentType.JSON).
                setRequestBody(authentication).
                perform();
        String token = RestActions.getResponseJSONValue(createToken, "token");
        apiObject.addHeader("cookie", "token=" + token);
    }
}
