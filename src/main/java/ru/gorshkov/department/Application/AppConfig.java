package ru.gorshkov.department.Application;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import ru.gorshkov.department.Service.Mappers.DepartmentMapper;
import ru.gorshkov.department.Service.Mappers.EmployerMapper;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.sql.DataSource;

@Configuration
@EnableSwagger2
public class AppConfig {

    @Autowired
    private Environment environment;

    @Bean
    public DataSource getDataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(environment.getRequiredProperty("spring.datasource.driver-class-name"));
        dataSource.setUrl(environment.getRequiredProperty("spring.datasource.url"));
        dataSource.setUsername(environment.getRequiredProperty("spring.datasource.username"));
        dataSource.setPassword(environment.getRequiredProperty("spring.datasource.password"));
        return dataSource;
    }

    @Bean
    public DataSourceTransactionManager transactionManager() {
        return new DataSourceTransactionManager(getDataSource());
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(getDataSource());
        return sessionFactory.getObject();
    }

    @Bean
    public DepartmentMapper departmentMapperWithFactory() throws Exception {
        MapperFactoryBean<DepartmentMapper> mapperFactoryBean = new MapperFactoryBean<>();
        mapperFactoryBean.setMapperInterface(DepartmentMapper.class);
        mapperFactoryBean.setSqlSessionFactory(sqlSessionFactory());
        mapperFactoryBean.afterPropertiesSet();
        return mapperFactoryBean.getObject();
    }

    @Bean
    public EmployerMapper employerMapperWithFactory() throws Exception {
        MapperFactoryBean<EmployerMapper> mapperFactoryBean = new MapperFactoryBean<>();
        mapperFactoryBean.setMapperInterface(EmployerMapper.class);
        mapperFactoryBean.setSqlSessionFactory(sqlSessionFactory());
        mapperFactoryBean.afterPropertiesSet();
        return mapperFactoryBean.getObject();
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }
}
