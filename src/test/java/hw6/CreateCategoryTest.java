package hw6;

import org.junit.jupiter.api.*;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

/**
 * Backend Java. Homework 6
 *
 * @author Vitalii Luzhnov
 * @version 31.05.2022
 */
public class CreateCategoryTest extends AbstractTest {

    String category;

    @Test
    @Tag("Positive")
    @DisplayName("Product creation (Positive)")
    void createProductTest() {
        category = "test";

        db.dao.CategoriesMapper categoriesMapper = session.getMapper(db.dao.CategoriesMapper.class);

        //находим
        db.model.CategoriesExample categoriesExample = new db.model.CategoriesExample();
        categoriesExample.createCriteria().andTitleLike(category);
        List<db.model.Categories> list = categoriesMapper.selectByExample(categoriesExample);
        assertThat(list.size(), equalTo(0));

        //создаем
        db.model.Categories categories = new db.model.Categories();
        categories.setTitle(category);
        categoriesMapper.insert(categories);
        session.commit();

        //находим
        categoriesExample = new db.model.CategoriesExample();
        categoriesExample.createCriteria().andTitleLike(category);
        list = categoriesMapper.selectByExample(categoriesExample);
        db.model.Categories selected = list.get(0);

        //проверяем
        assertThat(selected.getTitle(), equalTo(category));

        //удаляем
        categoriesMapper.deleteByPrimaryKey(selected.getId());
        session.commit();
    }
}
