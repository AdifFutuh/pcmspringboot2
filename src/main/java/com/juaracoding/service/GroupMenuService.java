package com.juaracoding.service;


import com.juaracoding.core.IService;
import com.juaracoding.model.GroupMenu;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

/**
 *  Platform Code  - USM
 *  Modul Code - 01
 *  FV - FE
 */
// 01 05
    // 06 10
    // 11 15
@Service
@Transactional
public class GroupMenuService implements IService<GroupMenu> {


    @Override
    public void insert(GroupMenu groupMenu) {
        if(groupMenu==null){
            System.out.println("USM01FV001");
        }
        if(1==1){
            System.out.println("USM01FV002");
        }

        try{

        }catch(Exception e){
            System.out.println("USM01FE001");
        }
    }

    @Override
    public void update(Long id, GroupMenu groupMenu) {
        if(groupMenu==null){
            System.out.println("USM01FV011");
        }
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public GroupMenu findBy(Long id) {
        return null;
    }

    @Override
    public List<GroupMenu> findAll() {
        return List.of();
    }
}
