package com.sm.card_app.serviceImpl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sm.card_app.bean.CardRequestBean;
import com.sm.card_app.bean.PageBean;
import com.sm.card_app.entity.CardRequest;
import com.sm.card_app.entity.Role;
import com.sm.card_app.entity.User;
import com.sm.card_app.exceptions.CardRequestAlreadyExistsException;
import com.sm.card_app.repository.CardRequestRepository;
import com.sm.card_app.repository.RoleRepository;
import com.sm.card_app.repository.UserRepository;
import com.sm.card_app.service.CardRequestService;
import com.sm.card_app.util.MailService;

import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional
public class CardRequestServiceImpl implements CardRequestService {

	@Autowired
	private CardRequestRepository cardRequestRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private MailService mailService;

	@Override
	public CardRequestBean save(CardRequestBean cardRequestBean) {
		log.info("Saving cardRequest {}", cardRequestBean);
		if (cardRequestBean.getCardApprovalStatus() == null
				|| ObjectUtils.isEmpty(cardRequestBean.getCardApprovalStatus())) {
			cardRequestBean.setCardApprovalStatus("Pending");
		}
		if (cardRequestBean.getApplicationDate() == null) {
			cardRequestBean.setApplicationDate(LocalDate.now());
		}
		// Check if a CardRequest with the same email or phone number already exists
		CardRequest existingCardRequest = cardRequestRepository.findByEmailOrPhoneNumber(cardRequestBean.getEmail(),
				cardRequestBean.getPhoneNumber());
		if (existingCardRequest != null) {
			if (!existingCardRequest.getCardApprovalStatus().equalsIgnoreCase("Rejected")) {
				if (existingCardRequest != null) {
					log.error("CardRequest with email {} or phone number {} already exists", cardRequestBean.getEmail(),
							cardRequestBean.getPhoneNumber());
					throw new CardRequestAlreadyExistsException(
							"A CardRequest with this email or phone number already exists.");
				}
			}
		}

		CardRequest cardRequest = beanToEntityBuilder(cardRequestBean);
		CardRequest savedCardRequest = cardRequestRepository.save(cardRequest);
		log.info("Saved cardRequest successfully {}", savedCardRequest);
		saveUser(cardRequestBean);
		cardRequestBean = entityToBean(savedCardRequest);
		return cardRequestBean;
	}

	public CardRequest beanToEntityBuilder(CardRequestBean cardRequestBean) {
		return CardRequest.builder().id(cardRequestBean.getId()).userName(cardRequestBean.getUserName())
				.phoneNumber(cardRequestBean.getPhoneNumber()).email(cardRequestBean.getEmail())
				.address(cardRequestBean.getAddress()).dateOfBirth(cardRequestBean.getDateOfBirth())
				.income(cardRequestBean.getIncome()).idProofNumber(cardRequestBean.getIdProofNumber())
				.cardApprovalStatus(cardRequestBean.getCardApprovalStatus())
				.applicationDate(cardRequestBean.getApplicationDate()).approvalDate(cardRequestBean.getApprovalDate())
				.reason(cardRequestBean.getReason()).build();
	}

	private void saveUser(CardRequestBean cardRequestBean) {
		log.info("Saving user {}", cardRequestBean.getUserName());
		User user = new User();
		user.setUsername(cardRequestBean.getUserName());
		user.setEmail(cardRequestBean.getEmail());
		user.setPassword(cardRequestBean.getPhoneNumber());
		Integer roleId = fetchRoleIdByRoleName();
		user.setRoleId(roleId);
		User dbUser = userRepository.save(user);
		try {
			mailService.cardRequestMail(dbUser);
		} catch (MessagingException e) {
			log.error("Error occurred while sending mail , msg : {}", e.getMessage());
			throw new RuntimeException(e);
		}
		log.info("User Saved successfully");
	}

	private Integer fetchRoleIdByRoleName() {
		String roleName = "Customer";
		Optional<Role> role = roleRepository.findByRoleName(roleName);
		log.info("Role Id {]", role.get().getId());
		return role.get().getId();
	}

//	@Override
//	public PageBean<List<CardRequestBean>> getAll(int page, int size, String status) {
//		try {
//			log.info("Retrieving all credit cardRequest");
//			Page<CardRequest> creditCardRequests = cardRequestRepository.findAll(PageRequest.of(page, size));
//			List<CardRequestBean> creditRequest;
//			if ("all".equalsIgnoreCase(status)) {
//				creditRequest = creditCardRequests.getContent().stream().map(this::entityToBean)
//						.collect(Collectors.toList());
//			} else {
//				creditRequest = creditCardRequests.getContent().stream()
//						.filter(cardRequest -> status.equalsIgnoreCase(cardRequest.getCardApprovalStatus()))
//						.map(this::entityToBean).collect(Collectors.toList());
//			}
//			PageBean<List<CardRequestBean>> pageDto = PageBean.<List<CardRequestBean>>builder()
//					.pageNo(creditCardRequests.getNumber()).pageSize(creditCardRequests.getSize())
//					.last(creditCardRequests.isLast()).first(creditCardRequests.isFirst())
//					.totalPages(creditCardRequests.getTotalPages()).totalRecords(creditCardRequests.getTotalElements())
//					.records(creditRequest).build();
//			return pageDto;
//		} catch (Exception exception) {
//			log.error("Error occured while fetching all cardRequest request", exception);
//			throw exception;
//		}
//	}

