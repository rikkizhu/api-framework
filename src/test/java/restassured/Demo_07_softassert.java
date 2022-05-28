package restassured;

import io.qameta.allure.*;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import restassured.apiobject.DepartmentObject;
import restassured.task.EnvHelperTask;
import restassured.utils.FakerUtils;

import static org.junit.jupiter.api.Assertions.assertAll;
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
 */

@Epic("企业微信接口测试用例")
@Feature("部门相关功能测试用例")
public class Demo_07_softassert {
    private static final Logger logger = LoggerFactory.getLogger(Demo_04_repeat_evn_clear.class);

    @AfterEach
    @BeforeEach
    void clearDepartment() {
        EnvHelperTask.clearDepartMentTask();
    }

    @Story("创建部门测试")
    @DisplayName("创建部门")
    @Description("这个测试方法会测试创建部门的功能")
    @Severity(SeverityLevel.BLOCKER)
    @ParameterizedTest
    @CsvFileSource(resources = "/data/createDepartment.csv", numLinesToSkip = 1)
    void createDepartment(String createName, String createEnName, String returnCode) {
        Response createResponse = DepartmentObject.createDepartment(createName, createEnName);
        assertEquals(createResponse.path("errcode").toString(), returnCode);

    }

    @Story("查询部门测试")
    @DisplayName("查询部门")
    @Description("这个测试方法会测试查询部门的功能")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    void listDepartment() {
        String name = "name" + FakerUtils.getTimeStamp();
        String enName = "en_name" + FakerUtils.getTimeStamp();

        String departmentId = DepartmentObject.createDepartment(name, enName).path("id").toString();
        Response listResponse = DepartmentObject.listDepartMent(departmentId);

        assertAll("返回值校验！",
                () -> assertEquals(departmentId + "x", listResponse.path("department.id[0]").toString()),
                () -> assertEquals(name + "x", listResponse.path("department.name[0]").toString()),
                () -> assertEquals(enName + "x", listResponse.path("department.name_en[0]").toString())
        );

    }
}
