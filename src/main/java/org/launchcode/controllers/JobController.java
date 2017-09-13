package org.launchcode.controllers;

import org.launchcode.models.*;
import org.launchcode.models.data.JobFieldData;
import org.launchcode.models.forms.JobForm;
import org.launchcode.models.data.JobData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.jar.Pack200;

/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping(value = "job")
public class JobController {

    private JobData jobData = JobData.getInstance();

    // The detail display for a given Job at URLs like /job?id=17
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(Model model, @RequestParam Integer id) {

        // TODO #1 - get the Job with the given ID and pass it into the view
        model.addAttribute("title", "Job Detail");
        model.addAttribute(new Job());
        Job someJob = JobData.getInstance().findById(id);

        model.addAttribute("jobs", someJob);
        return "job-detail";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute(new JobForm());
        return "new-job";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String add( @ModelAttribute @Valid JobForm jobForm, Errors errors, Model model) {

        // TODO #6 - Validate the JobForm model, and if valid, create a
        // new Job and add it to the jobData data store. Then
        // redirect to the job detail view for the new Job.

        if (errors.hasErrors()) {
            model.addAttribute("title", "Add Job");
            model.addAttribute(jobForm);
            return "new-job";
        }

        String name = jobForm.getName();
        Employer employer = JobData.getInstance().getEmployers().findById(jobForm.getEmployerId());
        Location location = JobData.getInstance().getLocations().findById(jobForm.getLocationId());
        PositionType positionType = JobData.getInstance().getPositionTypes().findById(jobForm.getPositionTypeId());
        CoreCompetency coreCompetency = JobData.getInstance().getCoreCompetencies().findById(jobForm.getCoreCompetencyId());


        Job newJob = new Job(name, employer, location, positionType, coreCompetency);
        model.addAttribute("jobs", newJob);
        jobData.add(newJob);
        return "job-detail";

        //model.addAttribute("name", jobForm.getName());
        //model.addAttribute("employer", jobForm.getEmployerId());
        //model.addAttribute("location", jobForm.getLocationId());
        //model.addAttribute("coreCompetency", jobForm.getCoreCompetencyId());
        //model.addAttribute("positionType", jobForm.getPositionTypeId());
    }
}
