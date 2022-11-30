package com.myApp;

import com.myApp.db.model.User;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.hibernate.AssertionFailure;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Objects;

import static com.myApp.containerts.AllContainers.postgreSQL;

@SpringBootTest
class ApplicationTest {

    @BeforeAll
    public static void before() {
        postgreSQL.start();
        Application.main(new String[]{});
    }

    @Test
    @DisplayName("Проверка метода GET /users")
    void getAllUsersTest() {
        createUser("Jack", 18);
        List<User> user = getAllUsers();
        Assertions.assertEquals("Jack", user.get(0).getName(), "Имя не соответствует");
    }

    @Test
    @DisplayName("Проверка метода DELETE /users/{id}")
    void deleteUserTest() {
        createUser("Jack", 18);
        deleteUser(1, 204);
        getUser(1, 404);
    }

    @Test
    @DisplayName("Проверка метода GET /users/{id}")
    void getUserTest() {
        createUser("Jack", 18);
        User user = getUser(1, 200);
        Assertions.assertEquals("Jack", Objects.requireNonNull(user,
                        "Объект пришел null").getName(),
                "Имя не соответствует");
    }

    private List<User> getAllUsers() {
        return RestAssured.given()
                .contentType(ContentType.JSON)
                .get("http://localhost:8080/users").then()
                .statusCode(200).log().all()
                .extract().jsonPath().getList(".", User.class);
    }

    private User getUser(int id, int statusCode) {
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .get("http://localhost:8080/users/%s".formatted(id));
        if (response.statusCode() == 200) {
            if (response.statusCode() == statusCode) {
                return response.then().statusCode(statusCode).extract().as(User.class);
            } else {
                throw new AssertionFailure("Expected status code %s, but was %s".formatted(statusCode, response.statusCode()));
            }
        } else {
            response.then().statusCode(statusCode);
            return null;
        }
    }

    private void deleteUser(int id, int statusCode) {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .delete("http://localhost:8080/users/%s".formatted(id)).then()
                .statusCode(statusCode).log().all();
    }

    private void createUser(String name, int age) {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(User.builder()
                        .name(name)
                        .age(age)
                        .build())
                .post("http://localhost:8080/users")
                .then().statusCode(200).log().all();
    }
}
