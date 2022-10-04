package com.example;

import java.time.LocalDateTime;

public class Study {
	private StudyStatus status = StudyStatus.DRAFT;
	private LocalDateTime openedDateTime;
	private int limit;
	private String name;

	private Study(int limit, String name){
		this.limit = limit;
		this.name = name;
	}
	public void publish() {
		this.openedDateTime = LocalDateTime.now();
		this.status = StudyStatus.OPENED;
	}

	public LocalDateTime getOpenedDateTime() {
		return openedDateTime;
	}

	public StudyStatus getStatus() {
		return this.status;
	}

	public Study (int limit) {
		if(limit < 0) {
			throw new IllegalArgumentException("limit 은 0보다 커야함");
		}
		this.limit = limit;

	}
	public int getLimit() {
		return limit;
	}


	public String getName() {
		return name;
	}


}
