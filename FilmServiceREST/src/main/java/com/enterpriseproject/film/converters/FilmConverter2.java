package com.enterpriseproject.film.converters;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.support.converter.MarshallingMessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import com.enterpriseproject.film.Films;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class FilmConverter2 {

    @Bean
    public MessageConverter MarshallingConvertor(ObjectMapper objectMapper) {

        // Initialize JAXBContext and marshaller/unmarshaller
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setClassesToBeBound(Films.class);

        // Initialize and configure MarshallingMessageConverter
        MarshallingMessageConverter converter = new MarshallingMessageConverter();
        converter.setTargetType(MessageType.TEXT); // Set target message type only once
        converter.setMarshaller(marshaller);
        converter.setUnmarshaller(marshaller);

        return converter;
}

}