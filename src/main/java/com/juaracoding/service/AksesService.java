package com.juaracoding.service;


import com.juaracoding.config.OtherConfig;
import com.juaracoding.core.IReport;
import com.juaracoding.core.IService;
import com.juaracoding.dto.report.RepAksesDTO;
import com.juaracoding.dto.response.RespAksesDTO;
import com.juaracoding.dto.validation.ValAksesDTO;
import com.juaracoding.handler.GlobalResponse;
import com.juaracoding.model.Akses;
import com.juaracoding.repo.AksesRepo;
import com.juaracoding.security.RequestCapture;
import com.juaracoding.util.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.modelmapper.ModelMapper;
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
 *  Modul Code - 03
 *  FV - FE
 */
@Service
@Transactional
public class AksesService implements IService<Akses>, IReport<Akses> {

    @Autowired
    private AksesRepo aksesRepo;

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
    public ResponseEntity<Object> save(Akses akses, HttpServletRequest request) {
        Map<String,Object> mapToken = GlobalFunction.extractToken(request);
        try {
            akses.setCreatedBy(Long.parseLong(mapToken.get("userId").toString()));
            aksesRepo.save(akses);
        }catch (Exception e) {
            LoggingFile.logException("AksesService","save(Akses akses, HttpServletRequest request) -- Line 59 "+RequestCapture.allRequest(request),e,OtherConfig.getEnableLog());
            return GlobalResponse.dataGagalDisimpan("USM03FE001",request);
        }
        return GlobalResponse.dataBerhasilDisimpan(request);
    }

    @Override
    public ResponseEntity<Object> update(Long id, Akses akses, HttpServletRequest request) {
        Map<String,Object> mapToken = GlobalFunction.extractToken(request);
        if (akses == null) {
            return GlobalResponse.dataTidakValid("USM03FV011",request);
        }

        try {
            Optional<Akses> optionalAkses = aksesRepo.findById(id);
            if (!optionalAkses.isPresent()) {
                return GlobalResponse.dataTidakDitemukan("USM03FV012",request);
            }

            Akses nextAkses = optionalAkses.get();
            nextAkses.setModifiedBy(Long.parseLong(mapToken.get("userId").toString()));
            nextAkses.setNama(akses.getNama());
            nextAkses.setDeskripsi(akses.getDeskripsi());
            nextAkses.setLtMenu(akses.getLtMenu());

        }catch (Exception e) {
            LoggingFile.logException("AksesService","update(Long id, Akses akses, HttpServletRequest request) -- Line 81 "+RequestCapture.allRequest(request),e,OtherConfig.getEnableLog());
            return GlobalResponse.dataGagalDiubah("USM03FE011",request);
        }
        return GlobalResponse.dataBerhasilDiubah(request);
    }

    @Override
    public ResponseEntity<Object> delete(Long id, HttpServletRequest request) {
        try {
            Optional<Akses> optionalAkses = aksesRepo.findById(id);
            if (!optionalAkses.isPresent()) {
                return GlobalResponse.dataTidakDitemukan("USM03FV021",request);
            }
        }catch (Exception e) {
            LoggingFile.logException("AksesService","delete(Long id) -- Line 95 "+RequestCapture.allRequest(request),e,OtherConfig.getEnableLog());
            return GlobalResponse.dataGagalDihapus("USM03FE021",request);
        }
        return GlobalResponse.dataBerhasilDihapus(request);
    }

    @Override
    public ResponseEntity<Object> findAll(Pageable pageable, HttpServletRequest request) {
        Page<Akses> page = null;
        List<Akses> list = null;
        page = aksesRepo.findAll(pageable);
        list = page.getContent();
        List<RepAksesDTO> lt = converToRepAksesDTO(list);

        return GlobalResponse.dataDitemukan(transformPagination.transformPagination(lt,page,null,null),
                request);
    }

