package com.ys.javatest;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.DisabledOnOs;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.AggregateWith;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.aggregator.ArgumentsAggregationException;
import org.junit.jupiter.params.aggregator.ArgumentsAggregator;
import org.junit.jupiter.params.converter.ArgumentConversionException;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.converter.SimpleArgumentConverter;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.junit.jupiter.api.Assumptions.assumingThat;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class StudyTest {



    @Test
    @DisplayName("스터디 만들기")
    void create_new_study() {

        String test_env = System.getenv("TEST_ENV");
        assumeTrue("LOCAL".equalsIgnoreCase(test_env));

        assumingThat("LOCAL".equalsIgnoreCase(test_env), () -> {
            System.out.println("test 시작");

        });


        assertTimeout(Duration.ofSeconds(10), () -> new Study(10));

    }

    @DisplayName("반복 테스트 ")
    @RepeatedTest(value = 10, name = "{displayName}, ! {currentRepetition} / {totalRepetitions}")
    void repeatTest(RepetitionInfo repetitionInfo) {
        System.out.println("반복 테스트 " +
                repetitionInfo.getCurrentRepetition() + " \n" +
                repetitionInfo.getTotalRepetitions());
    }

    @ParameterizedTest(name = "{index} {displayName} message = {0}")
    @EmptySource
    @ValueSource(strings = {"날씨가", "많이", "추워지고", "있습니다."})
    void parameterizedTest(String message) {
        System.out.println(message);
    }

    @DisplayName("컨버터 테스트")
    @ParameterizedTest(name = "{index} {displayName} message = {0}")
    @CsvSource({"10, '자바 스터디'", "20, 스프링"})
        // '' 공백을 포함한 문자열을 넣을때 사용
    void parameterizedTest3(ArgumentsAccessor argumentsAccessor) {
        Study study =
                new Study(argumentsAccessor.getInteger(0), argumentsAccessor.getString(1));
        System.out.println(study);
    }

    static class StudyAggregator implements ArgumentsAggregator {
        @Override
        public Object aggregateArguments(ArgumentsAccessor argumentsAccessor,
                                         ParameterContext parameterContext)
                throws ArgumentsAggregationException {

            return new Study(argumentsAccessor.getInteger(0), argumentsAccessor.getString(1));
        }
    }

    @DisplayName("컨버터 테스트")
    @ParameterizedTest(name = "{index} {displayName} message = {0}")
    @CsvSource({"10, '자바 스터디'", "20, 스프링"})// '' 공백을 포함한 문자열을 넣을때 사용
    void parameterizedTest4(@AggregateWith(StudyAggregator.class) Study study) {
        System.out.println(study);
    }



    static class StudyConverter extends SimpleArgumentConverter {

        @Override
        protected Object convert(Object o, Class<?> aClass) throws ArgumentConversionException {
            assertEquals(Study.class, aClass, "Can only convert to Study");
            return new Study(Integer.parseInt(o.toString()));
        }
    }

    @DisplayName("컨버터 테스트")
    @ParameterizedTest(name = "{index} {displayName} message = {0}")
    @ValueSource(ints = {10, 20, 40})
    void parameterizedTest2(@ConvertWith(StudyConverter.class) Study study) {
        System.out.println(study.getLimit());
    }


    @Test
    @DisplayName("fast 그룹 테스트 1")
    @Tag("fast")
    void test_fast1() {
    }

    @FastTest
    @DisplayName("fast 그룹 테스트 1")
    void custom_test_fast1() {
    }


    @Test
    @DisplayName("테스트2")
    @EnabledOnOs({OS.WINDOWS, OS.MAC, OS.LINUX})
    void test2_windows() {
        System.out.println("테스트 2");
    }

    @Test
    @DisplayName("테스트3")
    @DisabledOnOs({OS.MAC, OS.WINDOWS})
    void test2_mac() {
        System.out.println("테스트 3");
    }


    @Test
    void create_new_study_again() {

    }

}