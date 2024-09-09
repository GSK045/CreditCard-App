package com.sm.card_app.service;

import java.util.List;

import com.sm.card_app.bean.CardRequestBean;
import com.sm.card_app.bean.PageBean;
import com.sm.card_app.entity.CardRequest;

import jakarta.mail.MessagingException;

public interface CardRequestService {

	CardRequestBean save(CardRequestBean cardApproval);

	CardRequestBean update(CardRequestBean cardApprovalBean, String statusType) throws MessagingException;

	CardRequestBean getById(int id);

	PageBean<List<CardRequestBean>> getAll(int page, int size, String status);

	List<CardRequest> getCreditCardRequest(String eamil);

}
