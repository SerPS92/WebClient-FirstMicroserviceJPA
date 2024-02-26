package com.example.clientFirstMicroserviceJPA.controller;


import com.example.clientFirstMicroserviceJPA.model.Person;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;

@RestController
public class PersonController {

    private final WebClient webClient;
    String url = "http://localhost:8080";

    public PersonController(WebClient webClient) {
        this.webClient = webClient;
    }

    @GetMapping(value = "/people/{name}/{email}/{age}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Person> addPerson(@PathVariable(name = "name") String name,
                                  @PathVariable(name = "email") String email,
                                  @PathVariable(name = "age") int age) {
        Person person = new Person(name, email, age);
        webClient
                .post() //requestBodyUriSpec
                .uri(url + "/contacts") //requestBodySpec
                .contentType(MediaType.APPLICATION_JSON) //requestBodySpec
                .bodyValue(person) //RequestHeadersSpec
                .retrieve() //ResponseSpec
                .bodyToMono(void.class) //Mono<Void>
                .block(); //Void

        Person[] people = webClient
                .get() //RequestHeadersUriSpec
                .uri(url + "/contacts") //RequestHeadersSpec
                .retrieve() //ResponseSpec
                .bodyToMono(Person[].class) //Mono <Person[]>
                .block(); //Person[]

        return Arrays.asList(people);
    }


}
