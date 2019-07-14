package wklab.Task1Rest.domain;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Data
public class Birthday {
    @Min(1)
    @Max(31)
    Long day;
    @Min(1)
    @Max(12)
    Long month;
    @Min(1900)
    Long year;
}
