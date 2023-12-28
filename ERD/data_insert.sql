DELETE FROM recommend;
DELETE FROM post_comment;
DELETE FROM post_img;
DELETE FROM post;
DELETE FROM product_comment;
DELETE FROM product_img;
DELETE FROM product;
DELETE FROM zzim;
DELETE FROM together;
DELETE FROM kindergarden;
DELETE FROM child_house;
DELETE FROM user_img;
DELETE FROM user_authorities;
DELETE FROM user_authority;
DELETE FROM `user`;

ALTER TABLE recommend AUTO_INCREMENT = 1;
ALTER TABLE post_comment AUTO_INCREMENT = 1;
ALTER TABLE post_img AUTO_INCREMENT = 1;
ALTER TABLE post AUTO_INCREMENT = 1;
ALTER TABLE product_comment AUTO_INCREMENT = 1;
ALTER TABLE product_img AUTO_INCREMENT = 1;
ALTER TABLE product AUTO_INCREMENT = 1;
ALTER TABLE zzim AUTO_INCREMENT = 1;
ALTER TABLE together AUTO_INCREMENT = 1;
ALTER TABLE kindergarden AUTO_INCREMENT = 1;
ALTER TABLE child_house AUTO_INCREMENT = 1;
ALTER TABLE user_img AUTO_INCREMENT = 1;
ALTER TABLE user_authorities AUTO_INCREMENT = 1;
ALTER TABLE user_authority AUTO_INCREMENT = 1;
ALTER TABLE `user` AUTO_INCREMENT = 1;

-- 샘플 사용자
INSERT INTO user(loginId, nickname, name, password, email) VALUES
('1234', '바삭한해물파전', 'hmpj', '$2a$10$K6ipjV2LUKV2ncw3FE9wwe1PEn3lHepog5kKu/vutJ2K9HFLQ/12m', 'hmpj@mail.com')
;

INSERT INTO user_authority (authName) VALUES
('MEMBER'), 
('ADMIN')
;

INSERT INTO user_authorities (userId, authorityId) VALUES
(1, 2)
;

-- 샘플 사용자 프로필 이미지
INSERT INTO user_img(sourceName , fileName , userId) VALUES
('default.jpg', 'default.jpg', 1)
;

