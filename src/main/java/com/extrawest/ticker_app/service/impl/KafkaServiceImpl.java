package com.extrawest.ticker_app.service.impl;

import com.extrawest.ticker_app.dto.TicksHistoryDto;
import com.extrawest.ticker_app.dto.mapper.TicksHistoryMapper;
import com.extrawest.ticker_app.exception.ApiRequestException;
import com.extrawest.ticker_app.model.TickSender;
import com.extrawest.ticker_app.repository.TicksHistoryRepository;
import com.extrawest.ticker_app.service.KafkaService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class KafkaServiceImpl implements KafkaService {
    private final KafkaTemplate<Long, TicksHistoryDto> kafkaTemplate;
    private final ObjectMapper objectMapper;
    private final TicksHistoryRepository ticksHistoryRepository;
    private final TicksHistoryMapper ticksHistoryMapper;
    private final SimpMessagingTemplate simpMessagingTemplate;

    @Override
    public void send(TicksHistoryDto dto) {
        kafkaTemplate.send("ticks", dto);
    }

    @Override
    @KafkaListener(id = "ticks", topics = {"ticks"}, containerFactory = "singleFactory")
    public void consume(TicksHistoryDto dto) {
        log.info("=> consumed {}", writeValueAsString(dto));
        ticksHistoryRepository.save(ticksHistoryMapper.toModel(dto));
        TickSender tickSender = new TickSender(dto.toString());
        simpMessagingTemplate.convertAndSend("topic/public", tickSender);
        simpMessagingTemplate.convertAndSendToUser(ticksHistoryMapper.toModel(dto).getTicker().getUser().getEmail(),
                "/private", tickSender);
    }

    private String writeValueAsString(TicksHistoryDto dto) {
        try {
            return objectMapper.writeValueAsString(dto);
        } catch (JsonProcessingException e) {
            throw new ApiRequestException("Writing value to JSON was failed: " + dto.toString());
        }
    }
}
