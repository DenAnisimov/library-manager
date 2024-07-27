package org.example.map;

import org.example.dto.AuthorDTO;
import org.example.entity.Author;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(uses = {BookMapper.class})
public interface AuthorMapper {
    AuthorMapper INSTANCE = Mappers.getMapper(AuthorMapper.class);
    Author mapToEntity(AuthorDTO authorDTO);
    AuthorDTO mapToDTO(Author author);
    List<Author> mapToEntity(List<AuthorDTO> authorDTOList);
    List<AuthorDTO> mapToDTO(List<Author> authorList);
}
