package com.navdeep.reviewMS.review.messaging;

import com.navdeep.reviewMS.review.Review;
import com.navdeep.reviewMS.review.dto.ReviewMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class ReviewMessageProducer {
    RabbitTemplate rabbitTemplate;

    public ReviewMessageProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessage(Review review){
        ReviewMessage reviewMessage=new ReviewMessage();
        reviewMessage.setDescription(review.getDescription());
        reviewMessage.setTitle(review.getTitle());
        reviewMessage.setCompanyId(review.getCompanyId());
        reviewMessage.setRating(review.getRating());
        reviewMessage.setId(review.getId());
        rabbitTemplate.convertAndSend("companyRatingQueue", reviewMessage);
    }
}
