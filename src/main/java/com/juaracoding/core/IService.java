package com.juaracoding.core;

import java.util.List;

/** interface / kontrak untuk handling CRUD */
public interface IService<T> {

    void insert(T t);// 001 010
    void update(Long id,T t);//011 020
    void delete(Long id);//021 030
    T findBy(Long id);//031 040
    List<T> findAll();//041 050
}
