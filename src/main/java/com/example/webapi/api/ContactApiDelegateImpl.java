package com.example.webapi.api;

import com.example.webapi.domain.Contact;
import com.example.webapi.mapper.ContactMapper;
import com.example.webapi.model.*;
import com.example.webapi.service.ContactService;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class ContactApiDelegateImpl implements ContactApiDelegate {

    private final ContactService contactService;
    private final ContactMapper contactMapper;

    public ContactApiDelegateImpl(ContactService contactService, ContactMapper contactMapper) {
        this.contactService = contactService;
        this.contactMapper = contactMapper;
    }

    @Override
    public ResponseEntity<HALExtendedContactDto> addSkillToContact(UUID contactId, Long skillId, String ifMatch) {
        Contact contact = contactService.addSkillToContact(contactId, skillId);
        return ResponseEntity.ok(contactMapper.entityToExtendedDto(contact));
    }

    @Override
    public ResponseEntity<HALExtendedContactDto> createContact(HALExtendedContactDto haLExtendedContactDto) {
        Contact contact = contactService.createContact(contactMapper.dtoToEntity(haLExtendedContactDto));
        return new ResponseEntity<>(contactMapper.entityToExtendedDto(contact), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Void> deleteContact(UUID contactId, String ifMatch) {
        contactService.deleteContact(contactId);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<HALContactsDto> findAllContactsBySkillIds(List<Long> skillIds, Integer skillIdsMinMatch, Integer limit, Integer page, String ifNoneMatch) {
        List<HALContactDto> contacts = contactService.findContactsBySkills(PageRequest.of(page, limit), skillIds, skillIdsMinMatch)
                .getContent()
                .stream()
                .map(contactMapper::entityToDto)
                .collect(Collectors.toList());
        HALContactsAllOfEmbeddedDto halEmbeddedSkillsEmbeddedDto = new HALContactsAllOfEmbeddedDto();
        halEmbeddedSkillsEmbeddedDto.setContacts(contacts);
        HALContactsDto halContactsDto = new HALContactsDto();
        halContactsDto.setEmbedded(halEmbeddedSkillsEmbeddedDto);
        return ResponseEntity.ok(halContactsDto);
    }

    @Override
    public ResponseEntity<HALExtendedContactDto> getContact(UUID contactId, String ifNoneMatch) {
        Optional<HALExtendedContactDto> halExtendedContactDto = contactService.findContactById(contactId).map(contactMapper::entityToExtendedDto);
        return ResponseEntity.of(halExtendedContactDto);
    }

    @Override
    public ResponseEntity<HALExtendedContactDto> removeSkillFromContact(UUID contactId, Long skillId, String ifMatch) {
        Contact contact = contactService.removeSkillFromContact(contactId, skillId);
        return ResponseEntity.ok(contactMapper.entityToExtendedDto(contact));
    }

    @Override
    public ResponseEntity<HALExtendedContactDto> updateContact(UUID contactId, String ifMatch, HALExtendedContactDto haLExtendedContactDto) {
        haLExtendedContactDto.setId(contactId);
        Contact contact = contactService.updateContact(contactMapper.dtoToEntity(haLExtendedContactDto));
        return ResponseEntity.ok(contactMapper.entityToExtendedDto(contact));
    }
}
