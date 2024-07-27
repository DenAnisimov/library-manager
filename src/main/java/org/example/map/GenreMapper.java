package org.example.map;

import org.example.dto.GenreDTO;
import org.example.entity.Genre;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface GenreMapper {
    GenreMapper INSTANCE = Mappers.getMapper(GenreMapper.class);
    Genre mapToEntity(GenreDTO genreDTO);
    GenreDTO mapToDTO(Genre genre);
    List<Genre> mapToEntity(List<GenreDTO> genreDTOList);
    List<GenreDTO> mapToDTO(List<Genre> genreList);
}
