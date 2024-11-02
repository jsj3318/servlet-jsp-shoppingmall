show databases;
use nhn_academy_43;
show tables;


CREATE TABLE IF NOT EXISTS `users` (
                                       `user_id` 		varchar(50) 	NOT NULL 		COMMENT '아이디',
    `user_name` 		varchar(50) 	NOT NULL 		COMMENT '이름',
    `user_password` 	varchar(200) 	NOT NULL 		COMMENT 'mysql password 사용',
    `user_birth` 		varchar(8) 		NOT NULL 		COMMENT '생년월일 : 19840503',
    `user_auth` 		varchar(10) 	NOT NULL 		COMMENT '권한: ROLE_ADMIN,ROLE_USER',
    `user_point` 		int 			NOT NULL 		COMMENT 'default : 1000000',
    `created_at` 		datetime 		NOT NULL 		COMMENT '가입 일자',
    `latest_login_at` datetime 		DEFAULT NULL 	COMMENT '마지막 로그인 일자',

    PRIMARY KEY (`user_id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='회원';

desc users;
select * from users;


CREATE TABLE IF NOT EXISTS `address` (
                                         `address_id` 	int auto_increment	  		comment '주소 인덱스' ,
                                         `user_id` 		varchar(50) 	not null	comment '회원 아이디',
    `address` 		varchar(100) 	not null	comment '주소지',

    PRIMARY KEY(`address_id`),
    FOREIGN KEY (`user_id`) REFERENCES users(`user_id`) ON DELETE CASCADE
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='회원 주소';

desc address;
select * from address;


CREATE TABLE IF NOT EXISTS `point_history` (
                                               `history_id`	int auto_increment	NOT NULL	COMMENT '포인트 이용 내역 인덱스',
                                               `user_id`		varchar(50)			NOT NULL	COMMENT '유저 아이디',
    `change_amount`	int					NOT NULL	COMMENT '증감량 (+적립 -사용)',
    `reason`		varchar(50)			NOT NULL	COMMENT '상품 구매, 포인트 사용, 출석 체크',
    `created_at`	datetime			NOT NULL	COMMENT '내역 생성 일시',

    PRIMARY KEY(`history_id`),
    FOREIGN KEY (`user_id`) REFERENCES users(`user_id`) ON DELETE CASCADE
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='포인트 내역';

desc point_history;
select * from point_history;


CREATE TABLE IF NOT EXISTS `category` (
                                          `category_id`	int auto_increment	NOT NULL	COMMENT '상품 카테고리 인덱스',
                                          `category_name`	varchar(50)			NOT NULL	COMMENT '카테고리 이름',

    PRIMARY KEY(`category_id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='카테고리';

desc category;
select * from category;


CREATE TABLE IF NOT EXISTS `product` (
                                         `product_id`	int auto_increment	NOT NULL	COMMENT '상품 인덱스',
                                         `product_name`	varchar(50)			NOT NULL	COMMENT '이름',
    `price`			decimal				NOT NULL	COMMENT '상품 가격',
    `description`	varchar(250)		NOT NULL	COMMENT '상품 설명 본문',
    `quantity`		int					NOT NULL	COMMENT '상품 재고',

    PRIMARY KEY(`product_id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='상품';

insert into product values(1, '콜라', 2000, '제로라임펩시콜라', 100);

desc product;
select * from product;


CREATE TABLE IF NOT EXISTS `product_category` (
                                                  `product_id`	int	NOT NULL	COMMENT '상품 인덱스',
                                                  `category_id`	int	NOT NULL	COMMENT '상품 카테고리 인덱스',

                                                  PRIMARY KEY(`product_id`, `category_id`),
    FOREIGN KEY (`product_id`) 	REFERENCES product(`product_id`) 	ON DELETE CASCADE,
    FOREIGN KEY (`category_id`) REFERENCES category(`category_id`) 	ON DELETE CASCADE
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='상품 카테고리 관계';

desc product_category;
select * from product_category;


CREATE TABLE IF NOT EXISTS `purchase` (
                                          `purchase_id`	int auto_increment	NOT NULL	COMMENT '주문 번호',
                                          `user_id`		varchar(50)			NOT NULL	COMMENT '주문한 고객 아이디',
    `purchased_at`	datetime			NOT NULL	COMMENT '주문 일시',
    `destination`	varchar(100)		NOT NULL	COMMENT '배송지 주소',
    `total_amount`	decimal	NOT NULL				COMMENT '주문 총 액',

    PRIMARY KEY(`purchase_id`),
    foreign key(`user_id`) references users(`user_id`) ON DELETE CASCADE
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='상품 카테고리 관계';

desc purchase;
select * from purchase;


CREATE TABLE IF NOT EXISTS `purchase_product` (
                                                  `purchase_id`	int auto_increment	NOT NULL	COMMENT '주문 번호',
                                                  `product_id`	int					NOT NULL	COMMENT '상품 인덱스',
                                                  `quantity`		int					NOT NULL	COMMENT '주문한 갯수',

                                                  PRIMARY KEY (`product_id`, `purchase_id`),
    foreign key(`purchase_id`) references purchase(`purchase_id`) ON DELETE CASCADE,
    foreign key(`product_id`) references product(`product_id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='상품 카테고리 관계';

desc purchase_product;
select * from purchase_product;
