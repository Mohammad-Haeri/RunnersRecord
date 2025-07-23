package ir.Mohammad_Haeri.RunnersRecord.run;

import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

public interface IRunRecordRepository extends ListCrudRepository<Run, Integer> {

    List<Run> findAllByLocation(Location location);

    List<Run> findAllByScoreGreaterThan(int scoreIsGreaterThan);

    List<Run> findAllByScoreBetween(int scoreAfter, int scoreBefore);

    List<Run> findAllByScoreIsLessThan(int scoreIsLessThan);


}
