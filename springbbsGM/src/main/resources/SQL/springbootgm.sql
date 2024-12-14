## DATABASE 생성 및 선택
DROP DATABASE IF EXISTS springbootgm;
CREATE DATABASE IF NOT EXISTS springbootgm;
use springbootgm;

# 카테고리
CREATE TABLE IF NOT EXISTS category(
category_code INTEGER AUTO_INCREMENT PRIMARY KEY,
category_name VARCHAR(10) NOT NULL
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

# 멤버
CREATE TABLE IF NOT EXISTS member(
id VARCHAR(20) PRIMARY KEY,
name VARCHAR(10) NOT NULL,
pass VARCHAR(100) NOT NULL,
email VARCHAR(30) NOT NULL,
mobile VARCHAR(13) NOT NULL,
zipcode  VARCHAR(5) NOT NULL,
address1  VARCHAR(80) NOT NULL,
address2  VARCHAR(60) NOT NULL,
email_get BOOLEAN NOT NULL,
reg_date  TIMESTAMP NOT NULL,
nickname VARCHAR(20) NOT NULL UNIQUE
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

# 게시판
CREATE TABLE IF NOT EXISTS board(
board_no INTEGER AUTO_INCREMENT PRIMARY KEY,
board_title VARCHAR(50) NOT NULL,
board_content VARCHAR(10000) NOT NULL,
board_pass VARCHAR(30) NOT NULL,
board_reg_date TIMESTAMP NOT NULL,
board_view INTEGER NOT NULL,
category_code INTEGER NOT NULL,
member_id VARCHAR(20) NOT NULL,
board_file1 VARCHAR(100),
board_like INTEGER DEFAULT 0,
board_dislike INTEGER DEFAULT 0,
CONSTRAINT member_id_fk FOREIGN KEY (member_id) REFERENCES member(id),
CONSTRAINT category_code_fk FOREIGN KEY (category_code) REFERENCES category(category_code)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

# 댓글
CREATE TABLE IF NOT EXISTS reply(
   reply_no INTEGER AUTO_INCREMENT PRIMARY KEY,
   board_no INTEGER NOT NULL,
   reply_content VARCHAR(500) NOT NULL,
   member_id VARCHAR(20) NOT NULL,
   reply_reg_date TIMESTAMP NOT NULL,
   reply_like INTEGER DEFAULT 0,
   reply_dislike INTEGER DEFAULT 0,
   CONSTRAINT board_no_reply_fk FOREIGN KEY(board_no) REFERENCES board(board_no),
   CONSTRAINT reply_member_id_fk FOREIGN KEY(member_id) REFERENCES member(id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


INSERT INTO category
VALUES (1, "축구");

INSERT INTO member
VALUES ('dgm0224', '도건민', '$2a$10$aWYm2BGI/0iMuemBeF4Y8.7WZeVKAoudv/VzgQx697lYlZgQxr/pe', 'dogun0224@naver.com', '01012345678', '12345', '경기도 군포시', '산본', 1, SYSDATE(), 'Thomas');

INSERT INTO board(board_title, board_content, board_pass, board_reg_date, board_view, category_code, member_id, board_file1, board_like, board_dislike)
VALUES ('내가 검붉은 피라고?', '그렇다. 사실 난 서울에서 태어났지. 태생이 수호신이야 난..', '1234', SYSDATE(), '10', 1, 'dgm0224', null, 90, 1);
INSERT INTO board(board_title, board_content, board_pass, board_reg_date, board_view, category_code, member_id, board_file1, board_like, board_dislike)
VALUES ('일류첸코, 강상우 이적썰?', '에바지;; 서울을 위해 뛰어야지;;', '1234', SYSDATE(), '16', 1, 'dgm0224', null, 90, 12);

SELECT *
FROM board;

INSERT INTO reply(board_no, reply_content, member_id, reply_reg_date, reply_like, reply_dislike)
VALUES (69, '당신은 서울라이팅 당한거야...', 'dgm0224', SYSDATE(), 20, 11);
INSERT INTO reply(board_no, reply_content, member_id, reply_reg_date, reply_like, reply_dislike)
VALUES (70, '응 절대 안가~', 'dgm0224', SYSDATE(), 12, 2);