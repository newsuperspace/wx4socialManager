package cc.natapp4.ddaig.test.utils;

import org.junit.Test;

import cc.natapp4.ddaig.utils.FileController;

public class TestFileController {

	@Test
	public void testDeleteFile(){
		boolean deleteFile = FileController.deleteFile("D:\\1.txt");
	}
}
