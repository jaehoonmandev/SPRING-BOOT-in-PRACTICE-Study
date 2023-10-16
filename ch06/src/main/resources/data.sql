INSERT INTO COURSES(ID, NAME, CATEGORY, RATING, DESCRIPTION)  VALUES(1, 'Rapid Spring Boot Application Development', 'Spring', 4, 'Spring Boot gives all the power of the Spring Framework without all of the complexity');
INSERT INTO COURSES(ID, NAME, CATEGORY, RATING, DESCRIPTION)  VALUES(2, 'Getting Started with Spring Security DSL', 'Spring', 5, 'Learn Spring Security DSL in easy steps');
INSERT INTO COURSES(ID, NAME, CATEGORY, RATING, DESCRIPTION)  VALUES(3, 'Getting Started with Spring Cloud Kubernetes', 'Python', 3, 'Master Spring Boot application deployment with Kubernetes');

INSERT INTO CT_USERS(FIRST_NAME,LAST_NAME,EMAIL,USERNAME,PASSWORD, TOTP_ENABLED)
values ('SUPER', 'MAN', 'good@dc.comics', 'a', '$2a$10$eWFioIGVqV7lS9ogKJj7zOpDqLBfFeLBOQHqu/KcqSzXr79B8iPJ2', false)