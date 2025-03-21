package com.juaracoding.util;

import com.juaracoding.dto.response.MenuLoginDTO;
import com.juaracoding.dto.response.MenuMappingDTO;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeToken;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class TransformationData {
    private int intListMenuSize = 0;
    private final List<Object> lObj = new ArrayList<>();
    private ModelMapper modelMapper;
    PropertyMap<MenuLoginDTO, MenuMappingDTO> propMapMenuToLogin;
    public TransformationData() {
        this.modelMapper = new ModelMapper();
        propMapMenuToLogin = new PropertyMap<MenuLoginDTO,MenuMappingDTO>() {
            protected void configure() {
                map().setNamaGroupMenu(source.getGroupMenu().getNamaGroupMenu());
            }
        };
        modelMapper.addMappings(propMapMenuToLogin);
    }

    public List<Object> doTransformAksesMenuLogin(List<MenuLoginDTO> listMenu){
        lObj.clear();
        intListMenuSize = listMenu.size();
        /**
         * Grouping berdasarkan field getNamaGroupMenu
         */
        List<MenuMappingDTO> z = modelMapper.map(listMenu, new TypeToken<List<MenuMappingDTO>>() {}.getType());
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
}