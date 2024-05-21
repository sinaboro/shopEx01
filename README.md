"# shopEx01" 

1. springboot version
  : 2.7.1
```xml
<parent>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-parent</artifactId>
  <version>2.7.1</version>
  <relativePath/> <!-- lookup parent from repository -->
</parent>
```

2. java version
  : 11
```xml
<properties>
  <java.version>11</java.version>
</properties>
```

3.
이 프로젝트는 MySQL 데이터베이스를 사용합니다. MySQL 데이터베이스와의 연결을 위해 `mysql-connector-java` 라이브러리가 필요합니다. Maven을 사용하는 경우, `pom.xml` 파일에 다음 의존성을 추가하십시오:

```xml
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <scope>runtime</scope>
</dependency>
```