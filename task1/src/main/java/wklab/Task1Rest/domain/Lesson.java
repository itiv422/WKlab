package wklab.Task1Rest.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Lesson {
    String name;
    Long duration;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "teacher_id", nullable = false)
    Teacher teacher;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
}
