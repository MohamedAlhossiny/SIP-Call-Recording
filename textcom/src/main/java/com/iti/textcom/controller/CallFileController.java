package com.iti.textcom.controller;

import com.iti.textcom.entity.CallLog;
import com.iti.textcom.repository.CallLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Controller
public class CallFileController {

    private static final Logger logger = LoggerFactory.getLogger(CallFileController.class);

    @Autowired
    private CallLogRepository callLogRepository;

    @GetMapping("/audio/{callId}")
    @ResponseBody
    public ResponseEntity<Resource> streamAudio(@PathVariable Long callId) {
        logger.info("Attempting to stream audio for callId: {}", callId);
        Optional<CallLog> callLogOptional = callLogRepository.findById(callId);

        if (callLogOptional.isEmpty() || callLogOptional.get().getPathWavFile() == null) {
            logger.warn("CallLog not found or Path_WAV_File is null for callId: {}", callId);
            return ResponseEntity.notFound().build();
        }

        CallLog call = callLogOptional.get();
        String wavFilePath = call.getPathWavFile();
        logger.info("Found WAV file path: {}", wavFilePath);

        Path audioPath = Paths.get(wavFilePath);
        Resource resource;
        try {
            resource = new UrlResource(audioPath.toUri());
            logger.info("Created UrlResource for path: {}", audioPath.toUri());
        } catch (MalformedURLException e) {
            logger.error("Malformed URL exception for path {}: {}", wavFilePath, e.getMessage());
            return ResponseEntity.badRequest().build();
        }

        if (resource.exists() && resource.isReadable()) {
            logger.info("Resource exists and is readable: {}", resource.getFilename());
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType("audio/wav"))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);
        } else {
            logger.warn("Resource does not exist or is not readable: {}", resource.getFilename());
            return ResponseEntity.notFound().build();
        }
    }
} 