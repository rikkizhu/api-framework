package restassured;

import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

/**
 * @program: restassured.Demo_01_base
 * @description:
 * @author: zhuruiqi
 * @create: 2021-06-05 14:49
 **/

/**
 * 基础脚本，分别执行了，创建、修改、查询、删除接口并进行了校验
 */

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class Demo_01_base {

    private static final Logger logger = LoggerFactory.getLogger(Demo_01_base.class);
    static String accessToken;
    static String departmentId;

    @BeforeAll
    public static void getAccessToken() {
        accessToken = given().log().all()
                .when()
                .param("corpid", "wwc8a676fe2024f770")
                .param("corpsecret", "Ns-cXO_h7-O0p32rDpMR7Uah4vDRCXRHd3NbHJjZLBI")
                .get("https://qyapi.weixin.qq.com/cgi-bin/gettoken")
                .then().log().all()
                .extract().response().path("access_token");

        logger.info(accessToken);
        System.out.println("*************************************8");
    }

    @DisplayName("创建部门")
    @Test
    @Order(1)
    void createDepartment() {
        String body = "{\n" +
                "   \"name\": \"子部门01\",\n" +
                "   \"name_en\": \"child01\",\n" +
                "   \"parentid\": 1\n" +
                "}\n";


        final Response response = given().log().all()
                .contentType("application/json")
                .body(body)
                .post("https://qyapi.weixin.qq.com/cgi-bin/department/create?access_token=" + accessToken + "")
                .then()
                .log().body()
                .extract()
                .response();

        departmentId=response.path("id").toString();
        logger.info("departmentId:" + departmentId);
    }

    @DisplayName("修改部门")
    @Test
    @Order(2)
    void updateDepartment() {

        String body ="{\n" +
                "   \"id\": "+departmentId+",\n" +
                "   \"name\": \"子部门01_updated\",\n" +
                "   \"name_en\": \"child01_updated\",\n" +
                "   \"order\": 1\n" +
                "}\n";
        Response response = given().log().all()
                .contentType("application/json")
                .body(body)
                .post("https://qyapi.weixin.qq.com/cgi-bin/department/update?access_token=" + accessToken + "")
                .then()
                .log().body()
                .extract().response();
        assertEquals(response.path("errcode").toString(),"0");

    }

    @DisplayName("查询部门")
    @Test
    @Order(3)
    void listDepartment() {
        Response response =given().log().all()
                .when()
                .param("id",departmentId)
                .get("https://qyapi.weixin.qq.com/cgi-bin/department/list?access_token="+accessToken)
                .then()
                .log().body()
                .extract()
                .response();
        assertEquals(response.path("errcode").toString(),"0");
        assertEquals(response.path("department.id[0]").toString(),departmentId);

    }


    @DisplayName("删除部门")
    @Test
    @Order(4)
    void deleteDepartment() {


        Response response = given().log().all()
                .contentType("application/json")
                .param("access_token",accessToken)
                .param("id",departmentId)
                .get("https://qyapi.weixin.qq.com/cgi-bin/department/delete")
                .then()
                .log().body()
                .extract().response()
                ;
        assertEquals(response.path("errcode").toString(),"0");

    }

}
