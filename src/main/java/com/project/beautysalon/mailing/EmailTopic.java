package com.project.beautysalon.mailing;

public enum EmailTopic {
    REVIEW_REQUEST("mail.review-request.subject", "mail.review-request.body", 86400000),
    MASTER_ACCOUNT_CREATED("mail.master-account-created.subject", "mail.master-account-created.body", 0);

    private final String subject;
    private final String body;
    private final long delay;

    EmailTopic(String subject, String body, long delay) {
        this.subject = subject;
        this.body = body;
        this.delay = delay;
    }

    public String getSubject() {
        return subject;
    }

    public String getBody() {
        return body;
    }

    public long getDelay() {
        return delay;
    }
}
