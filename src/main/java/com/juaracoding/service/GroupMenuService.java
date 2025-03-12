package com.juaracoding.service;


import com.juaracoding.config.OtherConfig;
import com.juaracoding.core.IReport;
import com.juaracoding.core.IService;
import com.juaracoding.dto.response.RespGroupMenuDTO;
import com.juaracoding.dto.validation.ValGroupMenuDTO;
import com.juaracoding.handler.GlobalResponse;
import com.juaracoding.model.GroupMenu;
import com.juaracoding.repo.GroupMenuRepo;
import com.juaracoding.security.RequestCapture;
import com.juaracoding.util.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 *  Platform Code  - USM
 *  Modul Code - 01
 *  FV - FE
 */
@Service
@Transactional
public class GroupMenuService implements IService<GroupMenu>, IReport<GroupMenu> {

    @Autowired
    private GroupMenuRepo groupMenuRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private TransformPagination transformPagination;

    StringBuilder sBuild = new StringBuilder();


    @Override
    public ResponseEntity<Object> save(GroupMenu groupMenu, HttpServletRequest request) {
        Map<String,Object> mapToken = GlobalFunction.extractToken(request);
        try {
            groupMenu.setCreatedBy(Long.parseLong(mapToken.get("userId").toString()));
            groupMenuRepo.save(groupMenu);
        }catch (Exception e) {
            LoggingFile.logException("GroupMenuService","save(GroupMenu groupMenu, HttpServletRequest request) -- Line 59 "+RequestCapture.allRequest(request),e,OtherConfig.getEnableLog());
            return GlobalResponse.dataGagalDisimpan("USM01FE001",request);
        }
        return GlobalResponse.dataBerhasilDisimpan(request);
    }

    @Override
    public ResponseEntity<Object> update(Long id, GroupMenu groupMenu, HttpServletRequest request) {
        Map<String,Object> mapToken = GlobalFunction.extractToken(request);
        if (groupMenu == null) {
            return GlobalResponse.dataTidakValid("USM01FV011",request);
        }

        try {
            Optional<GroupMenu> optionalGroupMenu = groupMenuRepo.findById(id);
            if (!optionalGroupMenu.isPresent()) {
                return GlobalResponse.dataTidakDitemukan("USM01FV012",request);
            }

            GroupMenu nextGroupMenu = optionalGroupMenu.get();
            nextGroupMenu.setCreatedBy(Long.parseLong(mapToken.get("userId").toString()));
            nextGroupMenu.setNama(groupMenu.getNama());
            nextGroupMenu.setDeskripsi(groupMenu.getDeskripsi());

        }catch (Exception e) {
            LoggingFile.logException("GroupMenuService","update(Long id, GroupMenu groupMenu, HttpServletRequest request) -- Line 81 "+RequestCapture.allRequest(request),e,OtherConfig.getEnableLog());
            return GlobalResponse.dataGagalDiubah("USM01FE011",request);
        }
        return GlobalResponse.dataBerhasilDiubah(request);
    }

    @Override
    public ResponseEntity<Object> delete(Long id, HttpServletRequest request) {
        try {
            Optional<GroupMenu> optionalGroupMenu = groupMenuRepo.findById(id);
            if (!optionalGroupMenu.isPresent()) {
                return GlobalResponse.dataTidakDitemukan("USM01FV021",request);
            }
        }catch (Exception e) {
            LoggingFile.logException("GroupMenuService","delete(Long id) -- Line 95 "+RequestCapture.allRequest(request),e,OtherConfig.getEnableLog());
            return GlobalResponse.dataGagalDihapus("USM01FE021",request);
        }
        return GlobalResponse.dataBerhasilDihapus(request);
    }

    @Override
    public ResponseEntity<Object> findAll(Pageable pageable, HttpServletRequest request) {
        Page<GroupMenu> page = null;
        List<GroupMenu> list = null;
        page = groupMenuRepo.findAll(pageable);
        list = page.getContent();
        List<RespGroupMenuDTO> lt = converToRespGroupMenuDTO(list);

        return GlobalResponse.dataDitemukan(transformPagination.transformPagination(lt,page,null,null),
                request);
    }

    @Override
    public ResponseEntity<Object> findById(Long id, HttpServletRequest request) {
        Optional<GroupMenu> optionalGroupMenu = null;
        try {
            optionalGroupMenu = groupMenuRepo.findById(id);
            if (!optionalGroupMenu.isPresent()) {
                return GlobalResponse.dataTidakDitemukan("USM01FV041",request);
            }
        }catch (Exception e) {
            LoggingFile.logException("GroupMenuService","findById(Long id, HttpServletRequest request) -- Line 122 "+RequestCapture.allRequest(request),e,OtherConfig.getEnableLog());
            return GlobalResponse.terjadiKesalahan("USM01FE041",request);
        }
        return GlobalResponse.dataDitemukan(optionalGroupMenu.get(),request);
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
        list = page.getContent();
        List<RespGroupMenuDTO> lt = converToRespGroupMenuDTO(list);

        return GlobalResponse.dataDitemukan(transformPagination.transformPagination(lt,page,columnName,value),
                request);
    }

