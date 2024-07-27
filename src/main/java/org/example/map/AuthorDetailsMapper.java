package org.example.map;

import org.example.dto.AuthorDetailsDTO;
import org.example.entity.AuthorDetails;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(uses = AuthorDetailsMapper.class)
public interface AuthorDetailsMapper {
    AuthorDetailsMapper INSTANCE = Mappers.getMapper(AuthorDetailsMapper.class);
    @Mapping(source = "authorDTO", target = "author")
    AuthorDetails mapToEntity(AuthorDetailsDTO authorDetailsDTO);
    @Mapping(source = "author", target = "authorDTO")
    AuthorDetailsDTO mapToDTO(AuthorDetails authorDetails);
    List<AuthorDetails> mapToEntity(List<AuthorDetailsDTO> authorDetailsDTOList);
    List<AuthorDetailsDTO> mapToDTO(List<AuthorDetails> authorDetailsList);
}
