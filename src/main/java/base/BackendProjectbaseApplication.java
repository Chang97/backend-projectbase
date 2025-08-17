package base;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("base.work.user")
public class BackendProjectbaseApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendProjectbaseApplication.class, args);
    }

}
