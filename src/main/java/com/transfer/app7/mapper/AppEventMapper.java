package com.transfer.app7.mapper;

import com.transfer.app7.domain.AppEvent;
import com.transfer.app7.domain.dto.AppEventDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AppEventMapper {

    public AppEvent mapToAppEvent(final AppEventDto appEventDto) {
        return new AppEvent(
                appEventDto.getId(),
                appEventDto.getDate(),
                appEventDto.getTime(),
                appEventDto.getEvent(),
                appEventDto.getInformation());
    }
    public AppEventDto mapToAppEventDto(final AppEvent appEvent) {
        return new AppEventDto(
                appEvent.getId(),
                appEvent.getDate(),
                appEvent.getTime(),
                appEvent.getEvent(),
                appEvent.getInformation());
    }

    public List<AppEventDto> mapToAppEventDtoList(final List<AppEvent> appEventList) {
        return appEventList.stream()
                .map(appEvent -> new AppEventDto(
                        appEvent.getId(),
                        appEvent.getDate(),
                        appEvent.getTime(),
                        appEvent.getEvent(),
                        appEvent.getInformation()))
                .collect(Collectors.toList());
    }
}
