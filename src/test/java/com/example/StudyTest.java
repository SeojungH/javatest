package com.example;

import static org.junit.jupiter.api.Assertions.*;

import com.example.FastTest;
import com.example.SlowTest;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.EnabledOnJre;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.JRE;
import org.junit.jupiter.api.condition.OS;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.ArgumentConversionException;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.converter.SimpleArgumentConverter;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.context.annotation.EnableMBeanExport;

import java.time.Duration;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;

import static org.junit.jupiter.api.Assertions.*;

//@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@ExtendWith(FindSlowTestExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class StudyTest {

	int value = 1;

	@RegisterExtension
	static FindSlowTestExtension findSlowTestExtension =
			new FindSlowTestExtension(1000L);

	@Order(2)
	@FastTest
	@DisplayName("스터디 만들기 fast")
	@Tag("fast")
	void create_new_study() {

		//TODO ThreadLocal
		System.out.println(this);
		System.out.println(value++);
		String test_env = System.getenv("TEST_ENV");
		System.out.println(test_env);

		Study actual = new Study(1);
		assertThat(actual.getLimit()).isGreaterThan(0);

	}

	@Order(1)
	@SlowTest
	@Tag("slow")
	@DisplayName("스터디 만들기 slow")
	void create_new_study_again() throws InterruptedException {
		Thread.sleep(1005L);
		System.out.println(this);
		System.out.println(value++);
	}

	@Order(3)
	@DisplayName("스터디만들기")
	@RepeatedTest(value = 10, name = "{displayName}, {currentRepetition}/{totalRepetitions}")
	void repeatTest(RepetitionInfo repetitionInfo) {
		System.out.println("test" + repetitionInfo.getCurrentRepetition() + "/" +
				repetitionInfo.getTotalRepetitions());
	}

	@Order(4)
	@DisplayName("스터디 날씨")
	@ParameterizedTest(name = "{index} {displayName} message={0}")
	@ValueSource(ints = {10, 20, 40})
	@Disabled
	void parameterrizedTest(@ConvertWith(StudyConverter.class) Study study) {
		System.out.println(study.getLimit());
	}

	static class StudyConverter extends SimpleArgumentConverter {

		@Override
		protected Object convert(Object source, Class<?> targetType) throws ArgumentConversionException {
			assertEquals(Study.class, targetType, "Can only convert to Study");
			return new Study((Integer.parseInt(source.toString())));
		}
	}

	@BeforeAll
	static void beforeAll() {
		System.out.println("before all");
	}

	@AfterAll
	static void afterAll() {
		System.out.println("after all");
	}

	@BeforeEach
	void beforeEach() {
		System.out.println("Before each");
	}

	@AfterEach
	void afterEach() {
		System.out.println("After each");
	}
}