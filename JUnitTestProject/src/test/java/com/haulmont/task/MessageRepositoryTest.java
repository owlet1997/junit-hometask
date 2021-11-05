package com.haulmont.task;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import static org.junit.Assert.*;

public class MessageRepositoryTest {
    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    MessageRepository messageRepository;

    Person fromPerson;
    Person toPerson;

    Message message;

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

    @Before
    public void setUp(){
        messageRepository = new MessageRepository();
        setupUsers();
        setupMessage();
    }

    @Test
    public void removeMessage() {
        Message message = messageRepository.addMessage(fromPerson, toPerson,"Subject",  "Message text");

        exceptionRule.expect(NotImplementedException.class);
        messageRepository.removeMessage(message);
    }

    @Test
    public void addMessage() {
        Message message = messageRepository.addMessage(fromPerson, toPerson,"Subject",  "Message text");

        assertEquals(message.getFrom().getEmail(), "fromEmail@ya.ru");
        assertEquals(message.getTo().getEmail(), "toEmail@ya.ru");
        assertEquals(message.getStatus(),Status.NEW);
        assertEquals(message.getSubject(), "Subject");
        assertEquals(message.getText(), "Message text");
    }
}