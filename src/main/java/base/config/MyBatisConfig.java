package base.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(basePackages = "base.work") // DAO 인터페이스 스캔
public class MyBatisConfig {

    private final DataSource dataSource;

    public MyBatisConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);

        // 필요 시 TypeAliasPackage 지정
        factoryBean.setTypeAliasesPackage("base.work.user");

        // 필요 시 Mapper 위치 지정
        factoryBean.setMapperLocations(
            new org.springframework.core.io.support.PathMatchingResourcePatternResolver()
                .getResources("classpath:mapper/**/*.xml")
        );

        return factoryBean.getObject();
    }
}