//package com.juaracoding.service;
//
//
//import com.juaracoding.config.OtherConfig;
//import com.juaracoding.core.IReport;
//import com.juaracoding.core.IService;
//import com.juaracoding.dto.report.RepUserDTO;
//import com.juaracoding.dto.response.RespUserDTO;
//import com.juaracoding.dto.validation.ValUserDTO;
//import com.juaracoding.handler.GlobalResponse;
//import com.juaracoding.model.User;
//import com.juaracoding.repo.UserRepo;
//import com.juaracoding.security.RequestCapture;
//import com.juaracoding.util.*;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.modelmapper.ModelMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.multipart.MultipartFile;
//import org.thymeleaf.context.Context;
//import org.thymeleaf.spring6.SpringTemplateEngine;
//
//import java.text.SimpleDateFormat;
//import java.time.LocalDateTime;
//import java.util.*;
//
///**
// *  Platform Code  - USM
// *  Modul Code - 04
// *  FV - FE
// */
//@Service
//@Transactional
//public class UserService implements IService<User>, IReport<User> {
//
//    @Autowired
//    private UserRepo userRepo;
//
//    @Autowired
//    private ModelMapper modelMapper;
//
//    @Autowired
//    private TransformPagination transformPagination;
//
//    StringBuilder sBuild = new StringBuilder();
//
//    @Autowired
//    private SpringTemplateEngine springTemplateEngine;
//
//    @Autowired
//    private PdfGenerator pdfGenerator;
//
//
//    @Override
//    public ResponseEntity<Object> save(User user, HttpServletRequest request) {
//        Map<String,Object> mapToken = GlobalFunction.extractToken(request);
//        try {
//            user.setCreatedBy(Long.parseLong(mapToken.get("userId").toString()));
//            userRepo.save(user);
//        }catch (Exception e) {
//            LoggingFile.logException("UserService","save(User user, HttpServletRequest request) -- Line 59 "+RequestCapture.allRequest(request),e,OtherConfig.getEnableLog());
//            return GlobalResponse.dataGagalDisimpan("USM04FE001",request);
//        }
//        return GlobalResponse.dataBerhasilDisimpan(request);
//    }
//
//    @Override
//    public ResponseEntity<Object> update(Long id, User user, HttpServletRequest request) {
//        Map<String,Object> mapToken = GlobalFunction.extractToken(request);
//        if (user == null) {
//            return GlobalResponse.dataTidakValid("USM04FV011",request);
//        }
//
//        try {
//            Optional<User> optionalUser = userRepo.findById(id);
//            if (!optionalUser.isPresent()) {
//                return GlobalResponse.dataTidakDitemukan("USM04FV012",request);
//            }
//
//            User nextUser = optionalUser.get();
//            nextUser.setModifiedBy(Long.parseLong(mapToken.get("userId").toString()));
//            nextUser.setNama(user.getNama());
//            nextUser.setAlamat(user.getAlamat());
//            nextUser.setTanggalLahir(user.getTanggalLahir());
//            nextUser.setPassword(user.getPassword());
//            nextUser.setEmail(user.getEmail());
//            nextUser.setAkses(user.getAkses());
//            nextUser.setUsername(user.getUsername());
//            nextUser.setNoHp(user.getNoHp());
//        }catch (Exception e) {
//            LoggingFile.logException("UserService","update(Long id, User user, HttpServletRequest request) -- Line 81 "+RequestCapture.allRequest(request),e,OtherConfig.getEnableLog());
//            return GlobalResponse.dataGagalDiubah("USM04FE011",request);
//        }
//        return GlobalResponse.dataBerhasilDiubah(request);
//    }
//
//    @Override
//    public ResponseEntity<Object> delete(Long id, HttpServletRequest request) {
//        try {
//            Optional<User> optionalUser = userRepo.findById(id);
//            if (!optionalUser.isPresent()) {
//                return GlobalResponse.dataTidakDitemukan("USM04FV021",request);
//            }
//        }catch (Exception e) {
//            LoggingFile.logException("UserService","delete(Long id) -- Line 95 "+RequestCapture.allRequest(request),e,OtherConfig.getEnableLog());
//            return GlobalResponse.dataGagalDihapus("USM04FE021",request);
//        }
//        return GlobalResponse.dataBerhasilDihapus(request);
//    }
//
//    @Override
//    public ResponseEntity<Object> findAll(Pageable pageable, HttpServletRequest request) {
//        Page<User> page = null;
//        List<User> list = null;
//        page = userRepo.findAll(pageable);
//        list = page.getContent();
//        List<RepUserDTO> lt = converToRepUserDTO(list);
//
//        return GlobalResponse.dataDitemukan(transformPagination.transformPagination(lt,page,null,null),
//                request);
//    }
//
//    @Override
//    public ResponseEntity<Object> findById(Long id, HttpServletRequest request) {
//        Optional<User> optionalUser = null;
//        try {
//            optionalUser = userRepo.findById(id);
//            if (!optionalUser.isPresent()) {
//                return GlobalResponse.dataTidakDitemukan("USM04FV041",request);
//            }
//        }catch (Exception e) {
//            LoggingFile.logException("UserService","findById(Long id, HttpServletRequest request) -- Line 122 "+RequestCapture.allRequest(request),e,OtherConfig.getEnableLog());
//            return GlobalResponse.terjadiKesalahan("USM04FE041",request);
//        }
//        return GlobalResponse.dataDitemukan(modelMapper.map(optionalUser.get(), RespUserDTO.class),request);
//    }
//
//    @Override
//    public ResponseEntity<Object> findByParam(Pageable pageable, String columnName, String value, HttpServletRequest request) {
//
//        Page<User> page = null;
//        List<User> list = null;
//        switch (columnName) {
//            case "nama": page = userRepo.findByUsernameContainsIgnoreCase(pageable,value);break;
//            case "alamat": page = userRepo.findByAlamatContainsIgnoreCase(pageable,value);break;
//            case "email": page = userRepo.findByEmailContainsIgnoreCase(pageable,value);break;
//            case "noHp": page = userRepo.findByNoHpContainsIgnoreCase(pageable,value);break;
//            case "umur": page = userRepo.cariUmur(pageable,value);break;
//            default: page = userRepo.findAll(pageable);
//        }
//        list = page.getContent();
//        List<RepUserDTO> lt = converToRepUserDTO(list);
//
//        return GlobalResponse.dataDitemukan(transformPagination.transformPagination(lt,page,columnName,value),
//                request);
//    }
//
//    @Override
//    public ResponseEntity<Object> uploadDataExcel(MultipartFile multipartFile, HttpServletRequest request) {
//        Map<String,Object> mapToken = GlobalFunction.extractToken(request);
//        String message = "";
//        /** pengecekan file nya excel atau bukan */
//        if(!ExcelReader.hasWorkBookFormat(multipartFile)){
//            return GlobalResponse.formatHarusExcel("USM04FV061",request);
//        }
//        try{
//            List lt = new ExcelReader(multipartFile.getInputStream(),"Sheet1").getDataMap();
//            if(lt.isEmpty()){
//                return GlobalResponse.fileExcelKosong("USM04FV062",request);
//            }
//            userRepo.saveAll(convertListWorkBookToListEntity(lt,Long.parseLong(mapToken.get("userId").toString())));
//        }catch (Exception e) {
//            LoggingFile.logException("UserService","uploadDataExcel(MultipartFile multipartFile, HttpServletRequest request)  -- Line 161 "+RequestCapture.allRequest(request),e,OtherConfig.getEnableLog());
//            return GlobalResponse.formatHarusExcel("USM04FE061",request);
//        }
//        return GlobalResponse.uploadExcelBerhasil(request);
//    }
//
//    @Override
//    public List<User> convertListWorkBookToListEntity(List<Map<String, String>> workBookData, Long userId) {
//        List<User> list = new ArrayList<>();
//        for (Map<String, String> map : workBookData) {
//            User user = new User();
//            user.setNama(map.get("nama-user"));
//            user.setDeskripsi(map.get("deskripsi"));
//            user.setCreatedBy(userId);
//            list.add(user);
//        }
//        return list;
//    }
//
//    @Override
//    public void downloadReportExcel(String column, String value, HttpServletRequest request, HttpServletResponse response) {
//        List<User> userList = null;
//        switch (column){
//            case "nama" : userList = userRepo.findByNamaContainsIgnoreCase(value);
//            case "deskripsi" : userList = userRepo.findByDeskripsiContainsIgnoreCase(value);
//            default : userList = userRepo.findAll();
//        }
//
//        List<RepUserDTO> lt = converToRepUserDTO(userList);
//        if(lt.isEmpty()){
//            GlobalResponse.manualResponse(response,GlobalResponse.dataTidakDitemukan("USM04FV071",request));
//            return;
//        }
//
//        sBuild.setLength(0);
//        String headerKey = "Content-Disposition";
//        sBuild.setLength(0);
//
//        String headerValue = sBuild.append("attachment; filename=user_").
//                append(new SimpleDateFormat("dd-MM-yyyy_HH:mm:ss").
//                format(new Date())).append(".xlsx").toString();
//        response.setHeader(headerKey, headerValue);
//        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
//
//        Map<String,Object> map = GlobalFunction.convertClassToMap(new RepUserDTO());
//        List<String> listTemp = new ArrayList<>();
//        for (Map.Entry<String,Object> entry: map.entrySet()){
//            listTemp.add(entry.getKey());
//        }
//
//        int intListTemp = listTemp.size();
//        String [] headerArr = new String[intListTemp];//kolom judul di excel
//        String [] loopDataArr = new String[intListTemp]; // kolom judul untuk java reflection
//
//        /** Untuk mempersiapkan data judul kolomnya */
//        for (int i = 0; i < intListTemp; i++){
//            headerArr[i] = GlobalFunction.camelToStandard(listTemp.get(i));
//            loopDataArr[i] = listTemp.get(i);
//        }
//
//        /** Untuk mempersiapkan data body baris nyya */
//        int intListDTOSize = lt.size();
//        String [][] strBody = new String[intListDTOSize][intListTemp];
//        for (int i = 0; i < intListDTOSize; i++){
//            map = GlobalFunction.convertClassToMap(lt.get(i));
//            for(int j = 0; j < intListTemp; j++){
//                strBody[i][j] = String.valueOf(map.get(loopDataArr[j]));
//            }
//        }
//        new ExcelWriter(strBody,headerArr,"sheet-1",response);
//    }
//
//    @Override
//    public void generateToPDF(String column, String value, HttpServletRequest request, HttpServletResponse response) {
//        Map<String,Object> mapToken = GlobalFunction.extractToken(request);
//        List<User> userList = null;
//        switch (column){
//            case "nama" : userList = userRepo.findByNamaContainsIgnoreCase(value);
//            case "deskripsi" : userList = userRepo.findByDeskripsiContainsIgnoreCase(value);
//            default : userList = userRepo.findAll();
//        }
//
//        List<RepUserDTO> lt = converToRepUserDTO(userList);
//        if(lt.isEmpty()){
//            GlobalResponse.manualResponse(response,GlobalResponse.dataTidakDitemukan("USM04FV091",request));
//            return;
//        }
//        int intRespUserDTOList = lt.size();
//        Map<String,Object> map = new HashMap<>();// ini untuk menampung seluruh data yang akan di oper ke file html
//        String strHtml = null;
//        Context context = new Context();
//        Map<String,Object> mapColumnName = GlobalFunction.convertClassToMap(new RepUserDTO());// ini diubah
//        List<String> listTemp = new ArrayList<>();
//        List<String> listHelper = new ArrayList<>();
//        for (Map.Entry<String,Object> entry : mapColumnName.entrySet()) {
//            listTemp.add(GlobalFunction.camelToStandard(entry.getKey()));
//            listHelper.add(entry.getKey());
//        }
//        Map<String,Object> mapTemp = null;
//        List<Map<String,Object>> listMap = new ArrayList<>();
//        for(int i=0;i<intRespUserDTOList;i++){
//            mapTemp = GlobalFunction.convertClassToMap(lt.get(i));
//            listMap.add(mapTemp);
//        }
//
//
//        map.put("title","REPORT DATA AKSES");
//        map.put("listKolom",listTemp);
//        map.put("listHelper",listHelper);
//        map.put("timestamp",LocalDateTime.now());
//        map.put("totalData",intRespUserDTOList);
//        map.put("listContent",listMap);
//        map.put("username",mapToken.get("namaLengkap"));
//        context.setVariables(map);
//        strHtml = springTemplateEngine.process("global-report",context);
//        pdfGenerator.htmlToPdf(strHtml,"user",response);
//    }
//
//    public void generateToPDFManual(String column, String value, HttpServletRequest request, HttpServletResponse response) {
//        Map<String,Object> mapToken = GlobalFunction.extractToken(request);
//        List<User> userList = null;
//        switch (column){
//            case "nama" : userList = userRepo.findByNamaContainsIgnoreCase(value);
//            case "deskripsi" : userList = userRepo.findByDeskripsiContainsIgnoreCase(value);
//            default : userList = userRepo.findAll();
//        }
//
//        List<RepUserDTO> lt = converToRepUserDTO(userList);
//        if(lt.isEmpty()){
//            GlobalResponse.manualResponse(response,GlobalResponse.dataTidakDitemukan("USM04FV091",request));
//            return;
//        }
//
//        Map<String,Object> map = new HashMap<>();// ini untuk menampung seluruh data yang akan di oper ke file html
//        String strHtml = null;
//        Context context = new Context();
//        map.put("title","REPORT AKSES");
//        map.put("timestamp",LocalDateTime.now());
//        map.put("totalData",lt.size());
//        map.put("listContent",userList);
//        map.put("username",mapToken.get("namaLengkap"));
//        context.setVariables(map);
//        strHtml = springTemplateEngine.process("/report/userreport",context);
//        pdfGenerator.htmlToPdf(strHtml,"user",response);
//    }
//
//    public List<RepUserDTO> converToRepUserDTO(List<User> users) {
//        List<RepUserDTO> lt = new ArrayList<>();
//        for (User user : users) {
//            RepUserDTO repUserDTO = new RepUserDTO();
//            repUserDTO.setId(user.getId());
//            repUserDTO.setNama(user.getNama());
//            repUserDTO.setDeskripsi(user.getDeskripsi());
//            lt.add(repUserDTO);
//        }
//        return lt;
//    }
//
//    public User converToEntity(ValUserDTO valUserDTO) {
//        User user = modelMapper.map(valUserDTO, User.class);
//        return user;
//    }
//}