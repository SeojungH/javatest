package com.example;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StudyServiceTest {

	@Test
	void createNewStudy(@Mock MemberService memberService,
						@Mock StudyRepository studyRepository) {
		StudyService studyService = new StudyService(memberService, studyRepository);
		assertNotNull(studyService);

		Member member = new Member();
		member.setId(1L);
		member.setEmail("keesun@email.com");

		when(memberService.findById(any()))
				.thenReturn(Optional.of(member))
				.thenThrow(new RuntimeException())
				.thenReturn(Optional.empty());

		Optional<Member> byId = memberService.findById(1L);
		assertEquals("keesun@email.com", byId.get().getEmail());

		assertThrows(RuntimeException.class, () -> {
			memberService.findById(2L);
		});

		assertEquals(Optional.empty(), memberService.findById(3L));
	}

}