package com.monsterhunters.pa165.service.config;

import com.monsterhunters.pa165.PersistenceSampleApplicationContext;
import com.monsterhunters.pa165.service.LocationServiceImpl;
import com.monsterhunters.pa165.facade.LocationFacadeImpl;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.dozer.Mapper;
import org.springframework.context.annotation.Bean;
import org.dozer.DozerBeanMapper;
import org.springframework.context.annotation.ComponentScan;

// TODO: add also others services and facades impl into conponent scan

/**
 *
 * @author Tomas Durcak
 */
@Configuration
@Import(PersistenceSampleApplicationContext.class)
@ComponentScan(basePackageClasses = { LocationServiceImpl.class, LocationFacadeImpl.class})
public class MappingConfiguration {

    @Bean
    public Mapper dozer() {
        DozerBeanMapper mapper = new DozerBeanMapper();
        mapper.addMapping(new EntityDTOMapping());
        return mapper;
    }
}