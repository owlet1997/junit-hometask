package com.haulmont.task;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

@RunWith(MockitoJUnitRunner.class)
public class MailServiceTest {

    @Mock
    MailSender mailSender;

    @Mock
    MessageRepository messageRepository;

    MailService mailService;

    Person fromPerson;
    Person toPerson;

    Message message;

    @Before
    public void setUp(){
        mailService = new MailService(mailSender, messageRepository);
        setupUsers();
        setupMessage();
    }

    private void setupUsers() {
        fromPerson = new Person();
        fromPerson.setEmail("fromEmail@ya.ru");
        toPerson = new Person();
        toPerson.setEmail("toEmail@ya.ru");
    }

    private void setupMessage() {
        message = new Message("Subject", fromPerson, toPerson, "Message text");
        message.setStatus(Status.NEW);
    }

    @Test
    public void sendMessagePositive() {
        Mockito.when(mailSender
                .sendMessage(anyString(), anyString(), anyString(), anyString()))
                .thenReturn(true);
        boolean result = mailService.sendMessage(message);

        assertTrue(result);
        assertEquals(message.getStatus(), Status.DELIVERED);
    }

    @Test
    public void sendMessageNegative() {
        Mockito.when(mailSender
                .sendMessage(anyString(), anyString(), anyString(), anyString()))
                .thenReturn(false);
        boolean result = mailService.sendMessage(message);

        assertFalse(result);
        assertEquals(message.getStatus(), Status.ERROR);
    }

    @Test
    public void testIsMailSenderExecutorMethodCalled() {
        Mockito.when(mailSender
                .sendMessage(anyString(), anyString(), anyString(), anyString()))
                .thenReturn(false);
        mailService.sendMessage(message);

        Mockito.verify(mailSender, Mockito.times(1))
                .sendMessage(anyString(), anyString(), anyString(), anyString());
    }

    @Test
    public void testIsMailSenderExecutorMethodCalledWithRightParameters() {
        Mockito.when(mailSender
                .sendMessage(anyString(), anyString(), anyString(), anyString()))
                .thenReturn(false);
        mailService.sendMessage(message);

        Mockito.verify(mailSender, Mockito.times(1))
                .sendMessage(fromPerson.getEmail(),
                toPerson.getEmail(),
                message.getSubject(),
                message.getText());
    }


    @Test
    public void removeMessagePositive() {
        Mockito.doAnswer(invocation -> {
            Message message = invocation.getArgument(0);
            message.setStatus(Status.REMOVED);
            return null;
        }).when(messageRepository).removeMessage(any());

        mailService.removeMessage(message);

        assertEquals(message.getStatus(), Status.REMOVED);
    }

    @Test
    public void removeMessageNegative() {
        Mockito.doAnswer(invocation -> {
            Message message = invocation.getArgument(0);
            message.setStatus(Status.ERROR);
            return null;
        }).when(messageRepository).removeMessage(any());

        mailService.removeMessage(message);

        assertEquals(message.getStatus(), Status.ERROR);
    }

    @Test
    public void testIsMessageRepositoryExecutorMethodCalled() {
        Mockito.doAnswer(invocation -> {
            Message message = invocation.getArgument(0);
            message.setStatus(Status.REMOVED);
            return null;
        }).when(messageRepository).removeMessage(any());

        mailService.removeMessage(message);

        Mockito.verify(messageRepository, Mockito.times(1))
                .removeMessage(any());
    }

    @Test
    public void testIsMessageRepositoryExecutorMethodCalledWithRightParameters() {
        Mockito.doAnswer(invocation -> {
            Message message = invocation.getArgument(0);
            message.setStatus(Status.REMOVED);
            return null;
        }).when(messageRepository).removeMessage(any());

        mailService.removeMessage(message);

        Mockito.verify(messageRepository, Mockito.times(1))
                .removeMessage(message);
    }
}