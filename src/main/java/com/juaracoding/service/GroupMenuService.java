package com.juaracoding.service;


import com.juaracoding.config.ContohConfig;
import com.juaracoding.config.OtherConfig;
import com.juaracoding.core.IService;
import com.juaracoding.dto.response.RespGroupMenuDTO;
import com.juaracoding.dto.validation.ValGroupMenuDTO;
import com.juaracoding.model.GroupMenu;
import com.juaracoding.repo.GroupMenuRepo;
import com.juaracoding.security.RequestCapture;
import com.juaracoding.util.LoggingFile;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.io.IOUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

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
    public ResponseEntity<Object> save(GroupMenu groupMenu, HttpServletRequest request) {
        try {
            groupMenuRepo.save(groupMenu);
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Data Gagal Disimpan "+e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.CREATED).body("Data Berhasil Disimpan");
    }

    @Override
    public ResponseEntity<Object> update(Long id, GroupMenu groupMenu, HttpServletRequest request) {
        if (groupMenu == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Data Tidak Valid - USM01FV011");
        }

        try {
            Optional<GroupMenu> optionalGroupMenu = groupMenuRepo.findById(id);

            if (!optionalGroupMenu.isPresent()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Data Tidak Valid - USM01FV012");
            }
            GroupMenu nextGroupMenu = optionalGroupMenu.get();
            groupMenu.setNama(nextGroupMenu.getNama());
            groupMenu.setDeskripsi(groupMenu.getDeskripsi());

        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Data Gagal di Ubah - USM01FE011");
        }

        return ResponseEntity.status(HttpStatus.OK).body("Data Tidak Valid - USM01FV012");
    }

    @Override
    public ResponseEntity<Object> delete(Long id, HttpServletRequest request) {
        return null;
    }

    @Override
    public ResponseEntity<Object> findAll(Pageable pageable, HttpServletRequest request) {
        return null;
    }

    @Override
    public ResponseEntity<Object> findById(Long id, HttpServletRequest request) {
        return null;
    }

    @Override
    public ResponseEntity<Object> findByParam(Pageable pageable, String columnName, String value, HttpServletRequest request) {

        Page<GroupMenu> page = null;
        List<GroupMenu> list = null;
        switch (columnName) {
            case "nama": page = groupMenuRepo.findByNamaContainsIgnoreCase(value,pageable);break;
            case "deskripsi": page = groupMenuRepo.findByDeskripsiContainsIgnoreCase(value,pageable);break;
            default: page = groupMenuRepo.findAll(pageable);
        }

//        return ResponseEntity.status(HttpStatus.OK).body(page);
        return ResponseEntity.status(HttpStatus.OK).body(page.getContent());
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
