package db;

import entity.Group;
import entity.Student;
import services.StringService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBManager {

    private static Statement statement;
    private static final String ID = "id";
    private static final String SURNAME = "surname";
    private static final String NAME = "name";
    private static final String DATE = "date";
    private static final String GROUP_NAME = "group";

    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/students_4?user=root&password=ynwa1892");
            statement = connection.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<Student> getAllStudents() {
        List<Student> result = new ArrayList<>();
        try {
            ResultSet resultSet = statement.executeQuery("select s.id, surname, name, date, g.group from student" +
                    " as s left join groupp as g on s.id_group = g.id" +
                    " where status = '1';");

            while (resultSet.next()) {
                Student student = new Student();
                student.setId(resultSet.getInt(ID));
                student.setSurname(resultSet.getString(SURNAME));
                student.setName(resultSet.getString(NAME));
                student.setDate(resultSet.getDate(DATE));

                Group group = new Group();
                group.setName(resultSet.getString(GROUP_NAME));
                student.setGroup(group);

                result.add(student);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static int getGroupId(String groupName) {
        int result = 0;
        try {
            ResultSet resultSet = statement.executeQuery(String.format("select id from groupp where groupp.group = '%s';", groupName));

            while (resultSet.next()) {
                return resultSet.getInt(ID);
            }

            statement.execute(String.format("insert into `groupp` (`group`) values ('%s');", groupName));
            resultSet = statement.executeQuery(String.format("select id from groupp where groupp.group = '%s';", groupName));

            while (resultSet.next()) {
                result = resultSet.getInt(ID);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void createStudent(String surname, String name, int group, String date) {
        try {
            statement.execute(String.format("INSERT INTO `student` (`surname`, `name`, `id_group`, `date`)" +
                    " VALUES ('%s', '%s', '%d', '%s');", surname, name, group, date));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void deleteStudents(String[] ids) {
        try {
            statement.execute(String.format("update `student` set `status` = `0` where id in (%s);",
                    StringService.convertIds(ids)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Student getStudentById(String id) {
        Student student = new Student();
        try {
            ResultSet resultSet = statement.executeQuery(String.format("select s.id, surname, name, date, g.group from student" +
                    " as s left join groupp as g on s.id_group = g.id" +
                    " where s.id = '%s';", id));

            while (resultSet.next()) {
                student.setId(resultSet.getInt(ID));
                student.setSurname(resultSet.getString(SURNAME));
                student.setName(resultSet.getString(NAME));
                student.setDate(resultSet.getDate(DATE));

                Group group = new Group();
                group.setName(resultSet.getString(GROUP_NAME));
                student.setGroup(group);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return student;
    }

    public static void modifyStudent(String studentId, String surname, String name, int groupId, String dateForDB) {
        try {
            statement.execute(String.format("UPDATE `student` SET `surname` = '%s', `name` = '%s'," +
                    "`id_group` = '%d', `date` = '%s' WHERE (`id` = '%s');", surname, name, groupId, dateForDB, studentId));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
