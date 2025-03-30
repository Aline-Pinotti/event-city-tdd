package com.devsuperior.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.demo.repositories.EventRepository;
import com.devsuperior.demo.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

import com.devsuperior.demo.dto.EventDTO;
import com.devsuperior.demo.entities.City;
import com.devsuperior.demo.entities.Event;

@Service
public class EventService {

    @Autowired
    private EventRepository repository;

    @Transactional
    public EventDTO update(Long id, EventDTO dto){ //o id é do evento existente, o dto é o alterado

        try{
            Event entity = repository.getReferenceById(id);
            toEntity(dto, entity);
            entity = repository.save(entity);
            return new EventDTO(entity);
        }catch(EntityNotFoundException e){
            throw new ResourceNotFoundException("Id not found " + id);

        }

    }

    //eu não poderia fazer um método que retorna a entidade dentro do DTO?
    private void toEntity(EventDTO dto, Event entity){
        entity.setName(dto.getName());
        entity.setDate(dto.getDate());
        entity.setUrl(dto.getUrl());
        //entity.setCity(cityRepository.getReferenceById(dto.getCityId()));
        entity.setCity(new City(dto.getCityId(), null));
    }
}
