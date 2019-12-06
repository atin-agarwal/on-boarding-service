/**
 * 
 */
package com.cisco.tli.apimp.onboarding.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cisco.tli.apimp.onboarding.entity.OnBoardingEntity;
import com.cisco.tli.apimp.onboarding.entity.OnBoardingModelEntity;
import com.cisco.tli.apimp.onboarding.repository.OnBoardingModelRepository;
import com.cisco.tli.apimp.onboarding.repository.OnBoardingRepository;
import com.cisco.tli.apimp.onboarding.util.OnBaordingModelUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * @author atiagarw
 *
 */
@RestController
@RequestMapping("/v1/onboarding")
@Api(value = "Manage APIs")
@Slf4j
public class OnBoardingController {

	@Autowired
	private OnBoardingRepository repo;

	@Autowired
	private OnBoardingModelRepository modelRepo;

	@Autowired
	private OnBaordingModelUtil modelUtil;

	@PostMapping("/apis")
	@ApiOperation("Add API")
	public ResponseEntity<?> addAPI(@RequestBody OnBoardingEntity body) {
		OnBoardingEntity entity = repo.save(body);
		return ResponseEntity.status(HttpStatus.CREATED).body(entity);

	}

	@GetMapping("/apis")
	@ApiOperation("Get All API")
	public ResponseEntity<?> getAPIs() {
		return ResponseEntity.ok().body(repo.findAll());

	}

	@GetMapping("/apis/{apiId}")
	@ApiOperation("Get API")
	public ResponseEntity<?> getAPIById(@PathVariable("apiId") String apiId) {
		return ResponseEntity.ok().body(repo.findById(apiId));

	}

	@PutMapping("/apis/{apiId}")
	@ApiOperation("Update API")
	public ResponseEntity<?> updateAPIById(@PathVariable("apiId") String apiId, @RequestBody OnBoardingEntity body) {
		Optional<OnBoardingEntity> entity = repo.findById(apiId);
		body.setId(entity.get().getId());
		OnBoardingEntity newEntity = repo.save(body);
		return ResponseEntity.ok().body(newEntity);

	}

	@DeleteMapping("/apis/{apiId}")
	@ApiOperation("Delete API")
	public ResponseEntity<?> deleteAPIById(@PathVariable("apiId") String apiId) {
		repo.deleteById(apiId);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

	}

	@PostMapping("/models")
	@ApiOperation("Add ML Models")
	public ResponseEntity<?> addModel(@RequestParam("name") String name, @RequestParam("lib") List<String> lib,
			@RequestPart("modelFile") MultipartFile modelFile, @RequestPart("inputFile") MultipartFile inputFile,
			@RequestParam("sampleOutput") String sampleOutput) throws IOException, InterruptedException {

		// Installing lib
		log.info("Constructing install command");
		lib.stream().forEach(l -> {
			try {
				String[] cmd = new String[] { "/Library/Frameworks/Python.framework/Versions/3.8/bin/pip3", "install",
						l };
				Process pr = Runtime.getRuntime().exec(cmd);
				log.info("Executing install command");
				pr.waitFor();
				BufferedReader buf = new BufferedReader(new InputStreamReader(pr.getInputStream()));
				String line = "";
				log.info("Printing o/p....");
				while ((line = buf.readLine()) != null) {
					log.info("Output :: " + line);
				}
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}

		});

		// Saving to DB
		OnBoardingModelEntity entity = new OnBoardingModelEntity();
		entity.setId(UUID.randomUUID().toString());
		entity.setName(name);
		
		String modelFileName = modelFile.getOriginalFilename();
		entity.setModelFileName(modelFileName);
		entity.setModelFileSize(modelFile.getSize());
		entity.setModelFileExtension(modelFileName.substring(modelFileName.lastIndexOf(".") + 1));
		Path modelFilePath = Paths.get("/Users/atiagarw/tli/models", modelFileName);
		entity.setModelFileLocation(modelFilePath.toString());
		
		String inputFileName = inputFile.getOriginalFilename();
		entity.setInputFileName(inputFileName);
		entity.setInputFileSize(inputFile.getSize());
		entity.setInputFileExtension(inputFileName.substring(inputFileName.lastIndexOf(".") + 1));
		Path inputFilePath = Paths.get("/Users/atiagarw/tli/inputs", inputFileName);
		entity.setInputFileLocation(inputFilePath.toString());
		
		entity.setSampleOutput(sampleOutput);
		entity.setCreatedDate(new Date());
		entity.setModifiedDate(new Date());
		modelRepo.save(entity);

		// Saving model file to filesystem
		modelUtil.copy(modelFile, "/Users/atiagarw/tli/models", modelFileName);
		log.info("Saved model file to file system");
		
		// Saving model file to filesystem
		modelUtil.copy(inputFile, "/Users/atiagarw/tli/inputs", inputFileName);
		log.info("Saved input file to file system");
		return ResponseEntity.status(HttpStatus.CREATED).body(entity);

	}

	@GetMapping("/models")
	@ApiOperation("Get All Models")
	public ResponseEntity<?> getModels() {
		return ResponseEntity.ok().body(modelRepo.findAll(Sort.by("modifiedDate").descending()));

	}

}