    @Override
    public ResponseEntity<Object> uploadDataExcel(MultipartFile multipartFile, HttpServletRequest request) {
        Map<String,Object> mapToken = GlobalFunction.extractToken(request);
        String message = "";
        /** pengecekan file nya excel atau bukan */
        if(!ExcelReader.hasWorkBookFormat(multipartFile)){
            return GlobalResponse.formatHarusExcel("USM01FV061",request);
        }
        try{
            List lt = new ExcelReader(multipartFile.getInputStream(),"Sheet1").getDataMap();
            if(lt.isEmpty()){
                return GlobalResponse.fileExcelKosong("USM01FV062",request);
            }
            groupMenuRepo.saveAll(convertListWorkBookToListEntity(lt,Long.parseLong(mapToken.get("userId").toString())));
        }catch (Exception e) {
            LoggingFile.logException("GroupMenuService","uploadDataExcel(MultipartFile multipartFile, HttpServletRequest request)  -- Line 161 "+RequestCapture.allRequest(request),e,OtherConfig.getEnableLog());
            return GlobalResponse.formatHarusExcel("USM01FE061",request);
        }
        return GlobalResponse.uploadExcelBerhasil(request);
    }

    @Override
    public List<GroupMenu> convertListWorkBookToListEntity(List<Map<String, String>> workBookData, Long userId) {
        List<GroupMenu> list = new ArrayList<>();
        for (Map<String, String> map : workBookData) {
            GroupMenu groupMenu = new GroupMenu();
            groupMenu.setNama(map.get("nama-group-menu"));
            groupMenu.setDeskripsi(map.get("deskripsi"));
            groupMenu.setCreatedBy(userId);
            list.add(groupMenu);
        }
        return list;
    }

    @Override
    public void downloadReportExcel(String column, String value, HttpServletRequest request, HttpServletResponse response) {
        List<GroupMenu> groupMenuList = null;
        switch (column){
            case "nama" : groupMenuList = groupMenuRepo.findByNamaContainsIgnoreCase(value);
            case "deskripsi" : groupMenuList = groupMenuRepo.findByDeskripsiContainsIgnoreCase(value);
            default : groupMenuList = groupMenuRepo.findAll();
        }

        List<RespGroupMenuDTO> lt = converToRespGroupMenuDTO(groupMenuList);
        if(lt.isEmpty()){
            GlobalResponse.manualResponse(response,GlobalResponse.dataTidakDitemukan("USM01FV071",request));
            return;
        }

        sBuild.setLength(0);
        String headerKey = "Content-Disposition";
        sBuild.setLength(0);

        String headerValue = sBuild.append("attachment; filename=group-menu_").
                append(new SimpleDateFormat("dd-MM-yyyy_HH:mm:ss").
                format(new Date())).append(".xlsx").toString();
        response.setHeader(headerKey, headerValue);
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

        Map<String,Object> map = GlobalFunction.convertClassToMap(new RespGroupMenuDTO());
        List<String> listTemp = new ArrayList<>();
        for (Map.Entry<String,Object> entry: map.entrySet()){
            listTemp.add(entry.getKey());
        }

        int intListTemp = listTemp.size();
        String [] headerArr = new String[intListTemp];//kolom judul di excel
        String [] loopDataArr = new String[intListTemp]; // kolom judul untuk java reflection

        /** Untuk mempersiapkan data judul kolomnya */
        for (int i = 0; i < intListTemp; i++){
            headerArr[i] = GlobalFunction.camelToStandard(listTemp.get(i));
            loopDataArr[i] = listTemp.get(i);
        }

        /** Untuk mempersiapkan data body baris nyya */
        int intListDTOSize = lt.size();
        String [][] strBody = new String[intListDTOSize][intListTemp];
        for (int i = 0; i < intListDTOSize; i++){
            map = GlobalFunction.convertClassToMap(lt.get(i));
            for(int j = 0; j < intListTemp; j++){
                strBody[i][j] = String.valueOf(map.get(loopDataArr[j]));
            }
        }
        new ExcelWriter(strBody,headerArr,"sheet-1",response);
    }

    @Override
    public void generateToPDF(String column, String value, HttpServletRequest request, HttpServletResponse response) {

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
