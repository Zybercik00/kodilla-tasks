package com.crud.tasks.service;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.domain.CreatedTrelloCard;
import com.crud.tasks.domain.Mail;
import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.domain.TrelloCardDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Optional.ofNullable;

@Service("Service")
@RequiredArgsConstructor
public class TrelloService {
    private final com.crud.tasks.trello.client.TrelloService trelloService;
    private final SimpleEmailService emailService;
    private static final String SUBJECT = "Tasks: New Trello card";

    private final AdminConfig adminConfig;

    public List<TrelloBoardDto> fetchTrelloBoards() {
        return trelloService.getTrelloBoards();
    }

    public CreatedTrelloCard createTrelloCard(final TrelloCardDto trelloCardDto) {
        CreatedTrelloCard newCard = trelloService.createNewCard(trelloCardDto);


        ofNullable(newCard).ifPresent(card -> emailService.send(Mail.builder()
                        .mailTo(adminConfig.getAdminMail())
                        .subject(SUBJECT)
                        .message("New card: " + trelloCardDto.getName() + " has been created on your Trello account")
                        .toCc(null)
                        .build()));

        return newCard;
    }
}