    @Override
    public ResponseEntity<Object> findById(Long id, HttpServletRequest request) {
        Optional<Akses> optionalAkses = null;
        try {
            optionalAkses = aksesRepo.findById(id);
            if (!optionalAkses.isPresent()) {
                return GlobalResponse.dataTidakDitemukan("USM03FV041",request);
            }
        }catch (Exception e) {
            LoggingFile.logException("AksesService","findById(Long id, HttpServletRequest request) -- Line 122 "+RequestCapture.allRequest(request),e,OtherConfig.getEnableLog());
            return GlobalResponse.terjadiKesalahan("USM03FE041",request);
        }
        return GlobalResponse.dataDitemukan(modelMapper.map(optionalAkses.get(), RespAksesDTO.class),request);
    }

    @Override
    public ResponseEntity<Object> findByParam(Pageable pageable, String columnName, String value, HttpServletRequest request) {

        Page<Akses> page = null;
        List<Akses> list = null;
        switch (columnName) {
            case "nama": page = aksesRepo.findByNamaContainsIgnoreCase(value,pageable);break;
            case "deskripsi": page = aksesRepo.findByDeskripsiContainsIgnoreCase(value,pageable);break;
            default: page = aksesRepo.findAll(pageable);
        }
        list = page.getContent();
        List<RepAksesDTO> lt = converToRepAksesDTO(list);

        return GlobalResponse.dataDitemukan(transformPagination.transformPagination(lt,page,columnName,value),
                request);
    }

    @Override
    public ResponseEntity<Object> uploadDataExcel(MultipartFile multipartFile, HttpServletRequest request) {
        Map<String,Object> mapToken = GlobalFunction.extractToken(request);
        String message = "";
        /** pengecekan file nya excel atau bukan */
        if(!ExcelReader.hasWorkBookFormat(multipartFile)){
            return GlobalResponse.formatHarusExcel("USM03FV061",request);
        }
        try{
            List lt = new ExcelReader(multipartFile.getInputStream(),"Sheet1").getDataMap();
            if(lt.isEmpty()){
                return GlobalResponse.fileExcelKosong("USM03FV062",request);
            }
            aksesRepo.saveAll(convertListWorkBookToListEntity(lt,Long.parseLong(mapToken.get("userId").toString())));
        }catch (Exception e) {
            LoggingFile.logException("AksesService","uploadDataExcel(MultipartFile multipartFile, HttpServletRequest request)  -- Line 161 "+RequestCapture.allRequest(request),e,OtherConfig.getEnableLog());
            return GlobalResponse.formatHarusExcel("USM03FE061",request);
        }
        return GlobalResponse.uploadExcelBerhasil(request);
    }

    @Override
    public List<Akses> convertListWorkBookToListEntity(List<Map<String, String>> workBookData, Long userId) {
        List<Akses> list = new ArrayList<>();
        for (Map<String, String> map : workBookData) {
            Akses akses = new Akses();
            akses.setNama(map.get("nama-akses"));
            akses.setDeskripsi(map.get("deskripsi"));
            akses.setCreatedBy(userId);
            list.add(akses);
        }
        return list;
    }

