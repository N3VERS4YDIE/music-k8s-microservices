package com.n3vers4ydie.deletesong.model;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "songs")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Song {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String artist;
    @Column(name = "release_date")
    private LocalDate releaseDate;
    private String album;
    @Column(columnDefinition = "TEXT")
    private String lyrics;
    @ElementCollection
    @CollectionTable(name = "song_urls", joinColumns = @JoinColumn(name = "song_id"))
    @Column(name = "url")
    private List<String> listenUrls;
}
