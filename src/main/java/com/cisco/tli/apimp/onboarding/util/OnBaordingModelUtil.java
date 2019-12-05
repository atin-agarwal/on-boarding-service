/**
 * 
 */
package com.cisco.tli.apimp.onboarding.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

/**
 * @author atiagarw
 *
 */
@Component
@Slf4j
public class OnBaordingModelUtil {

	public void copy(MultipartFile source, String destination, String outputFileName) throws IOException {
		if (!Files.exists(Paths.get(destination))) {
			Files.createDirectories(Paths.get(destination));
		}
		log.info("Directory created");
		File fsFile = new File(destination, outputFileName);
		FileOutputStream os = new FileOutputStream(fsFile);
		FileCopyUtils.copy(source.getInputStream(), os);
		os.close();
	}

}
