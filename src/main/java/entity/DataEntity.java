package entity;

import lombok.Data;
import java.util.List;

@Data
public class DataEntity {

    private Long id;
    private Meta meta;
    private List<Candidate> candidates;

}
