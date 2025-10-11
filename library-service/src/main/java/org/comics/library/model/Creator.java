package org.comics.library.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity @Table(name = "creators")
@SQLDelete(sql = "UPDATE creators SET active = false WHERE creator_id = ?")
@Where(clause = "active = true")
public class Creator {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "creator_id")
    private Long creatorId;

    @NotBlank
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "primary_role")
    private String primaryRole;

    @JsonIgnore
    @Column(name = "active", nullable = false)
    private boolean active = true;

    @JsonIgnore
    @OneToMany(mappedBy = "creator", fetch = FetchType.LAZY)
    private List<ComicCreator> credits = new ArrayList<>();
}
