package com.yulu.common.test;

import com.google.common.collect.Lists;
import com.yulu.common.util.ExcelUtils;
import com.yulu.common.util.FileUtils;
import com.yulu.common.util.UserData;

import java.util.List;

/**
 * Created by Yulu on 2020/4/24.
 */
public class FileUtilsTest {
    public static void main(String[] args) {
        ExcelUtils excelUtils = new ExcelUtils();
        List<UserData> dataList = Lists.newArrayList();
        UserData userData1 = new UserData();
        userData1.setId("1");
        userData1.setName("Yulu1");
        userData1.setAge(10);
        dataList.add(userData1);
        UserData userData2 = new UserData();
        userData2.setId("2");
        userData2.setName("Yulu2");
        userData2.setAge(20);
        dataList.add(userData2);
        byte[] bytes = excelUtils.buildUserReportExcel(dataList);
        FileUtils.getFileByBytes(bytes, "E:\\report\\", "User_Report.xlsx");
    }
}
