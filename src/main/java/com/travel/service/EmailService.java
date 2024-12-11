package com.travel.service;

import com.travel.dto.entrada.EmailDto;

public interface EmailService {

    void sendEmail(EmailDto email);
}
