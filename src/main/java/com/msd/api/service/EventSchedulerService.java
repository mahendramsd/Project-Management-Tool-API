package com.msd.api.service;

import com.msd.api.domain.Notification;
import com.msd.api.domain.User;
import com.msd.api.repositories.NotificationRepository;
import com.msd.api.repositories.UserRepository;
import com.msd.api.util.Status;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
public class EventSchedulerService {

    private static final Logger logger = LoggerFactory.getLogger(EventSchedulerService.class);
    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;
    private final FirebaseMessagingService firebaseMessagingService;

    private final JavaMailSender javaMailSender;

    public EventSchedulerService(NotificationRepository notificationRepository, UserRepository userRepository, FirebaseMessagingService firebaseMessagingService, JavaMailSender javaMailSender) {
        this.notificationRepository = notificationRepository;
        this.userRepository = userRepository;
        this.firebaseMessagingService = firebaseMessagingService;
        this.javaMailSender = javaMailSender;
    }


    @Scheduled(fixedDelay = 1000 * 60 * 5)
    public void sendNotification() {
        try {
            logger.info("EVENT TRIGGER :: Start ");
            List<Notification> notifications = notificationRepository.findAllByIsSend(false);
            if(!notifications.isEmpty()) {
                List<String> emails = new ArrayList<>();
                List<User> users = userRepository.findAllByStatus(Status.ACTIVE);
                for (User user: users) {
                    if(StringUtils.isNotEmpty(user.getEmail())) {
                        emails.add(user.getEmail());
                    }
                }
                String[] strings = emails.stream().toArray(String[]::new);
                for (Notification notification: notifications) {
                    sendEmail(strings,notification.getMassage());
                    notification.setIsSend(true);
                    // TODO Push Notification implementation
                    //Note note = new Note();
                    // Send a message to a specific recipient by using Token
                    // String Token = "##############################";
                   // firebaseMessagingService.sendNotification(note, Token);
                }
                notificationRepository.saveAll(notifications);
            }
        } catch (Exception e) {
            logger.error("EVENT TRIGGER :: ::{}", e.getMessage());
        }
    }

    /**
     * Send Email
     * @param sendTo
     * @param message
     */
    void sendEmail(String[] sendTo, String message) {
        SimpleMailMessage msg = new SimpleMailMessage();
        System.out.println(sendTo.toString());
        msg.setTo(sendTo);
        msg.setSubject("Notification");
        msg.setText(message);
        javaMailSender.send(msg);
    }
}
