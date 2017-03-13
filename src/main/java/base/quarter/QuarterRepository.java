package base.quarter;

import base.flowchart.Flowchart;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface QuarterRepository extends CrudRepository<Quarter, Long> {
    List<Quarter> findByFlowchart(Flowchart flowchart);
}
