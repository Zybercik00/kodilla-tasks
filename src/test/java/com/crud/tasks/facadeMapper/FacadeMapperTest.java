package com.crud.tasks.facadeMapper;

import com.crud.tasks.domain.*;
import com.crud.tasks.mapper.TrelloMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class FacadeMapperTest {

    private TrelloMapper trelloMapper = new TrelloMapper();

    @Test
    public void testMapToBoards() {
        // Given
        List<TrelloListDto> trelloListDtos = List.of(new TrelloListDto("1", "list", false));
        List<TrelloBoardDto> trelloBoardDtoList = List.of(new TrelloBoardDto("11", "board", trelloListDtos));

        // When
        List<TrelloBoard> result = trelloMapper.mapToBoards(trelloBoardDtoList);

        // Then
        Assertions.assertThat(result.size()).isEqualTo(1);
        Assertions.assertThat(result.get(0).getId()).isEqualTo("11");
        Assertions.assertThat(result.get(0).getName()).isEqualTo("board");
        Assertions.assertThat(result.get(0).getLists().size()).isEqualTo(1);
        Assertions.assertThat(result.get(0).getLists().get(0).getId()).isEqualTo("1");
        Assertions.assertThat(result.get(0).getLists().get(0).getName()).isEqualTo("list");
    }

    @Test
    public void testMapToList() {
        // Given
        List<TrelloListDto> trelloListDtos = List.of(new TrelloListDto("1", "list", false));
        List<TrelloList> trelloLists = List.of(new TrelloList("1", "list", false));

        // When
        List<TrelloList> result = trelloMapper.mapToList(trelloListDtos);

        // Then
        Assertions.assertThat(result.size()).isEqualTo(1);
        Assertions.assertThat(result.get(0).getId()).isEqualTo("1");
        Assertions.assertThat(result.get(0).getName()).isEqualTo("list");

    }

    @Test
    public void testMapToBoardsDto() {
        // Given
        List<TrelloList> trelloList = List.of(new TrelloList("33", "list33", false));
        List<TrelloBoard> trelloBoardList = List.of(new TrelloBoard("22", "board5", trelloList));

        // When
        List<TrelloBoardDto> result = trelloMapper.mapToBoardsDto(trelloBoardList);

        // Then
        Assertions.assertThat(result.size()).isEqualTo(1);
        Assertions.assertThat(result.get(0).getId()).isEqualTo("22");
        Assertions.assertThat(result.get(0).getName()).isEqualTo("board5");
        Assertions.assertThat(result.get(0).getLists().size()).isEqualTo(1);
        Assertions.assertThat(result.get(0).getLists().get(0).getId()).isEqualTo("33");
        Assertions.assertThat(result.get(0).getLists().get(0).getName()).isEqualTo("list33");
    }

    @Test
    public void testMapToListDto() {
        // Given
        List<TrelloList> trelloList = List.of(new TrelloList("77", "list77", false));
        List<TrelloListDto> trelloListDtos = List.of(new TrelloListDto("77", "list77",false));
        // When
        List<TrelloListDto> result = trelloMapper.mapToListDto(trelloList);
        // Then
        Assertions.assertThat(result.size()).isEqualTo(1);
        Assertions.assertThat(result.get(0).getId()).isEqualTo("77");
        Assertions.assertThat(result.get(0).getName()).isEqualTo("list77");
    }

    @Test
    public void testMapToCardDto() {
        // Given
        TrelloCard trelloCard = new TrelloCard("card", "description1", "pos", "id1");
        TrelloCardDto trelloCardDto =new TrelloCardDto("card", "description1", "pos", "id1");
        // When
        TrelloCardDto result = trelloMapper.mapToCardDto(trelloCard);
        // Then
        Assertions.assertThat(result.getName()).isEqualTo("card");
        Assertions.assertThat(result.getDescription()).isEqualTo("description1");
        Assertions.assertThat(result.getPos()).isEqualTo("pos");
        Assertions.assertThat(result.getListId()).isEqualTo("id1");
    }

    @Test
    public void testMapToCard() {
        // Given
        TrelloCard trelloCard = new TrelloCard("card1", "description2", "pos1", "id2");
        TrelloCardDto trelloCardDto = new TrelloCardDto("card1", "description2", "pos1", "id2");
        // When
        TrelloCard result = trelloMapper.mapToCard(trelloCardDto);
        // Then
        Assertions.assertThat(result.getName()).isEqualTo("card1");
        Assertions.assertThat(result.getDescription()).isEqualTo("description2");
        Assertions.assertThat(result.getPos()).isEqualTo("pos1");
        Assertions.assertThat(result.getListId()).isEqualTo("id2");
    }
}
