import Server.CalculationCategory;
import Server.Category;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.*;
import java.util.stream.Stream;

public class TestCalculationCategory {


    private CalculationCategory calculationCategory;

    @BeforeEach
    void setUpApp() {
        calculationCategory = new CalculationCategory();
    }


    @ParameterizedTest
    @DisplayName("для проверки метода определения максимальной категории")
    @MethodSource("arguments")
    void testSetMaxCategory(List<Category> categoryList) {

        String expectedCategory = "еда";
        Integer expectedSum = 400;

        calculationCategory.setMaxCategory(categoryList);

        Assertions.assertEquals(expectedCategory, calculationCategory.getMaxCategory().getCategory());
        Assertions.assertEquals(expectedSum, calculationCategory.getMaxCategory().getSum());

    }

    private static Stream<Arguments> arguments() {
        return Stream.of(
                Arguments.of(Arrays.asList(

                                new Category("еда", 400),
                                new Category("одежда", 100),
                                new Category("быт", 50)
                        )
                ));
    }
}
