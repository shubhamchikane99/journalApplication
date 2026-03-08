package net.google.journalApp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.google.journalApp.entity.ErrorMessage;
import net.google.journalApp.entity.Plan;
import net.google.journalApp.repository.PlanFeatureRepository;
import net.google.journalApp.repository.PlanRepository;

@Service
public class PlanService {

	@Autowired
	private PlanRepository planRepository;

	@Autowired
	private PlanFeatureRepository planFeatureRepository;

	public Plan savePlan(Plan plan) {
		// save plan

		planFeatureRepository.deletePlanFeatureByPlanId(plan.getId());

		return planRepository.save(plan);
	}

	public List<Plan> getAllPlan() {
		// get all plan

		return planRepository.findAll();
	}

	public List<Plan> getActivePlan() {
		// get active plan

		return planRepository.getActivePlan();
	}

	public ErrorMessage deletePlanById(String id) {
		// Journal Entry Delete By Id

		ErrorMessage errorMessage = new ErrorMessage();

		errorMessage.setError(true);
		errorMessage.setStatusCode(500);
		errorMessage.setErrorMessage("failed to delete.");

		int result = planRepository.deletePlanById(id);

		if (result > 0) {

			errorMessage.setError(false);
			errorMessage.setStatusCode(200);
			errorMessage.setErrorMessage("Delete Successfully.");
		}

		return errorMessage;
	}
}
