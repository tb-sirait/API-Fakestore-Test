import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class APITest {

    @Test
    public void testGetAllCarts() {
        Response response = AppConfigAPI.getAllCarts();
        Assert.assertEquals(response.getStatusCode(), 200);
        System.out.println("Response for Get All Carts: " + response.getBody().asString());
    }

    @Test
    public void testGetSingleCart() {
        int cartId = 5; // ID cart yang akan diuji
        Response response = AppConfigAPI.getSingleCart(cartId);
        Assert.assertEquals(response.getStatusCode(), 200);
        System.out.println("Response for Get Single Cart: " + response.getBody().asString());
    }

    @Test
    public void testGetLimitedCarts() {
        int limit = 5; // Jumlah data yang ingin dibatasi
        Response response = AppConfigAPI.getLimitedCarts(limit);
        Assert.assertEquals(response.getStatusCode(), 200);
        System.out.println("Response for Get Limited Carts: " + response.getBody().asString());
    }

    @Test
    public void testGetSortedCarts() {
        String sortOrder = "desc"; // Urutan data descending
        Response response = AppConfigAPI.getSortedCarts(sortOrder);
        Assert.assertEquals(response.getStatusCode(), 200);
        System.out.println("Response for Get Sorted Carts: " + response.getBody().asString());
    }

    @Test
    public void testGetCartsInDateRange() {
        String startDate = "2019-12-10";
        String endDate = "2020-10-10";
        Response response = AppConfigAPI.getCartsInDateRange(startDate, endDate);
        Assert.assertEquals(response.getStatusCode(), 200);
        System.out.println("Response for Get Carts in Date Range: " + response.getBody().asString());
    }

    @Test
    public void testGetUserCart() {
        Response response = AppConfigAPI.getUserCart(3);
        System.out.println(response.asString());
        Assert.assertEquals(200, response.getStatusCode(), "Expected status code 200");
        // Get the list of carts
        List<Map<String, Object>> carts = response.jsonPath().getList("");

        // Calculate total price
        double totalPrice = 0.0;
        for (Map<String, Object> cart : carts) {
            List<Map<String, Object>> products = (List<Map<String, Object>>) cart.get("products");
            for (Map<String, Object> product : products) {
                int quantity = (int) product.get("quantity");
                // Assuming you have a method to get the price of a product by its ID
                double price = getProductPriceById((int) product.get("productId"));
                totalPrice += price * quantity;
            }
        }

        // Assert that totalPrice is calculated correctly
        Assert.assertTrue(totalPrice > 0, "Total price should be greater than 0");
    }

    // Mock method to get product price by ID
    private double getProductPriceById(int productId) {
        // Replace this with actual logic to get the product price
        switch (productId) {
            case 1: return 10.0; // Example price
            case 7: return 15.0; // Example price
            case 8: return 20.0; // Example price
            default: return 0.0; // Default price if not found
        }
    }

    @Test
    public void testAddNewCart() {
        int userId = 5;
        String date = "2020-02-03";
        String productsJson = "[{\"productId\":5,\"quantity\":1},{\"productId\":1,\"quantity\":5}]";
        Response response = AppConfigAPI.addNewCart(userId, date, productsJson);
        Assert.assertEquals(response.getStatusCode(), 200);
        System.out.println("Response for Add New Cart: " + response.getBody().asString());
    }
}
