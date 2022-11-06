import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

public class TestCalculationCategory {

    @ParameterizedTest
    @DisplayName("для проверки ")
    @MethodSource("arguments")
    void testCalculationMaxSum(String category, int sum) {


    }

    private static Stream<Arguments> arguments() {
        return Stream.of(Arguments.of(""), Arguments.of(""));
    }
}
