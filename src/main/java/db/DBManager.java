package db;

import constants.Constants;
import entity.*;
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
    private static final String DURATION = "duration";
    private static final String TERM_NAME = "term";
    private static final String VALUE = "value";
    private static final String DISCIPLINE_NAME = "discipline";
    private static final String ROLE_NAME = "role";

    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(Constants.DB_ADDRESS);
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
            statement.execute(String.format("update `student` set `status` = '0' where id in (%s);",
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

    public static List<Term> getAllTerms() {
        List<Term> result = new ArrayList<>();
        try {
            ResultSet resultSet = statement.executeQuery("select id, term, duration from term " +
                    "where status = '1'");
            while (resultSet.next()) {
                Term term = new Term();
                term.setId(resultSet.getInt(ID));
                term.setDuration(resultSet.getString(DURATION));
                term.setName(resultSet.getString(TERM_NAME));

                result.add(term);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static List<Grade> getGradesByStudentAndTermIds(String studentId, String termId) {
        List<Grade> result = new ArrayList<>();
        try {
            ResultSet resultSet = statement.executeQuery(String.format("select d.id, d.discipline, g.value from grade as g " +
                    "left join term_discipline as td on g.term_discipline_id = td.id " +
                    "left join discipline as d on td.id_discipline = d.id " +
                    "where g.student_id = '%s' and td.id_term = '%s';", studentId, termId));

            while (resultSet.next()) {
                Grade grade = new Grade();
                grade.setValue(Integer.parseInt(resultSet.getString(VALUE)));

                Discipline discipline = new Discipline();
                discipline.setId(resultSet.getInt(ID));
                discipline.setName(resultSet.getString(DISCIPLINE_NAME));
                grade.setDiscipline(discipline);

                result.add(grade);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static List<Discipline> getDisciplinesByTermId(String termId) {
        List<Discipline> result = new ArrayList<>();
        try {
            ResultSet resultSet = statement.executeQuery(String.format("SELECT d.discipline " +
                    "FROM term_discipline as td " +
                    "left join discipline as d on td.id_discipline = d.id " +
                    "where td.id_term = '%s';", termId));

            while (resultSet.next()) {
                Discipline discipline = new Discipline();
                discipline.setName(resultSet.getString(DISCIPLINE_NAME));
                result.add(discipline);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static List<Role> getAllRoles() {
        List<Role> result = new ArrayList<>();
        try {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM role;");

            while (resultSet.next()) {
                Role role = new Role();
                role.setId(resultSet.getInt(ID));
                role.setName(resultSet.getString(ROLE_NAME));
                result.add(role);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static boolean isAuthorised(String login, String password, String roleId) {
        try {
            ResultSet resultSet = statement.executeQuery(String.format("select * from user_role as ur " +
                    "left join user as u on ur.id_user = u.id " +
                    "where user = '%s' and password = '%s' and id_role = '%s';", login, password, roleId));
            while (resultSet.next()) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
