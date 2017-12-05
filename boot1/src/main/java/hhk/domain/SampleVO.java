package hhk.domain;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(exclude= {"val3"})
public class SampleVO {
	private String val1, val2, val3;
}