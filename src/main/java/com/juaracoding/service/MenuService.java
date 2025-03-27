package com.juaracoding.service;


import com.juaracoding.config.OtherConfig;
import com.juaracoding.core.IReport;
import com.juaracoding.core.IService;
import com.juaracoding.dto.report.RepMenuDTO;
import com.juaracoding.dto.response.RespMenuDTO;
import com.juaracoding.dto.validation.ValMenuDTO;
import com.juaracoding.handler.GlobalResponse;
import com.juaracoding.model.Menu;
import com.juaracoding.repo.MenuRepo;
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
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

/**
 *  Platform Code  - USM
 *  Modul Code - 02
 *  FV - FE
 */
@Service
@Transactional
public class MenuService implements IService<Menu>, IReport<Menu> {

    @Autowired
    private MenuRepo menuRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private TransformPagination transformPagination;

    StringBuilder sBuild = new StringBuilder();

    @Autowired
    private SpringTemplateEngine springTemplateEngine;

    @Autowired
    private PdfGenerator pdfGenerator;


    @Override
    public ResponseEntity<Object> save(Menu menu, HttpServletRequest request) {
        Map<String,Object> mapToken = GlobalFunction.extractToken(request);
        try {
            menu.setCreatedBy(Long.parseLong(mapToken.get("userId").toString()));
            menuRepo.save(menu);
        }catch (Exception e) {
            LoggingFile.logException("MenuService","save(Menu menu, HttpServletRequest request) -- Line 59 "+RequestCapture.allRequest(request),e,OtherConfig.getEnableLog());
            return GlobalResponse.dataGagalDisimpan("USM02FE001",request);
        }
        return GlobalResponse.dataBerhasilDisimpan(request);
    }

    @Override
    public ResponseEntity<Object> update(Long id, Menu menu, HttpServletRequest request) {
        Map<String,Object> mapToken = GlobalFunction.extractToken(request);
        if (menu == null) {
            return GlobalResponse.dataTidakValid("USM02FV011",request);
        }

        try {
            Optional<Menu> optionalMenu = menuRepo.findById(id);
            if (!optionalMenu.isPresent()) {
                return GlobalResponse.dataTidakDitemukan("USM02FV012",request);
            }

            Menu nextMenu = optionalMenu.get();
            nextMenu.setModifiedBy(Long.parseLong(mapToken.get("userId").toString()));
            nextMenu.setNama(menu.getNama());
            nextMenu.setPath(menu.getPath());
            nextMenu.setGroupMenu(menu.getGroupMenu());

        }catch (Exception e) {
            LoggingFile.logException("MenuService","update(Long id, Menu menu, HttpServletRequest request) -- Line 81 "+RequestCapture.allRequest(request),e,OtherConfig.getEnableLog());
            return GlobalResponse.dataGagalDiubah("USM02FE011",request);
        }
        return GlobalResponse.dataBerhasilDiubah(request);
    }

    @Override
    public ResponseEntity<Object> delete(Long id, HttpServletRequest request) {
        try {
            Optional<Menu> optionalMenu = menuRepo.findById(id);
            if (!optionalMenu.isPresent()) {
                return GlobalResponse.dataTidakDitemukan("USM02FV021",request);
            }
        }catch (Exception e) {
            LoggingFile.logException("MenuService","delete(Long id) -- Line 95 "+RequestCapture.allRequest(request),e,OtherConfig.getEnableLog());
            return GlobalResponse.dataGagalDihapus("USM02FE021",request);
        }
        return GlobalResponse.dataBerhasilDihapus(request);
    }

    @Override
    public ResponseEntity<Object> findAll(Pageable pageable, HttpServletRequest request) {
        Page<Menu> page = null;
        List<Menu> list = null;
        page = menuRepo.findAll(pageable);
        list = page.getContent();
        List<RepMenuDTO> lt = converToRepMenuDTO(list);
        return GlobalResponse.dataDitemukan(transformPagination.transformPagination(lt,page,null,null),
                request);
    }

