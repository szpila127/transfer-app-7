package com.transfer.app7.service;

import com.transfer.app7.domain.AppEvent;
import com.transfer.app7.domain.Event;
import com.transfer.app7.repository.AppEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class AppEventService {

    @Autowired
    private AppEventRepository appEventRepository;

    public List<AppEvent> getAllEvents() {
        return appEventRepository.findAll();
    }

    public List<AppEvent> getByDate(LocalDate date) {
        return appEventRepository.findByDate(date);
    }

    public List<AppEvent> getByType(Event event) {
        return appEventRepository.findByType(event);
    }

    public AppEvent save(AppEvent appEvent) {
        return appEventRepository.save(appEvent);
    }

    public Optional<AppEvent> getEvent(Long id) {
        return appEventRepository.findById(id);
    }

    public long countEvents() {
        return appEventRepository.count();
    }
}
