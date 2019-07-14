package wklab.Task1Rest.dto;

import lombok.Data;
import wklab.Task1Rest.domain.Birthday;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

@Data
@XmlRootElement
public class TeacherDto {
    @NotNull(message = "empty name")
    String name;
    @Valid
    Birthday birthday;
}
