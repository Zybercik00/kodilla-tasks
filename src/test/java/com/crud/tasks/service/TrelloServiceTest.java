package com.crud.tasks.service;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.domain.CreatedTrelloCardDto;
import com.crud.tasks.domain.Mail;
import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.domain.TrelloCardDto;
import com.crud.tasks.trello.client.TrelloClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TrelloServiceTest {

    @InjectMocks
    private TrelloService testee;

    @Mock
    private AdminConfig adminConfig;

    @Mock
    private TrelloClient trelloClient;

    @Mock
    private SimpleEmailService emailService;

    @Test
    public void shouldFetchTrelloBoards() {
        //Given
        when(trelloClient.getTrelloBoards()).thenReturn(List.of(new TrelloBoardDto("1", "board1", List.of())));

        //When
        List<TrelloBoardDto> result = testee.fetchTrelloBoards();

        //Then
        assertEquals(List.of(new TrelloBoardDto("1", "board1", List.of())), result);

    }

    @Test
    public void shouldCreateTrelloCard() {
        //Given
        TrelloCardDto trelloCardDto = new TrelloCardDto("card", "desc", "pos", "1");
        CreatedTrelloCardDto newCard = new CreatedTrelloCardDto("21", "card", "shortUrl");
        when(trelloClient.createNewCard(trelloCardDto)).thenReturn(newCard);
        when(adminConfig.getAdminMail()).thenReturn("mail@kodilla.com");

        //When
        CreatedTrelloCardDto result = testee.createTrelloCard(trelloCardDto);

        //Then
        assertEquals(newCard, result);
        verify(emailService).send(Mail.builder()
                .mailTo("mail@kodilla.com")
                .subject("Tasks: New Trello card")
                .message("New card: card has been created on your Trello account")
                .toCc(null)
                .build());
    }

    @Test
    public void shouldNotCreateTrelloCard() {
        //Given
        TrelloCardDto trelloCardDto = new TrelloCardDto("card", "desc", "pos", "1");

        when(trelloClient.createNewCard(trelloCardDto)).thenReturn(null);

        //When
        CreatedTrelloCardDto result = testee.createTrelloCard(trelloCardDto);

        //Then
        assertNull(result);
        verify(emailService, never()).send(Mail.builder()
                .mailTo("mail@kodilla.com")
                .subject("Tasks: New Trello card")
                .message("New card: card has been created on your Trello account")
                .toCc(null)
                .build());
    }


}
