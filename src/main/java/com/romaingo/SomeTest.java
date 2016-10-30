package com.romaingo;

import static org.junit.Assert.*;

import javax.jcr.RepositoryException;

import org.junit.BeforeClass;
import org.junit.Test;

public class SomeTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Test
	public void test() throws RepositoryException {
		UnomiCaller.callUnomi("");
	}

}
