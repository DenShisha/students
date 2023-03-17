package db;

import entity.Group;
import entity.Student;

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
}
