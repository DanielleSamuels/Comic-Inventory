package org.comics.library.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Getter @Setter
@Entity @Table(name = "comic_creators")
public class ComicCreator {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comic_creator_id")
    private Long comicCreatorId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "comic_id", nullable = false)
    private Comic comic;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "creator_id", nullable = false)
    private Creator creator;

    @NotBlank
    @Column(name = "role_on_issue", nullable = false)
    private String role;
}
