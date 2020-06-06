package com.example.webapi.exception;

import java.util.UUID;

public class ContactNotFoundException extends AbstractNotFoundException {

    public ContactNotFoundException(UUID contactId) {
        super(contactId, "Contact");
    }
}
