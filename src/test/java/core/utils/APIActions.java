package core.utils;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.Map;

/***
 * @author Sai Ram Prasath
 */
public class APIActions {
    private RequestSpecification requestSpecification;

    public APIActions(String baseUrl) {
        RestAssured.baseURI = baseUrl;
        requestSpecification = RestAssured.given();
    }


    public Response get(String uri, String parameters) {
        return requestSpecification.get(uri, parameters);
    }

    public Response get(String uri) {
        return requestSpecification.get(uri);
    }

    public Response get(String uri, Map<String, String> queryParameters) {
        return requestSpecification.queryParams(queryParameters).get(uri);
    }

}
