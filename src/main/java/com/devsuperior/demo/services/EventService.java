package com.devsuperior.demo.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.demo.repositories.CityRepository;
import com.devsuperior.demo.repositories.EventRepository;

import jakarta.persistence.EntityNotFoundException;

import com.devsuperior.demo.dto.EventDTO;
import com.devsuperior.demo.entities.Event;

@Service
public class EventService {

    private EventRepository repository;

    private CityRepository cityRepository;

    @Transactional
    public EventDTO update(Long id, EventDTO dto){ //o id é do evento existente, o dto é o alterado

        try{
            Event entity = repository.getReferenceById(id);
            toEntity(dto, entity);
            entity = repository.save(entity);
            return new EventDTO(entity);
        }catch(EntityNotFoundException e){
            throw new EntityNotFoundException("Recurso não encontrado");

        }

    }

    //eu não poderia fazer um método que retorna a entidade dentro do DTO?
    private void toEntity(EventDTO dto, Event entity){
        entity.setName(dto.getName());
        entity.setDate(dto.getDate());
        entity.setUrl(dto.getUrl());
        entity.setCity(cityRepository.getReferenceById(dto.getCityId()));
    }
}
