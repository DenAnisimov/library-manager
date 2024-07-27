package org.example.map;

import org.example.dto.BookGenreDTO;
import org.example.entity.BookGenre;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {BookGenreMapper.class, GenreMapper.class, AuthorMapper.class})
public interface BookGenreMapper {
    BookGenreMapper INSTANCE = Mappers.getMapper(BookGenreMapper.class);

    @Mapping(source = "books", target = "bookDTOS")
    @Mapping(source = "genres", target = "genreDTOS")
    BookGenreDTO mapToDTO(BookGenre bookGenre);

    @Mapping(source = "bookDTOS", target = "books")
    @Mapping(source = "genreDTOS", target = "genres")
    BookGenre mapToEntity(BookGenreDTO bookGenreDTO);
}
