package serenity.spartan;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import net.serenitybdd.junit5.SerenityTest;
import net.serenitybdd.rest.Ensure;
import org.junit.jupiter.api.*;
import serenity.utility.SpartanUtil;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import static net.serenitybdd.rest.SerenityRest.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.lessThan;

@SerenityTest
@Disabled
public class SimpleSpartanTest {
    @BeforeAll
    public static void setUp() {
        RestAssured.baseURI = "http://54.90.101.103:8000";
        RestAssured.basePath = "/api";
    }
    @AfterAll
    public static void cleanUp(){
        reset();
    }
    @DisplayName("Testing GET /api/hello Endpoint")
    @Test
    public void testingHelloEndPoint(){
        when()
                .get("/hello");
                //then()
                //.statusCode(200)
                //.contentType(ContentType.TEXT)
                //.body( is("Hello from Sparta") )
        ;
        // serenity way
        Ensure.that("make sure endpoint works",
                response -> response
                        .statusCode(200)
                        .contentType(ContentType.TEXT)
                        .body( is("Hello from Sparta") )
        );
        Ensure.that("Success response was received",
                thenResponse -> thenResponse.statusCode(200) )
                .andThat("I got text response" ,
                        blaResponse -> blaResponse.contentType(ContentType.TEXT) )
                .andThat("I got Hello from Sparta" ,
                        vResponse -> vResponse.body( is("Hello from Sparta") ) )
                //.andThat("I got my response within 2 seconds",
                  //      vResponse -> vResponse.time( lessThan(2L), TimeUnit.SECONDS  ) )
        ;
    }
    @DisplayName("Admin User should be able to add spartan")
    @Test
    public void testAdd1DAta(){
        Map<String,Object> payload = SpartanUtil.getRandomSpartanRequestPayload();

        given()
                .log().all()
                .auth().basic("admin", "admin")
                .contentType(ContentType.JSON)
                .body(payload).
        when()
                .post("/spartans");

        Ensure.that("request was successful",
                thenResponse -> thenResponse
                        .statusCode(201))
        .andThat("we got json format result",
                thenRespaonse -> thenRespaonse
                        .contentType(ContentType.JSON))
        .andThat("success message is A spartan is born!",
                thenResponse -> thenResponse
                       .body("success", is ("A Spartan is Born!"))
        );

        Ensure.that("The data <"+payload+"> we provided added correctly",
                vRes -> vRes.body("data.name", is( payload.get("name")  ) )
                        .body("data.gender", is( payload.get("gender")  ) )
                        .body("data.phone", is( payload.get("phone")  ) ) )
                .andThat("New ID has been generated and not null" ,
                        vRes -> vRes.body("data.id" , is(not(nullValue() )))    ) ;
        // how do we extract information after sending requests ? :
        // for example I want to print out ID
        // lastResponse() method is coming SerenityRest class
        // and return the Response Object obtained from last ran request.
//        lastResponse().prettyPeek();


        lastResponse().prettyPeek();
        System.out.println(lastResponse().jsonPath().getInt("data.id"));
        System.out.println(lastResponse().path("data.id").toString());

    }
}