package com.transfer.app7.repository;

import com.transfer.app7.domain.AppEvent;
import com.transfer.app7.domain.Event;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AppEventRepository extends CrudRepository<AppEvent, Long> {

    @Override
    List<AppEvent> findAll();

    List<AppEvent> findByDate(LocalDate date);
    List<AppEvent> findByType(Event event);

    @Override
    AppEvent save(AppEvent appEvent);

    @Override
    Optional<AppEvent> findById(Long id);

    @Override
    void deleteById(Long id);

    @Override
    long count();
}
