package restassured;

import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import restassured.apiobject.DepartmentObject;

import static org.testng.Assert.assertEquals;

/**
 * @program: restassured.Demo_05_filter
 * @description:
 * @author: zhuruiqi
 * @create: 2021-06-07 11:45
 **/

/**
 * 基础脚本，分别执行了，创建、修改、查询、删除接口并进行了校验
 * 1、进行了如下优化，方法间进行了解耦，每个方法都可以独立执行
 * 2、使用时间戳命名法避免入参重复造成的报错
 * 3、使用环境清理方案避免入参重复造成的报错。
 * 4、通过分层及filter的使用，极大地减少了重复代码，提高了代码的易读性和可维护性
 * 5、进行了优化：因为要覆盖不同的入参组合，以数据驱动的方式将入参数据从代码剥离。
 * 6、进行了优化：使用Junit5提供的Java 8 lambdas的断言方法，增加了脚本的容错性。
 * 7、进行了优化：增加并发场景
 */

public class Demo_08_thread_creatdepartment {
    private static final Logger logger = LoggerFactory.getLogger(Demo_04_repeat_evn_clear.class);


    @DisplayName("创建部门")
    @RepeatedTest(100)
    void createDepartment() {
        String creatName = "name1234567";
        String creatEnName = "en_name1234567";
        Response createResponse = DepartmentObject.createDepartment(creatName, creatEnName);
        assertEquals(createResponse.path("errcode").toString(), "0");

    }


}
