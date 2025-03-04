package com.juaracoding.service;


import com.juaracoding.core.IService;
import com.juaracoding.dto.response.RespGroupMenuDTO;
import com.juaracoding.dto.validation.ValGroupMenuDTO;
import com.juaracoding.model.GroupMenu;
import com.juaracoding.repo.GroupMenuRepo;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 *  Platform Code  - USM
 *  Modul Code - 01
 *  FV - FE
 */
@Service
@Transactional
public class GroupMenuService implements IService<GroupMenu> {

    @Autowired
    private GroupMenuRepo groupMenuRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public  ResponseEntity<Object> insert(GroupMenu groupMenu) {
        if(groupMenu==null){
            return ResponseEntity.badRequest().body("USM01FV001");
        }
        groupMenuRepo.save(groupMenu);
        return ResponseEntity.status(HttpStatus.CREATED).body("USM01FV001");
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
    public ResponseEntity<Object> findAll() {

        List<RespGroupMenuDTO> list = converToRespGroupMenuDTO(groupMenuRepo.findAll());
//        return ResponseEntity.ok(groupMenuRepo.findAll());
        return ResponseEntity.ok(list);
    }

    public List<RespGroupMenuDTO> converToRespGroupMenuDTO(List<GroupMenu> groupMenus) {
//        List<RespGroupMenuDTO> respGroupMenuDTOList = new ArrayList<>();
//        for (GroupMenu groupMenu : groupMenus) {
//            RespGroupMenuDTO respGroupMenuDTO = new RespGroupMenuDTO();
//            respGroupMenuDTO.setId(groupMenu.getId());
//            respGroupMenuDTO.setNama(groupMenu.getNama());
//            respGroupMenuDTO.setDeskripsi(groupMenu.getDeskripsi());
//            respGroupMenuDTOList.add(respGroupMenuDTO);
//        }
        List<RespGroupMenuDTO> respGroupMenuDTOList = modelMapper.map(groupMenus, new TypeToken<List<RespGroupMenuDTO>>() {}.getType());
        return respGroupMenuDTOList;
    }

    public GroupMenu converToEntity(ValGroupMenuDTO valGroupMenuDTO) {
//        GroupMenu groupMenu = new GroupMenu();
//        groupMenu.setNama(valGroupMenuDTO.getNama());
//        groupMenu.setDeskripsi(valGroupMenuDTO.getDeskripsi());
        GroupMenu groupMenu = modelMapper.map(valGroupMenuDTO, GroupMenu.class);
        return groupMenu;
    }
}
