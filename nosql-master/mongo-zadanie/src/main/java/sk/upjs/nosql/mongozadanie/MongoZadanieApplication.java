package sk.upjs.nosql.mongozadanie;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import sk.upjs.nosql.mongozadanie.student.MongoStudent;
import sk.upjs.nosql.mongozadanie.student.StudentService;

import java.util.List;

@SpringBootApplication
public class MongoZadanieApplication {

    private static final Logger logger = LoggerFactory.getLogger(MongoZadanieApplication.class);

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(MongoZadanieApplication.class, args);
        StudentService service = context.getBean(StudentService.class);

        testAllStudents(service);
        testOneStudent(service);
        testCustomMethods(service);
    }


    private static void testAllStudents(StudentService service) {
        service.deleteAllStudents();
        List<MongoStudent> allStudents = service.getAll();
        if (allStudents.size() != 0)
            logger.error("V DB sa stale nachadzaju studenti");

        int inserted = service.insertAllStudents();
        allStudents = service.getAll();
        if (allStudents.size() != inserted)
            logger.error("Neboli ulozeni vsetci studenti");

        logger.info("\n\tVsetci studenti:");
        service.printAllStudents();
    }

    private static void testOneStudent(StudentService service) {
        long id = 1006262;
        MongoStudent student = service.getById(id);
        if (!"Kugujalu".equals(student.getMeno()) || !"Najahalovu".equals(student.getPriezvisko()))
            logger.error("ID doesn't match with expected student");

        service.deleteById(id);

        student = service.getById(id);
        if (student != null)
            logger.error("Student does not equal null after delete, thus they were not deleted");


    }

    private static void testCustomMethods(StudentService service) {
        logger.info("\n\tGetBy akademicky titul (meno a priezvisko):");
        // mena a priezviska podla titulu
        service.getByTitul("RNDr.").forEach(
                student -> System.out.println(student.toString())
        );

        // studenti podla studijneho programu a akademickeho roku
        // pred indexom 21.2 milisekund
        // po indexe 17.4 milisekund
        logger.info("\n\tGetBy studijny program a akademicky rok:");

        service.getByStudProgAndAkademicYear("F", "1996-09-01").forEach(
                student -> System.out.println(student.toString())
        );
        logger.info("\n\tTabulka pocty studentov podla rokov a studijnych programov:");
        // vypisanie tabulky: pocty studentov podla rokov a studijnych programov
        service.printTable();
    }
}
