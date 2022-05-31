package hw6;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import java.io.IOException;
import java.io.InputStream;

/**
 * Backend Java. Homework 6
 *
 * @author Vitalii Luzhnov
 * @version 31.05.2022
 */
public abstract class AbstractTest {

    static SqlSession session = null;

    @BeforeAll
    static void setUp() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        session = sqlSessionFactory.openSession();
    }

    @AfterAll
    static void tearDown() {
        assert session != null;
        session.close();
    }
}
