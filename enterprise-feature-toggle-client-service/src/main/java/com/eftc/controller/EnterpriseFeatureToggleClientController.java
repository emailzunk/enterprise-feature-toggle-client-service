package com.eftc.controller;

import org.ff4j.FF4j;
import org.ff4j.exception.FeatureNotFoundException;
import org.ff4j.exception.PropertyNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.RequestScope;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Null;

@RestController
@RequestScope
public class EnterpriseFeatureToggleClientController {
    private static final Logger LOG = LoggerFactory.getLogger(EnterpriseFeatureToggleClientController.class);
    @Autowired
    private FF4j ff4j;
    @GetMapping("/feature/{fn}")
    public String CheckFeature(@PathVariable String fn) throws FeatureNotFoundException {
        String resp = "";
        try {
            if (ff4j.check(fn)) {
                LOG.info("List of Features is : " + ff4j.getFeatures());
                LOG.info(">>> Request is : " + fn + " : " + ff4j.check(fn));
                resp = " Feature : " + fn + " Found and its value is : Enabled";
            }
            else{
                resp = " Feature : " + fn + " Found and its value is : Not Enabled";
            }
        }
        catch (Exception exception){
            LOG.error(exception.getMessage() + " | Feature - " + fn + " Not Found.");
            resp = exception.getMessage() + " | Feature - " + fn + " Not Found.";
        }
        return(resp);
    }

    @GetMapping("/property/{prop}")
    public String CheckProperty(@PathVariable String prop) throws PropertyNotFoundException {
        String resp = "";
        LOG.info(" Property : " + resp);
        try {
            if (ff4j.getPropertyAsString(prop) != "") {
                LOG.info("List of Properties are : " + ff4j.getProperties());
                LOG.info(">>> Property " + prop + " - has the value : " + ff4j.getProperty(prop));
                resp = " Property " + prop + " - Found and its value is : " + ff4j.getProperty(prop);
           }
            else{
                resp = " Property " + prop + " - Not Found ";
            }
        }
        catch (Exception exception){
            LOG.error(exception.getMessage() + " | Property - " + prop + " Not Found.");
            resp = exception.getMessage() + " | Property - " + prop + " Not Found.";
        }
        return(resp);
    }
}
