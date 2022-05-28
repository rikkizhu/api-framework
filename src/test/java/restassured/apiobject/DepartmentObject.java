package restassured.apiobject;

import io.restassured.response.Response;
import restassured.utils.FakerUtils;

import static io.restassured.RestAssured.given;

/**
 * @program: restassured.apiobject.DepartmentObject
 * @description:
 * @author: zhuruiqi
 * @create: 2021-06-07 10:48
 **/
public class DepartmentObject {
    public static Response createDepartmentFilter(String createName, String createEnName, String accessToken) {
        String name = "name" + FakerUtils.getTimeStamp();
        String enName = "en_name" + FakerUtils.getTimeStamp();

        String creatBody = "{\n" +
                "   \"name\": \"" + createName + "\",\n" +
                "   \"name_en\": \"" + createEnName + "\",\n" +
                "   \"parentid\": 1\n" +
                "}\n";

        return given().log().all()
                .contentType("application/json")
                .body(creatBody)
                .post("https://qyapi.weixin.qq.com/cgi-bin/department/create?access_token=" + accessToken + "")
                .then()
                .log().body()
                .extract()
                .response();

    }

    public static Response createDepartment(String createName, String createEnName) {

        String creatBody = "{\n" +
                "   \"name\": \"" + createName + "\",\n" +
                "   \"name_en\": \"" + createEnName + "\",\n" +
                "   \"parentid\": 1\n" +
                "}\n";

        return given().filter(new TokenFilter()).log().all()
                .contentType("application/json")
                .body(creatBody)
                .post("https://qyapi.weixin.qq.com/cgi-bin/department/create" )
                .then()
                .log().body()
                .extract()
                .response();

    }

    public static Response updateDepartMent(String updateName, String updateEnName, String department) {
        String creatBody = "{\n" +
                "   \"name\": \"" + updateName + "\",\n" +
                "   \"name_en\": \"" + updateEnName + "\",\n" +
                "   \"parentid\": 1}";
        Response updateResponse = given().filter(new TokenFilter()).log().all()
                .contentType("application/json")
                .body(creatBody)
                .post("https://qyapi.weixin.qq.com/cgi-bin/department/create")
                .then()
                .log().body()
                .extract()
                .response();
        return updateResponse;
    }

    public static Response listDepartMent(String departmentId) {
        Response listResponse = given().filter(new TokenFilter()).log().all()
                .when()
                .param("id", departmentId)
                .get("https://qyapi.weixin.qq.com/cgi-bin/department/list")
                .then()
                .log().body()
                .extract()
                .response();
        return listResponse;
    }

    public static Response deleteDepartment(String departmentId) {
        Response deleteResponse = given().filter(new TokenFilter()).log().all()
                .contentType("application/json")
                .param("id", departmentId)
                .get("https://qyapi.weixin.qq.com/cgi-bin/department/delete")
                .then()
                .log().body()
                .extract().response();
        return deleteResponse;
    }

}
