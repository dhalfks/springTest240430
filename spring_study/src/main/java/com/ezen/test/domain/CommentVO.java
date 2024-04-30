package com.ezen.test.domain;

import lombok.Setter;
import lombok.ToString;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Setter
@Getter
public class CommentVO {
	
	private int cno;
	private int bno;
	private String writer;
	private String content;
	private String reg_date;
	
}
