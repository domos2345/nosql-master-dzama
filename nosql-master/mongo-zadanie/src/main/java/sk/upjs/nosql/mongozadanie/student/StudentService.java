package sk.upjs.nosql.mongozadanie.student;

import nosql.aislike.DaoFactory;
import nosql.aislike.StudentDao;
import nosql.aislike.entity.Student;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.aggregation.UnwindOperation;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.group;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.project;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.unwind;

@Service
public class StudentService {

    private final StudentDao studentDao = DaoFactory.INSTANCE.getStudentDao();
    private final StudentRepository repository;

    private final MongoTemplate template;

    public StudentService(StudentRepository repository, MongoTemplate template) {
        this.repository = repository;
        this.template = template;
    }

    public int insertAllStudents() {
        List<Student> students = studentDao.getAll();
        List<MongoStudent> cassandraStudents = students.stream().map(MongoStudent::new).toList();
        repository.saveAll(cassandraStudents);
        return students.size();
    }

    public List<MongoStudent> getAll() {
        List<MongoStudent> students = new ArrayList<>();
        repository.findAll().forEach(students::add);
        return students;
    }

    public MongoStudent getById(long id) {
        return repository.findById(id).orElse(null);
    }

    public void deleteById(long id) {
        repository.deleteById(id);
    }
    public void deleteAllStudents() {
        repository.deleteAll();
    }

    public void printAllStudents() {
        repository.findAll().forEach(System.out::println);
    }

    public List<MongoStudent> getByTitul(String titul) {
        long start = System.currentTimeMillis();
        List<MongoStudent> students = repository.findAllBySkratkaAkadTitul(titul);
        long end = System.currentTimeMillis();
        System.out.println("time of query: " + (end - start) + " millis");
        return students;

    }

    public List<MongoStudent> getByStudProgAndAkademicYear(String skratka, String akademickyRok) {
        long start = System.currentTimeMillis();
        List<MongoStudent> students = repository.findAllByStudijnyProgramSkratkaAndAkademickyRok(skratka, akademickyRok);
        long end = System.currentTimeMillis();
        System.out.println("time of query: " + (end - start) + " millis");
        return students;
    }


    public void printTable() {
        Aggregation aggregation = Aggregation.newAggregation(
                unwind("studium"),
                project()
                        .andExpression("studium.studijnyProgram.skratka").as("skratka")
                        .andExpression("studium.zaciatokStudia").as("rok"),
                group("skratka", "rok").count().as("count"),
                project("count")
                        .andExpression("_id.skratka").as("skratka")
                        .andExpression("_id.rok").as("rok")
                        .andExclude("_id")
        );

        List<AggregationResult> result = template.aggregate(aggregation, "mongoStudent", AggregationResult.class).getMappedResults();

        // Print table header
        System.out.printf("%-15s %-10s %-5s%n", "Program", "Year", "Count");
        System.out.println("-------------------------------------");

        // Print each result in a formatted manner
        for (AggregationResult r : result) {
            System.out.printf("%-15s %-10s %-5d%n", r.getSkratka(), r.getRok(), r.getCount());
        }
    }

    public static class AggregationResult {
        private String skratka;
        private String rok;
        private int count;

        public String getSkratka() {
            return skratka;
        }

        public String getRok() {
            return rok;
        }

        public int getCount() {
            return count;
        }

        @Override
        public String toString() {
            return String.format("AggregationResult{skratka='%s', rok='%s', count=%d}", skratka, rok, count);
        }
    }


}
