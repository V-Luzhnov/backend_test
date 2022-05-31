package hw6;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Backend Java. Homework 6
 *
 * @author Vitalii Luzhnov
 * @version 31.05.2022
 */
public class ExampleMain {

    public static void main( String[] args ) throws IOException {
        SqlSession session = null;
        try {
            String resource = "mybatis-config.xml";
            InputStream inputStream = Resources.getResourceAsStream(resource);
            SqlSessionFactory sqlSessionFactory = new
                    SqlSessionFactoryBuilder().build(inputStream);
            session = sqlSessionFactory.openSession();
            db.dao.CategoriesMapper categoriesMapper = session.getMapper(db.dao.CategoriesMapper.class);
            db.model.CategoriesExample example = new db.model.CategoriesExample();

            example.createCriteria().andIdEqualTo(1);
            List<db.model.Categories> list = categoriesMapper.selectByExample(example);
            System.out.println(categoriesMapper.countByExample(example));

            //создаем
            db.model.Categories categories = new db.model.Categories();
            categories.setTitle("test");
            categoriesMapper.insert(categories);
            session.commit();

            //находим
            db.model.CategoriesExample example2 = new db.model.CategoriesExample();
            example2.createCriteria().andTitleLike("test");
            List<db.model.Categories> list2 = categoriesMapper.selectByExample(example2);
            db.model.Categories categories2 = list2.get(0);

            //меняем
            categories2.setTitle("test_new");
            categoriesMapper.updateByPrimaryKey(categories2);
            session.commit();

            //удаляем
            categoriesMapper.deleteByPrimaryKey(categories2.getId());
            session.commit();

        } finally {
            assert session != null;
            session.close();
        }


    }
}
