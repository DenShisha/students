package controllers;

import db.DBManager;
import entity.Discipline;
import entity.Grade;
import entity.Student;
import entity.Term;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "StudentProgressController", urlPatterns = "/student_progress")
public class StudentProgressController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String studentId = req.getParameter("idForProgress");
        String termId = req.getParameter("selectedTermId");

        Student student = DBManager.getStudentById(studentId);
        List<Term> terms = DBManager.getAllTerms();

        Term selectedTerm = new Term();
        if (termId == null) {
            selectedTerm = terms.get(0);
        } else {
            for (Term term : terms) {
                if (termId.equals(String.valueOf(term.getId()))) {
                    selectedTerm = term;
                }
            }
        }

        List<Grade> grades = DBManager.getGradesByStudentAndTermIds(studentId, String.valueOf(selectedTerm.getId()));

        int sum = 0;
        double averageGrade = 0;

        for (Grade grade : grades) {
            sum += grade.getValue();
        }

        if (!grades.isEmpty()) {
            averageGrade = sum / (double) grades.size();
        }

        if (grades.isEmpty()) {
            List<Discipline> disciplines = DBManager.getDisciplinesByTermId(String.valueOf(selectedTerm.getId()));
            for (Discipline discipline : disciplines) {
                Grade grade = new Grade();
                grade.setValue(-1);
                grade.setDiscipline(discipline);
                grades.add(grade);
            }
        }

        req.setAttribute("averageGrade", averageGrade);
        req.setAttribute("student", student);
        req.setAttribute("terms", terms);
        req.setAttribute("selectedTerm", selectedTerm);
        req.setAttribute("grades", grades);
        req.getRequestDispatcher("WEB-INF/jsp/student_progress.jsp").forward(req, resp);
    }
}
