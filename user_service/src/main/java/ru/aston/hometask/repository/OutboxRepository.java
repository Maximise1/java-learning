package ru.aston.hometask.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

import ru.aston.hometask.repository.model.OutboxEvent;

public interface OutboxRepository extends JpaRepository<OutboxEvent, Long> {
    List<OutboxEvent> findTop20BySentFalseOrderByCreatedAtAsc();
}
