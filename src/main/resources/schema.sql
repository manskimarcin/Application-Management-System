CREATE TABLE APPLICATION (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name  VARCHAR(400) NOT NULL,
    content VARCHAR(2000) NOT NULL,
    state ENUM('ACCEPTED', 'CREATED', 'DELETED', 'PUBLISHED', 'REJECTED', 'VERIFIED'),
    rejection_Reason VARCHAR(2000) NULL,
    publication_id BIGINT NULL
);