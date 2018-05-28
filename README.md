# department

Here you can see small Gradle project named Department

This project shows some company model whith hyerarchical structure of unlimited nesting

# Some dependencies in project:
1) Spring-boot
2) Mybatis
3) Log4j
4) Springfox-swagger2
5) Postgresql

All API`s are documented by Springfox-swagger2 and are available at [localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

Also, you may need to change settings for DB connection, all them are lying at [application.properties](src/main/resources/application.properties) 

```
spring.datasource.url=jdbc:postgresql://localhost:5432/department
spring.datasource.username=postgres
spring.datasource.password=admin
```

[create.sql](SQLs/create.sql) and
[fill.sql](SQLs/fill.sql) are containing SQL scripts for creating tables, seq., etc, 2nd for filling in this tables by content
