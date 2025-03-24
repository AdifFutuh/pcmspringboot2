package com.juaracoding.util;

import com.juaracoding.dto.response.MenuLoginDTO;
import com.juaracoding.dto.response.MenuMappingDTO;
import com.juaracoding.model.Menu;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeToken;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class TransformationDataManual {
    private int intListMenuSize = 0;
    private final List<Object> lObj = new ArrayList<>();
    private ModelMapper modelMapper = new ModelMapper();

    public List<Object> doTransformAksesMenuLogin(List<MenuLoginDTO> listMenu){
        lObj.clear();
        intListMenuSize = listMenu.size();
        /**
         * Grouping berdasarkan field getNamaGroupMenu
         */
        List<MenuMappingDTO> z = convertToMenuMappingDTO(listMenu);
        Map<String, List<MenuMappingDTO>> map = groupBy(z, MenuMappingDTO::getNamaGroupMenu);
        Map<String ,Object> map2 = null;
        for (Map.Entry<String,List<MenuMappingDTO>> x:
                map.entrySet()) {
            map2 = new HashMap<>();
            map2.put("group",x.getKey());
            map2.put("subMenu",x.getValue());
            lObj.add(map2);
        }
        return lObj;
    }

    public <E, K> Map<K, List<E>> groupBy(List<E> list, Function<E, K> keyFunction) {
        return Optional.ofNullable(list)
                .orElseGet(ArrayList::new)
                .stream().skip(0)
                .collect(Collectors.groupingBy(keyFunction,Collectors.toList()));
    }

    private List<MenuMappingDTO> convertToMenuMappingDTO(List<MenuLoginDTO> list){
        List<MenuMappingDTO> lt = new ArrayList<>();
        for (MenuLoginDTO m : list) {
            MenuMappingDTO dto = new MenuMappingDTO();
            dto.setNama(m.getNama());
            dto.setPath(m.getPath());
            dto.setNamaGroupMenu(m.getGroupMenu().getNamaGroupMenu());
            lt.add(dto);
        }
        return lt;
    }
}