    @Override
    public void downloadReportExcel(String column, String value, HttpServletRequest request, HttpServletResponse response) {
        List<Akses> aksesList = null;
        switch (column){
            case "nama" : aksesList = aksesRepo.findByNamaContainsIgnoreCase(value);
            case "deskripsi" : aksesList = aksesRepo.findByDeskripsiContainsIgnoreCase(value);
            default : aksesList = aksesRepo.findAll();
        }

        List<RepAksesDTO> lt = converToRepAksesDTO(aksesList);
        if(lt.isEmpty()){
            GlobalResponse.manualResponse(response,GlobalResponse.dataTidakDitemukan("USM03FV071",request));
            return;
        }

        sBuild.setLength(0);
        String headerKey = "Content-Disposition";
        sBuild.setLength(0);

        String headerValue = sBuild.append("attachment; filename=akses_").
                append(new SimpleDateFormat("dd-MM-yyyy_HH:mm:ss").
                format(new Date())).append(".xlsx").toString();
        response.setHeader(headerKey, headerValue);
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

        Map<String,Object> map = GlobalFunction.convertClassToMap(new RepAksesDTO());
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
        Map<String,Object> mapToken = GlobalFunction.extractToken(request);
        List<Akses> aksesList = null;
        switch (column){
            case "nama" : aksesList = aksesRepo.findByNamaContainsIgnoreCase(value);
            case "deskripsi" : aksesList = aksesRepo.findByDeskripsiContainsIgnoreCase(value);
            default : aksesList = aksesRepo.findAll();
        }

        List<RepAksesDTO> lt = converToRepAksesDTO(aksesList);
        if(lt.isEmpty()){
            GlobalResponse.manualResponse(response,GlobalResponse.dataTidakDitemukan("USM03FV091",request));
            return;
        }
        int intRespAksesDTOList = lt.size();
        Map<String,Object> map = new HashMap<>();// ini untuk menampung seluruh data yang akan di oper ke file html
        String strHtml = null;
        Context context = new Context();
        Map<String,Object> mapColumnName = GlobalFunction.convertClassToMap(new RepAksesDTO());// ini diubah
        List<String> listTemp = new ArrayList<>();
        List<String> listHelper = new ArrayList<>();
        for (Map.Entry<String,Object> entry : mapColumnName.entrySet()) {
            listTemp.add(GlobalFunction.camelToStandard(entry.getKey()));
            listHelper.add(entry.getKey());
        }
        Map<String,Object> mapTemp = null;
        List<Map<String,Object>> listMap = new ArrayList<>();
        for(int i=0;i<intRespAksesDTOList;i++){
            mapTemp = GlobalFunction.convertClassToMap(lt.get(i));
            listMap.add(mapTemp);
        }


        map.put("title","REPORT DATA AKSES");
        map.put("listKolom",listTemp);
        map.put("listHelper",listHelper);
        map.put("timestamp",LocalDateTime.now());
        map.put("totalData",intRespAksesDTOList);
        map.put("listContent",listMap);
        map.put("username",mapToken.get("namaLengkap"));
        context.setVariables(map);
        strHtml = springTemplateEngine.process("global-report",context);
        pdfGenerator.htmlToPdf(strHtml,"akses",response);
    }

    public void generateToPDFManual(String column, String value, HttpServletRequest request, HttpServletResponse response) {
        Map<String,Object> mapToken = GlobalFunction.extractToken(request);
        List<Akses> aksesList = null;
        switch (column){
            case "nama" : aksesList = aksesRepo.findByNamaContainsIgnoreCase(value);
            case "deskripsi" : aksesList = aksesRepo.findByDeskripsiContainsIgnoreCase(value);
            default : aksesList = aksesRepo.findAll();
        }

        List<RepAksesDTO> lt = converToRepAksesDTO(aksesList);
        if(lt.isEmpty()){
            GlobalResponse.manualResponse(response,GlobalResponse.dataTidakDitemukan("USM03FV091",request));
            return;
        }

        Map<String,Object> map = new HashMap<>();// ini untuk menampung seluruh data yang akan di oper ke file html
        String strHtml = null;
        Context context = new Context();
        map.put("title","REPORT AKSES");
        map.put("timestamp",LocalDateTime.now());
        map.put("totalData",lt.size());
        map.put("listContent",aksesList);
        map.put("username",mapToken.get("namaLengkap"));
        context.setVariables(map);
        strHtml = springTemplateEngine.process("/report/aksesreport",context);
        pdfGenerator.htmlToPdf(strHtml,"akses",response);
    }

    public List<RepAksesDTO> converToRepAksesDTO(List<Akses> aksess) {
        List<RepAksesDTO> lt = new ArrayList<>();
        for (Akses akses : aksess) {
            RepAksesDTO repAksesDTO = new RepAksesDTO();
            repAksesDTO.setId(akses.getId());
            repAksesDTO.setNama(akses.getNama());
            repAksesDTO.setDeskripsi(akses.getDeskripsi());
            lt.add(repAksesDTO);
        }
        return lt;
    }

    public Akses converToEntity(ValAksesDTO valAksesDTO) {
        Akses akses = modelMapper.map(valAksesDTO, Akses.class);
        return akses;
    }
}