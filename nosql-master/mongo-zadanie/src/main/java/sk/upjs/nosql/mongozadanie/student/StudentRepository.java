package sk.upjs.nosql.mongozadanie.student;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends CrudRepository<MongoStudent, Long> {

    // custom queries
     @Query(value = "{ 'skratkaAkadTitul' : ?0 }", fields = "{ '_id': 0, 'meno' : 1, 'priezvisko' : 1}")
    List<MongoStudent> findAllBySkratkaAkadTitul(String skratkaAkadTitul);

    @Query(value = "{studium: {$elemMatch: {'studijnyProgram.skratka': ?0, zaciatokStudia: {$lte : ?1}, koniecStudia: {$gt: ?1}}}}")
    List<MongoStudent> findAllByStudijnyProgramSkratkaAndAkademickyRok(String skratka, String akademickyRok);
}
