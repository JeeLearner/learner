package com.jee.poi.excel.demo.export;

/**
 * excel导入导出 简单实体demo
 *
 * @author jeeLearner
 * @date 2019/6/30
 */
public class SimpleDemo {

    private String name;
    private String deptName;
    private int age;

    public SimpleDemo() {
    }

    public SimpleDemo(String name, String deptName, int age) {
        this.name = name;
        this.deptName = deptName;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }
}

