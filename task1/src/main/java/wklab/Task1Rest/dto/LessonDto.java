package wklab.Task1Rest.dto;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

@Data
@XmlRootElement
public class LessonDto {
    @NotNull(message = "empty name")
    String name;
    @Min(45)
    @Max(360)
    Long duration;
    @NotNull(message = "empty teacher id")
    Long teacherId;
}