	@Override
	public PageBean<List<CardRequestBean>> getAll(int page, int size, String status) {
		try {
			log.info("Retrieving credit card requests");

			// Retrieve all data with pagination
			Page<CardRequest> creditCardRequests = cardRequestRepository.findAll(PageRequest.of(page, size));

			// Filter based on status
			List<CardRequest> filteredRequests;
			if ("all".equalsIgnoreCase(status)) {
				filteredRequests = creditCardRequests.getContent();
			} else {
				filteredRequests = creditCardRequests.getContent().stream()
						.filter(cardRequest -> status.equalsIgnoreCase(cardRequest.getCardApprovalStatus()))
						.collect(Collectors.toList());
			}

			// Calculate total number of records after filtering
			int totalRecords = filteredRequests.size();

			// Paginate the filtered data
			int fromIndex = Math.min(page * size, totalRecords);
			int toIndex = Math.min(fromIndex + size, totalRecords);
			List<CardRequest> paginatedRequests = filteredRequests.subList(fromIndex, toIndex);

			// Convert to beans
			List<CardRequestBean> creditRequestBeans = paginatedRequests.stream().map(this::entityToBean)
					.collect(Collectors.toList());

			// Build and return PageBean
			PageBean<List<CardRequestBean>> pageDto = PageBean.<List<CardRequestBean>>builder().pageNo(page)
					.pageSize(size).last(fromIndex + size >= totalRecords).first(page == 0)
					.totalPages((int) Math.ceil((double) totalRecords / size)).totalRecords((long) totalRecords)
					.records(creditRequestBeans).build();

			return pageDto;
		} catch (Exception exception) {
			log.error("Error occurred while fetching card request", exception);
			throw exception;
		}
	}

	@Override
	public CardRequestBean getById(int id) {
		log.info("Retrieving cardRequestBean By Id {}", id);
		CardRequest cardApprovalBean = cardRequestRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("No Record Found with given id"));
		return entityToBean(cardApprovalBean);
	}

	@Override
	public CardRequestBean update(CardRequestBean cardRequestBean, String statusType) throws MessagingException {
		CardRequestBean dbCardApprovalBean = getById(cardRequestBean.getId());
		if (ObjectUtils.isNotEmpty(dbCardApprovalBean)) {
			log.info("Update cardRequest {}", cardRequestBean);
			if (statusType.equalsIgnoreCase("A")) {
				cardRequestBean.setCardApprovalStatus("Approved");
				cardRequestBean.setApprovalDate(LocalDate.now());
				cardRequestBean.setReason(cardRequestBean.getReason());
				mailService.approvedCardMail(cardRequestBean);
			} else if (statusType.equalsIgnoreCase("R")) {
				cardRequestBean.setCardApprovalStatus("Rejected");
				cardRequestBean.setApprovalDate(LocalDate.now());
				cardRequestBean.setReason(cardRequestBean.getReason());
				mailService.rejectedCardMail(cardRequestBean);
			}
			CardRequest cardRequest = beanToEntity(cardRequestBean);
			return entityToBean(cardRequestRepository.save(cardRequest));
		} else {
			log.info("cardRequest with id {} not found", cardRequestBean);
			throw new IllegalArgumentException("cardRequest not found for updating");
		}
	}

	@Override
	public List<CardRequest> getCreditCardRequest(String email) {
		log.info("Fetching credit card request based on logged in user {}", email);

		// Log the trimmed email for debugging
		String trimmedEmail = email.trim();
		log.debug("Trimmed email: {}", trimmedEmail);

		List<CardRequest> cardRequests = cardRequestRepository.findByEmailContaining(trimmedEmail);

		// Log the size of the list and the first item if available
		log.info("Card requests found: {}", cardRequests.size());
		if (!cardRequests.isEmpty()) {
			log.info("First card request user name: {}", cardRequests.get(0).getUserName());
		}

		return cardRequests;
	}

	public CardRequest beanToEntity(CardRequestBean cardRequestBean) {
		return objectMapper.convertValue(cardRequestBean, CardRequest.class);
	}

	public CardRequestBean entityToBean(CardRequest cardRequest) {
		return objectMapper.convertValue(cardRequest, CardRequestBean.class);
	}

	public List<CardRequestBean> entityToBean(List<CardRequest> list) {
		List<CardRequestBean> beanList = new ArrayList<>();
		for (CardRequest cardRequest : list) {
			CardRequestBean cardRequestBean;
			cardRequestBean = objectMapper.convertValue(cardRequest, CardRequestBean.class);
			beanList.add(cardRequestBean);
		}
		return beanList;
	}

}
