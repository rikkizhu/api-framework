package restassured.task;

import restassured.apiobject.DepartmentObject;

import java.util.ArrayList;

/**
 * @program: restassured.task.EnvHelperTask
 * @description:
 * @author: zhuruiqi
 * @create: 2021-06-07 11:49
 **/
public class EnvHelperTask {

    public static void clearDepartMentTask(){
        ArrayList<Integer> departmentIds = DepartmentObject.listDepartMent("1").path("department.id");
        for(int departmentId:departmentIds){
            if (1==departmentId)
                continue;
            DepartmentObject.deleteDepartment(departmentId+"");
        }
    }

}
