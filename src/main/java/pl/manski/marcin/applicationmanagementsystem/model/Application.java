package pl.manski.marcin.applicationmanagementsystem.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String content;
    @Enumerated(EnumType.STRING)
    private State state = State.CREATED;
    private String rejectionReason;
    private Long publicationId;
}
