package com.example;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;
public class DemoGenerator {
    public static void main(String[] args) throws Exception {
//        注意创建Schema,注意Version，否则读取不到之前的数据库数据
//step 1:
        Schema schema = new Schema(34, "charles.nocompany.greendao");
//step 2:
        addTest(schema);
        addTeacher(schema);
        addStudent(schema);

//step 3:
        new DaoGenerator().generateAll(schema,"./app/src/main/java-gen");
    }

    private static void addTest(Schema schema) {
        Entity testTable = schema.addEntity("TestTable");
        testTable.addIdProperty().primaryKey().autoincrement();
        testTable.addStringProperty("text").notNull();
        testTable.addStringProperty("comment");
        testTable.addDateProperty("date");
    }


    private static void addTeacher(Schema schema) {
        Entity teacherTable = schema.addEntity("Teacher");
        teacherTable.addIdProperty().primaryKey().autoincrement();
        teacherTable.addStringProperty("tecNumber");
        teacherTable.addStringProperty("name");
    }

    private static void addStudent(Schema schema) {
        Entity studentTable = schema.addEntity("Student");
        studentTable.addIdProperty().primaryKey().autoincrement();
        studentTable.addStringProperty("stuNum");
        studentTable.addStringProperty("name");
        studentTable.addStringProperty("sex");
        studentTable.addStringProperty("age");
    }
}

