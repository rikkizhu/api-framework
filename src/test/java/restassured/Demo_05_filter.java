package restassured;

import io.restassured.response.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import restassured.apiobject.DepartmentObject;
import restassured.task.EnvHelperTask;

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
 */

public class Demo_05_filter {
    private static final Logger logger = LoggerFactory.getLogger(Demo_04_repeat_evn_clear.class);

    @AfterEach
    @BeforeEach
    void clearDepartment() {
        EnvHelperTask.clearDepartMentTask();
    }


    @DisplayName("创建部门")
    @Test
    void createDepartment(){
        Response createResponse = DepartmentObject.createDepartment("字部门01","zbm01");
        assertEquals(createResponse.path("errcode").toString(),"0");

    }
}
