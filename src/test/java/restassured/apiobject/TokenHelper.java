package restassured.apiobject;

import static io.restassured.RestAssured.given;

/**
 * @program: restassured.apiobject.TokenHelper
 * @description:
 * @author: zhuruiqi
 * @create: 2021-06-07 10:53
 **/
public class TokenHelper {
    public static String getAccessToken(){
        String accessToken = given().log().all()
                .when()
                .param("corpid", "wwc8a676fe2024f770")
                .param("corpsecret", "Ns-cXO_h7-O0p32rDpMR7Uah4vDRCXRHd3NbHJjZLBI")
                .get("https://qyapi.weixin.qq.com/cgi-bin/gettoken")
                .then().log().all()
                .extract().response().path("access_token");

        return accessToken;
    }
}
