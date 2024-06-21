package sk.upjs.nosql.mongozadanie.student;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nosql.aislike.entity.StudijnyProgram;
import org.springframework.data.mongodb.core.index.Indexed;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MongoStudijnyProgram {

    private Long id;

    @Indexed(name = "studijnyProgramSkratka")
    private String skratka;
    private String popis;

    public MongoStudijnyProgram(StudijnyProgram s) {
        id = s.getId();
        skratka = s.getSkratka();
        popis = s.getPopis();
    }
}
