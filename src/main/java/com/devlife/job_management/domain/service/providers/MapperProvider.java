package com.devlife.job_management.domain.service.providers;

import org.modelmapper.ModelMapper;


public class MapperProvider {

    /**
     * Method to map one object to another
     * @param source class that you want to map
     * @param out class that you want to map to
     * @return mapped object
     * @param <TOut> generic type of the output class
     * @param <TSource> generic type of the source class
     */

    public static <TSource, TOut> TOut fromTo(TSource source, Class<TOut> out) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(source, out);
    }

}
