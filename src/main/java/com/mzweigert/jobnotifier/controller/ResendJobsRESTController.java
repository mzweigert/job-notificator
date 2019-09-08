package com.mzweigert.jobnotifier.controller;

import com.mzweigert.jobnotifier.service.ResendJobsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
public class ResendJobsRESTController {

    @Value("${resend.password}")
    private String resendPassword;

    private final ResendJobsService resendJobsService;

    @Autowired
    public ResendJobsRESTController(ResendJobsService resendJobsService) {
        this.resendJobsService = resendJobsService;
    }

    @PostMapping(path = "/resend")
    public ResponseEntity<?> resend(@RequestParam String password) {
        if (Objects.equals(password, resendPassword)) {
            resendJobsService.resend();
            return new ResponseEntity<>("\n resend done \n", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("\n password for resend is not correct! \n", HttpStatus.FORBIDDEN);
        }
    }

}
