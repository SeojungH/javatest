package com.example;

import java.util.Optional;

public class StudyService {

	private final MemberService memberService;

	private final StudyRepository repository;

	public StudyService(MemberService memberService, StudyRepository repository) {
		this.memberService = memberService;
		this.repository = repository;
	}

	public Study createNewStudy(Long memberId, Study study) {
		Optional<Member> member = memberService.findById(memberId);
		study.setOwner(member.orElseThrow(() -> new IllegalArgumentException("Member d")));
		Study newstudy = repository.save(study);
		memberService.notify(newstudy);
		return newstudy;
	}

	public Study openStudy(Study study) {
		study.open();
		Study openedStudy = repository.save(study);
		memberService.notify(openedStudy);
		return openedStudy;
	}
}