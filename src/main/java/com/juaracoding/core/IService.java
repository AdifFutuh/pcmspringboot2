package com.juaracoding.core;

import org.springframework.http.ResponseEntity;

import java.util.List;

/** interface / kontrak untuk handling CRUD */
public interface IService<T> {

    ResponseEntity<Object> insert(T t);// 001 010
    void update(Long id,T t);//011 020
    void delete(Long id);//021 030
    T findBy(Long id);//031 040
    ResponseEntity<Object> findAll();//041 050
}
