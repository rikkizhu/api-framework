package restassured.utils;

/**
 * @program: restassured.utils.FakerUtils
 * @description:
 * @author: zhuruiqi
 * @create: 2021-06-07 10:23
 **/
public class FakerUtils {
    public static String getTimeStamp() {
        return String.valueOf(System.currentTimeMillis());
    }
}
