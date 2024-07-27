package org.example.map;

import org.example.dto.BookDTO;
import org.example.entity.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(uses = {AuthorMapper.class})
public interface BookMapper {
    BookMapper INSTANCE = Mappers.getMapper(BookMapper.class);
    @Mapping(source = "authorDTO", target = "author")
    Book mapToEntity(BookDTO bookDTO);
    @Mapping(source = "author", target = "authorDTO")
    BookDTO mapToDTO(Book book);
    List<Book> mapToEntity(List<BookDTO> bookDTOList);
    List<BookDTO> mapToDTO(List<Book> bookList);
}