    @Override
    public ResponseEntity<Object> findById(Long id, HttpServletRequest request) {
        Optional<Menu> optionalMenu = null;
        try {
            optionalMenu = menuRepo.findById(id);
            if (!optionalMenu.isPresent()) {
                return GlobalResponse.dataTidakDitemukan("USM02FV041",request);
            }
        }catch (Exception e) {
            LoggingFile.logException("MenuService","findById(Long id, HttpServletRequest request) -- Line 122 "+RequestCapture.allRequest(request),e,OtherConfig.getEnableLog());
            return GlobalResponse.terjadiKesalahan("USM02FE041",request);
        }
        return GlobalResponse.dataDitemukan(modelMapper.map(optionalMenu.get(),RespMenuDTO.class),request);
    }

    public ResponseEntity<Object> allMenu(HttpServletRequest request){
        List<Menu> list = null;
        list = menuRepo.findAll();
        List<RepMenuDTO> lt = converToRepMenuDTO(list);
        return GlobalResponse.dataDitemukan(lt,
                request);
    }

    @Override
    public ResponseEntity<Object> findByParam(Pageable pageable, String columnName, String value, HttpServletRequest request) {

        Page<Menu> page = null;
        List<Menu> list = null;
        switch (columnName) {
            case "nama": page = menuRepo.findByNamaContainsIgnoreCase(value,pageable);break;
            case "path": page = menuRepo.findByPathContainsIgnoreCase(value,pageable);break;
            case "group": page = menuRepo.cariGroup(value,pageable);break;
            default: page = menuRepo.findAll(pageable);
        }
        list = page.getContent();
        List<RepMenuDTO> lt = converToRepMenuDTO(list);

        return GlobalResponse.dataDitemukan(transformPagination.transformPagination(lt,page,columnName,value),
                request);
    }

    @Override
    public ResponseEntity<Object> uploadDataExcel(MultipartFile multipartFile, HttpServletRequest request) {
        Map<String,Object> mapToken = GlobalFunction.extractToken(request);
        String message = "";
        /** pengecekan file nya excel atau bukan */
        if(!ExcelReader.hasWorkBookFormat(multipartFile)){
            return GlobalResponse.formatHarusExcel("USM02FV061",request);
        }
        try{
            List lt = new ExcelReader(multipartFile.getInputStream(),"Sheet1").getDataMap();
            if(lt.isEmpty()){
                return GlobalResponse.fileExcelKosong("USM02FV062",request);
            }
            menuRepo.saveAll(convertListWorkBookToListEntity(lt,Long.parseLong(mapToken.get("userId").toString())));
        }catch (Exception e) {
            LoggingFile.logException("MenuService","uploadDataExcel(MultipartFile multipartFile, HttpServletRequest request)  -- Line 161 "+RequestCapture.allRequest(request),e,OtherConfig.getEnableLog());
            return GlobalResponse.formatHarusExcel("USM02FE061",request);
        }
        return GlobalResponse.uploadExcelBerhasil(request);
    }

    @Override
    public List<Menu> convertListWorkBookToListEntity(List<Map<String, String>> workBookData, Long userId) {
        List<Menu> list = new ArrayList<>();
        for (Map<String, String> map : workBookData) {
            Menu menu = new Menu();
            menu.setNama(map.get("nama-menu"));
            menu.setPath(map.get("path"));
            menu.setCreatedBy(userId);
            list.add(menu);
        }
        return list;
    }

