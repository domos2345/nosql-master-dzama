package sk.upjs.nosql.mongozadanie.student;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nosql.aislike.entity.Studium;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MongoStudium {

    private Long id;
    private String zaciatokStudia;
    private String koniecStudia;
    private MongoStudijnyProgram studijnyProgram;

    public MongoStudium(Studium studium) {
        id = studium.getId();
        // change format of date for better interactions with mongo
        SimpleDateFormat inputFormat = new SimpleDateFormat("dd.MM.yyyy");
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            if (!studium.getZaciatokStudia().isEmpty()) {
                Date d = inputFormat.parse(studium.getZaciatokStudia());
                zaciatokStudia = outputFormat.format(d);
            }
            if (!studium.getKoniecStudia().isEmpty()) {
                Date d = inputFormat.parse(studium.getKoniecStudia());
                koniecStudia = outputFormat.format(d);
            }
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        studijnyProgram = new MongoStudijnyProgram(studium.getStudijnyProgram());
    }
}
