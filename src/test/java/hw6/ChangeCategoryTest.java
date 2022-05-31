package hw6;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

/**
 * Backend Java. Homework 6
 *
 * @author Vitalii Luzhnov
 * @version 31.05.2022
 */
public class ChangeCategoryTest extends AbstractTest {

    String category;
    String categoryNew;

    @Test
    @Tag("Positive")
    @DisplayName("Product creation (Positive)")
    void createProductTest() throws IOException {
        categoryNew = "test";

        db.dao.CategoriesMapper categoriesMapper = session.getMapper(db.dao.CategoriesMapper.class);

        //находим
        db.model.Categories selected = categoriesMapper.selectByPrimaryKey(1);

        category = selected.getTitle();

        //меняем
        selected.setTitle(categoryNew);
        categoriesMapper.updateByPrimaryKey(selected);
        session.commit();

        //проверяем
        assertThat(selected.getTitle(), equalTo(categoryNew));

        //меняем
        selected.setTitle(category);
        categoriesMapper.updateByPrimaryKey(selected);
        session.commit();

        //проверяем
        assertThat(selected.getTitle(), equalTo(category));
    }
}
