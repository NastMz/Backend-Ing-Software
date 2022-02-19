package com.zhopy.userservice.utils.helpers;

import org.modelmapper.ModelMapper;

public class MapperHelper {
    public static ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
