package com.transfer.app7.domain.dto;

import com.transfer.app7.domain.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AppEventDto {

    private Long id;
    private LocalDateTime date = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    private Event event;

    private String information;
}
