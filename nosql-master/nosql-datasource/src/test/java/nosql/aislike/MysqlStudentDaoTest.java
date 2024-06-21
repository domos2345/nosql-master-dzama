package nosql.aislike;

import nosql.aislike.entity.SimpleStudent;
import nosql.aislike.entity.Student;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MysqlStudentDaoTest {

    @org.junit.jupiter.api.Test
    void getSimpleStudents() {
        var studentDao = DaoFactory.INSTANCE.getStudentDao();
        List<SimpleStudent> simpleStudents = studentDao.getSimpleStudents();
        System.out.println(simpleStudents);
        assertNotNull(simpleStudents);
        assertFalse(simpleStudents.isEmpty());
    }

    @org.junit.jupiter.api.Test
    void getAll() {
        var studentDao = DaoFactory.INSTANCE.getStudentDao();
        List<Student> students = studentDao.getAll();
        System.out.println(students);
        assertNotNull(students);
        assertFalse(students.isEmpty());
    }
}