package sk.upjs.nosql.mongozadanie.student;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nosql.aislike.entity.Student;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document
public class MongoStudent {

    @Id
    private Long id;
    private String meno;
    private String priezvisko;
    private char kodPohlavie;
    private String skratkaAkadTitul;
    private List<MongoStudium> studium = new ArrayList<>();

    public MongoStudent(Student student) {
        this.id = student.getId();
        this.meno = student.getMeno();
        this.priezvisko = student.getPriezvisko();
        this.kodPohlavie = student.getKodPohlavie();
        this.skratkaAkadTitul = student.getSkratkaAkadTitul();
        this.studium = student.getStudium().stream().map(MongoStudium::new).toList();
    }
}
