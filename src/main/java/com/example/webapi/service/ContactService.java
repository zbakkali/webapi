package com.example.webapi.service;

import com.example.webapi.domain.Contact;
import com.example.webapi.exception.ContactNotFoundException;
import com.example.webapi.exception.SkillNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ContactService {
    Page<Contact> findAllContacts(Pageable pageable);
    Page<Contact> findContactsBySkills(Pageable pageable, List<Long> skillIds, Integer minMatch);
    Contact createContact(Contact contact);
    Optional<Contact> findContactById(UUID id);
    Contact updateContact(Contact contact);
    void deleteContact(UUID id);
    Contact addSkillToContact(UUID contactId, long skillId) throws ContactNotFoundException, SkillNotFoundException;
    Contact removeSkillFromContact(UUID contactId, long skillId) throws ContactNotFoundException, SkillNotFoundException;
}
