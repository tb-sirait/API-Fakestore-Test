import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.List;
import java.util.Map;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import static jdk.internal.org.jline.utils.Colors.s;

public class AppConfigAPI {
    static {
        // Base URI untuk API yang akan diuji
        RestAssured.baseURI = "https://fakestoreapi.com";
    }

    // Mendapatkan semua cart
    public static Response getAllCarts() {
        return RestAssured
                .given()
                .get("/carts");
    }

    // Mendapatkan cart berdasarkan ID
    public static Response getSingleCart(int cartId) {
        return RestAssured
                .given()
                .get("/carts/" + cartId);
    }

    // Mendapatkan cart dengan hasil terbatas
    public static Response getLimitedCarts(int limit) {
        return RestAssured
                .given()
                .queryParam("limit", limit)
                .get("/carts");
    }

    // Mendapatkan cart dengan sorting
    public static Response getSortedCarts(String sortOrder) {
        return RestAssured
                .given()
                .queryParam("sort", sortOrder)
                .get("/carts");
    }

    // Mendapatkan cart dalam rentang tanggal
    public static Response getCartsInDateRange(String startDate, String endDate) {
        return RestAssured
                .given()
                .queryParam("startdate", startDate)
                .queryParam("enddate", endDate)
                .get("/carts");
    }

    public static Response getUserCart(int userId) {
        return RestAssured.given()
                .basePath("/carts/user/{userId}")
                .pathParam("userId", userId)
                .get();
    }

    public static Response addNewCart(int userId, String date, String productsJson) {
        return RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(String.format("{\"userId\":%d,\"date\":\"%s\",\"products\":%s}", userId, date, productsJson))
                .post("/carts");
    }
}
