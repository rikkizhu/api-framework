package restassured;

import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import restassured.utils.FakerUtils;

import java.util.ArrayList;

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
 * 1、进行了如下优化，方法间进行了解耦，每个方法都可以独立执行
 * 2、使用时间戳命名法避免入参重复造成的报错
 * 3、使用环境清理方案避免入参重复造成的报错。
 */

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class Demo_04_repeat_evn_clear {

    private static final Logger logger = LoggerFactory.getLogger(Demo_04_repeat_evn_clear.class);
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

    @AfterEach
    @BeforeEach
    void clearDepartment(){
        Response response = given().log().all()
                .when()
                .param("id", 1)
                .get("https://qyapi.weixin.qq.com/cgi-bin/department/list?access_token=" + accessToken)
                .then()
                .log().body()
                .extract()
                .response();

        ArrayList<Integer> departmentIdList = response.path("department.id");
        for (int departmentId : departmentIdList){
            if (1==departmentId){
                continue;
            }

            Response deleteResponse = given().log().all()
                    .contentType("application/json")
                    .param("access_token", accessToken)
                    .param("id", departmentId)
                    .get("https://qyapi.weixin.qq.com/cgi-bin/department/delete")
                    .then()
                    .log().body()
                    .extract().response();
        }
    }

    @DisplayName("创建部门")
    @Test
    @Order(1)
    void createDepartment() {
        String name = "name" + FakerUtils.getTimeStamp();
        String enName = "en_name" + FakerUtils.getTimeStamp();

        String creatBody = "{\n" +
                "   \"name\": \"" + name + "\",\n" +
                "   \"name_en\": \"" + enName + "\",\n" +
                "   \"parentid\": 1\n" +
                "}\n";


        final Response response = given().log().all()
                .contentType("application/json")
                .body(creatBody)
                .post("https://qyapi.weixin.qq.com/cgi-bin/department/create?access_token=" + accessToken + "")
                .then()
                .log().body()
                .extract()
                .response();

        departmentId = response.path("id").toString();
        logger.info("departmentId:" + departmentId);
    }

    @DisplayName("修改部门")
    @Test
    @Order(2)
    void updateDepartment() {

        String name = "name" + FakerUtils.getTimeStamp();
        String enName = "en_name" + FakerUtils.getTimeStamp();

        String creatBody = "{\n" +
                "   \"name\": \"" + name + "\",\n" +
                "   \"name_en\": \"" + enName + "\",\n" +
                "   \"parentid\": 1\n" +
                "}\n";

        final Response creatResponse = given().log().all()
                .contentType("application/json")
                .body(creatBody)
                .post("https://qyapi.weixin.qq.com/cgi-bin/department/create?access_token=" + accessToken + "")
                .then()
                .log().body()
                .extract()
                .response();

        departmentId = creatResponse.path("id").toString();
        logger.info("departmentId:" + departmentId);

        String body = "{\n" +
                "   \"id\": " + departmentId + ",\n" +
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
        assertEquals(response.path("errcode").toString(), "0");

    }

    @DisplayName("查询部门")
    @Test
    @Order(3)
    void listDepartment() {
        String name = "name" + FakerUtils.getTimeStamp();
        String enName = "en_name" + FakerUtils.getTimeStamp();

        String creatBody = "{\n" +
                "   \"name\": \"" + name + "\",\n" +
                "   \"name_en\": \"" + enName + "\",\n" +
                "   \"parentid\": 1\n" +
                "}\n";


        final Response creatResponse = given().log().all()
                .contentType("application/json")
                .body(creatBody)
                .post("https://qyapi.weixin.qq.com/cgi-bin/department/create?access_token=" + accessToken + "")
                .then()
                .log().body()
                .extract()
                .response();

        departmentId = creatResponse.path("id").toString();
        logger.info("departmentId:" + departmentId);

        Response response = given().log().all()
                .when()
                .param("id", departmentId)
                .get("https://qyapi.weixin.qq.com/cgi-bin/department/list?access_token=" + accessToken)
                .then()
                .log().body()
                .extract()
                .response();
        assertEquals(response.path("errcode").toString(), "0");
        assertEquals(response.path("department.id[0]").toString(), departmentId);

    }


    @DisplayName("删除部门")
    @Test
    @Order(4)
    void deleteDepartment() {
        String name = "name" + FakerUtils.getTimeStamp();
        String enName = "en_name" + FakerUtils.getTimeStamp();

        String creatBody = "{\n" +
                "   \"name\": \"" + name + "\",\n" +
                "   \"name_en\": \"" + enName + "\",\n" +
                "   \"parentid\": 1\n" +
                "}\n";

        final Response creatResponse = given().log().all()
                .contentType("application/json")
                .body(creatBody)
                .post("https://qyapi.weixin.qq.com/cgi-bin/department/create?access_token=" + accessToken + "")
                .then()
                .log().body()
                .extract()
                .response();

        departmentId = creatResponse.path("id").toString();
        logger.info("departmentId:" + departmentId);


        Response response = given().log().all()
                .contentType("application/json")
                .param("access_token", accessToken)
                .param("id", departmentId)
                .get("https://qyapi.weixin.qq.com/cgi-bin/department/delete")
                .then()
                .log().body()
                .extract().response();
        assertEquals(response.path("errcode").toString(), "0");

    }

}
