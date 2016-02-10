package com.sekulicd.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.couchbase.core.mapping.Document;

@Document
public class TeemT {
	
	@Id
	String id;

}
