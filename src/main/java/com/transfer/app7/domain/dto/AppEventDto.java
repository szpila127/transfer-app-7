package com.transfer.app7.domain.dto;

import com.transfer.app7.domain.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AppEventDto {

    private long id;
    private LocalDateTime date = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    private Event event;

    private String information;

    public AppEventDto(Event event, String information) {
        this.event = event;
        this.information = information;
    }
}
