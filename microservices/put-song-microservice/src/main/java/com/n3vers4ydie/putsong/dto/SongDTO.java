package com.n3vers4ydie.putsong.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SongDTO {
    private Long id;
    private String name;
    private String artist;
    private LocalDate releaseDate;
    private String album;
    private String lyrics;
    private List<String> listenUrls;
}
