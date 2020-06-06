package com.example.webapi.service.impl;

import com.example.webapi.domain.Contact;
import com.example.webapi.domain.Skill;
import com.example.webapi.exception.ContactNotFoundException;
import com.example.webapi.exception.SkillNotFoundException;
import com.example.webapi.repository.ContactRepository;
import com.example.webapi.repository.SkillRepository;
import com.example.webapi.service.ContactService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.BiConsumer;

@Service
public class ContactServiceImpl implements ContactService {

    private final ContactRepository contactRepository;
    private final SkillRepository skillRepository;

    public ContactServiceImpl(ContactRepository contactRepository, SkillRepository skillRepository) {
        this.contactRepository = contactRepository;
        this.skillRepository = skillRepository;
    }

    @Override
    public Page<Contact> findAllContacts(Pageable pageable) {
        return contactRepository.findAll(pageable);
    }

    @Override
    public Page<Contact> findContactsBySkills(Pageable pageable, List<Long> skillIds, Integer minMatch) {
        if (skillIds == null || skillIds.isEmpty()) {
            return contactRepository.findAll(pageable);
        }
        return contactRepository.findBySkillsIdIn(skillIds, pageable);
    }

    @Override
    @Transactional
    public Contact createContact(Contact contact) {
        return contactRepository.save(contact);
    }

    @Override
    public Optional<Contact> findContactById(UUID id) {
        return contactRepository.findById(id);
    }

    @Override
    @Transactional
    public Contact updateContact(Contact contact) {
        return contactRepository.save(contact);
    }

    @Override
    @Transactional
    public void deleteContact(UUID id) {
        contactRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Contact addSkillToContact(UUID contactId, long skillId) throws ContactNotFoundException, SkillNotFoundException {
        return addOrRemoveSkill(contactId, skillId, Contact::addSkill);
    }

    @Override
    @Transactional
    public Contact removeSkillFromContact(UUID contactId, long skillId) throws ContactNotFoundException, SkillNotFoundException {
        return addOrRemoveSkill(contactId, skillId, Contact::removeSkill);
    }

    private Contact addOrRemoveSkill(UUID contactId, long skillId, BiConsumer<Contact, Skill> consumer) throws ContactNotFoundException, SkillNotFoundException {
        Contact contact = findContactById(contactId).orElseThrow(() -> new ContactNotFoundException(contactId));
        Skill skill = skillRepository.findById(skillId).orElseThrow(() -> new SkillNotFoundException(skillId));
        consumer.accept(contact, skill);
        return contactRepository.save(contact);
    }
}
