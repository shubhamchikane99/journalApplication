package net.google.journalApp.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.google.journalApp.entity.DTOPlan;
import net.google.journalApp.entity.DTOUserMessageUsage;
import net.google.journalApp.entity.UserMessageUsage;
import net.google.journalApp.repository.DTOUserMessageUsageRepository;
import net.google.journalApp.repository.UserMessageUsageRepository;

@Service
public class UserMessageUsageService {

	@Autowired
	private UserMessageUsageRepository userMessageUsageRepository;

	@Autowired
	private DTOUserMessageUsageRepository dtoUserMessageUsageRepository;

	@Autowired
	private PlanService planService;

	public UserMessageUsage saveUserMessageUsage(UserMessageUsage userMessageUsage) {
		// save userMessageUsage

		DTOPlan activePlan = planService.getUserActivePlanByUserId(userMessageUsage.getSenderId());
		boolean useFreeMessage = true;

		if (activePlan != null) {

			int remainingDays = activePlan.getDaysCount();

			// if plan active
			if (remainingDays > 0) {
				useFreeMessage = false; // do NOT use free messages
			}
		}

		if (useFreeMessage) {

			DTOUserMessageUsage messageUse = dtoUserMessageUsageRepository
					.getUserMessageUsageByUserId(userMessageUsage.getSenderId(), userMessageUsage.getReceiverId());

			if (messageUse == null) {
				userMessageUsage.setDateTime(new Date());
				userMessageUsage.setMessageCount(1);
			} else {

				if (messageUse.getDaysCount() <= 30) {
					userMessageUsage.setId(messageUse.getId());
					userMessageUsage.setMessageCount(messageUse.getMessageCount() + 1);
					userMessageUsage.setInsertDateTime(messageUse.getInsertDateTime());
					userMessageUsage.setDateTime(messageUse.getDateTime());
				} else {
					userMessageUsage.setId(messageUse.getId());
					userMessageUsage.setMessageCount(1);
					userMessageUsage.setInsertDateTime(messageUse.getInsertDateTime());
					userMessageUsage.setDateTime(new Date());
				}
			}

			userMessageUsageRepository.save(userMessageUsage);
		}

		return userMessageUsage;
	}

	public UserMessageUsage getUserMessageUsageByUserId(String senderId, String receiverId) {
		// UserMessageUsage by id

		return userMessageUsageRepository.getUserMessageUsageByUserId(senderId, receiverId);
	}

}
