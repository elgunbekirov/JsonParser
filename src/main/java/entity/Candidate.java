
package entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Candidate {

    private Long id;
    private String candidateName;
    private Long extraTime;

}