    @Override
    public void downloadReportExcel(String column, String value, HttpServletRequest request, HttpServletResponse response) {
        List<Menu> menuList = null;
        switch (column){
            case "nama" : menuList = menuRepo.findByNamaContainsIgnoreCase(value);
            case "path" : menuList = menuRepo.findByPathContainsIgnoreCase(value);
            case "group" : menuList = menuRepo.cariGroup(value);
            default : menuList = menuRepo.findAll();
        }

        List<RepMenuDTO> lt = converToRepMenuDTO(menuList);
        if(lt.isEmpty()){
            GlobalResponse.manualResponse(response,GlobalResponse.dataTidakDitemukan("USM02FV071",request));
            return;
        }

        sBuild.setLength(0);
        String headerKey = "Content-Disposition";
        sBuild.setLength(0);

        String headerValue = sBuild.append("attachment; filename=menu_").
                append(new SimpleDateFormat("dd-MM-yyyy_HH:mm:ss").
                format(new Date())).append(".xlsx").toString();
        response.setHeader(headerKey, headerValue);
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

        Map<String,Object> map = GlobalFunction.convertClassToMap(new RepMenuDTO());
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

        /** Untuk mempersiapkan data body baris nya */
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
        Map<String,Object> mapToken = GlobalFunction.extractToken(request);
        List<Menu> menuList = null;
        switch (column){
            case "nama" : menuList = menuRepo.findByNamaContainsIgnoreCase(value);
            case "path" : menuList = menuRepo.findByPathContainsIgnoreCase(value);
            case "group" : menuList = menuRepo.cariGroup(value);
            default : menuList = menuRepo.findAll();
        }

        List<RepMenuDTO> lt = converToRepMenuDTO(menuList);
        if(lt.isEmpty()){
            GlobalResponse.manualResponse(response,GlobalResponse.dataTidakDitemukan("USM02FV091",request));
            return;
        }
        int intRespMenuDTOList = lt.size();
        Map<String,Object> map = new HashMap<>();// ini untuk menampung seluruh data yang akan di oper ke file html
        String strHtml = null;
        Context context = new Context();
        Map<String,Object> mapColumnName = GlobalFunction.convertClassToMap(new RepMenuDTO());// ini diubah
        List<String> listTemp = new ArrayList<>();
        List<String> listHelper = new ArrayList<>();
        for (Map.Entry<String,Object> entry : mapColumnName.entrySet()) {
            listTemp.add(GlobalFunction.camelToStandard(entry.getKey()));
            listHelper.add(entry.getKey());
        }
        Map<String,Object> mapTemp = null;
        List<Map<String,Object>> listMap = new ArrayList<>();
        for(int i=0;i<intRespMenuDTOList;i++){
            mapTemp = GlobalFunction.convertClassToMap(lt.get(i));
            listMap.add(mapTemp);
        }

        map.put("title","REPORT DATA MENU");
        map.put("listKolom",listTemp);
        map.put("listHelper",listHelper);
        map.put("timestamp",LocalDateTime.now());
        map.put("totalData",intRespMenuDTOList);
        map.put("listContent",listMap);
        map.put("username",mapToken.get("namaLengkap"));
        context.setVariables(map);
        strHtml = springTemplateEngine.process("global-report",context);
        pdfGenerator.htmlToPdf(strHtml,"menu",response);
    }

    public void generateToPDFManual(String column, String value, HttpServletRequest request, HttpServletResponse response) {
        Map<String,Object> mapToken = GlobalFunction.extractToken(request);
        List<Menu> menuList = null;
        switch (column){
            case "nama" : menuList = menuRepo.findByNamaContainsIgnoreCase(value);
            case "path" : menuList = menuRepo.findByPathContainsIgnoreCase(value);
            case "group" : menuList = menuRepo.cariGroup(value);
            default : menuList = menuRepo.findAll();
        }

        List<RepMenuDTO> lt = converToRepMenuDTO(menuList);
        if(lt.isEmpty()){
            GlobalResponse.manualResponse(response,GlobalResponse.dataTidakDitemukan("USM02FV091",request));
            return;
        }

        Map<String,Object> map = new HashMap<>();// ini untuk menampung seluruh data yang akan di oper ke file html
        String strHtml = null;
        Context context = new Context();
        map.put("title","REPORT MENU");
        map.put("timestamp",LocalDateTime.now());
        map.put("totalData",lt.size());
        map.put("listContent",menuList);
        map.put("username",mapToken.get("namaLengkap"));
        context.setVariables(map);
        strHtml = springTemplateEngine.process("/report/menureport",context);
        pdfGenerator.htmlToPdf(strHtml,"menu",response);

    }

    public List<RepMenuDTO> converToRepMenuDTO(List<Menu> menus) {
        List<RepMenuDTO> lt = new ArrayList<>();
        for (Menu menu : menus) {
            Object object = menu.getGroupMenu();//untuk handling jika nilainya berisi null
            RepMenuDTO repMenuDTO = new RepMenuDTO();
            repMenuDTO.setId(menu.getId());
            repMenuDTO.setNama(menu.getNama());
            repMenuDTO.setPath(menu.getPath());
            repMenuDTO.setNamaGroup(object==null?"":menu.getGroupMenu().getNama());//ternary operator untuk handling null value
            repMenuDTO.setDeskripsiGroup(object==null?"":menu.getGroupMenu().getDeskripsi());//ternary operator untuk handling null value
            lt.add(repMenuDTO);
        }
        return lt;
    }

    public Menu converToEntity(ValMenuDTO valMenuDTO) {
        Menu menu = modelMapper.map(valMenuDTO, Menu.class);
        return menu;
    }
}