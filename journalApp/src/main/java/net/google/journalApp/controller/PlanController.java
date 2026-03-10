package net.google.journalApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import net.google.journalApp.entity.Plan;
import net.google.journalApp.exception.ServiceResponse;
import net.google.journalApp.service.PlanService;

@RestController
@RequestMapping("v1/plan")
public class PlanController {

	@Autowired
	private PlanService planService;

	@PostMapping
	public ServiceResponse savePlan(@RequestBody Plan plan) {

		return ServiceResponse.asSuccess(planService.savePlan(plan));

	}

	@GetMapping("/get-all")
	public ServiceResponse getAllPlan() {

		return ServiceResponse.asSuccess(planService.getAllPlan());

	}

	@GetMapping("/active")
	public ServiceResponse getActivePlan() {

		return ServiceResponse.asSuccess(planService.getActivePlan());
	}

	@PostMapping("delete/{id}")
	public ServiceResponse deletePlanById(@PathVariable("id") String id) {

		return ServiceResponse.asSuccess(planService.deletePlanById(id));
	}

	@GetMapping("/by-user")
	public ServiceResponse getUserActivePlanByUserId(@RequestParam("userId") String userId) {

		return ServiceResponse.asSuccess(planService.getUserActivePlanByUserId(userId));
	}

}
