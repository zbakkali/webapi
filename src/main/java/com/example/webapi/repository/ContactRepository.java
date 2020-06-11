package com.example.webapi.repository;

import com.example.webapi.domain.Contact;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ContactRepository extends JpaRepository<Contact, UUID> {

    Page<Contact> findBySkillsIdIn(List<Long> skillIds, Pageable pageable);
}
