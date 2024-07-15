/*
작성자 : 유영
편집일 : 2024-06-17
프로젝트명 : 여성 의류 쇼핑몰
*/

-- 0. 한글데이터 크기
SELECT LENGTHB('영') FROM DUAL;

--1.회원가입 테이블
CREATE TABLE MBSP_TBL(
        MBSP_ID             VARCHAR2(15),
        MBSP_NAME           VARCHAR2(30)           NOT NULL,
        MBSP_EMAIL          VARCHAR2(50)           NOT NULL,
        MBSP_PASSWORD       CHAR(60)               NOT NULL,        -- 비밀번호 암호화 처리.
        MBSP_ZIPCODE        CHAR(5)                NOT NULL,
        MBSP_ADDR           VARCHAR2(100)          NOT NULL,
        MBSP_DEADDR         VARCHAR2(100)          NOT NULL,
        MBSP_PHONE          VARCHAR2(15)           NOT NULL,
        MBSP_NICK           VARCHAR2(30)           NOT NULL UNIQUE, -- 닉네임
        MBSP_RECEIVE        CHAR(1)                NOT NULL, -- Y/N
        MBSP_POINT          NUMBER DEFAULT 2000    NOT NULL,
        MBSP_LASTLOGIN      DATE                   NULL,
        MBSP_CREATEDATE     DATE DEFAULT SYSDATE   NOT NULL,
        MBSP_UPDATEDATE     DATE DEFAULT SYSDATE   NOT NULL
);

ALTER TABLE MBSP_TBL
ADD CONSTRAINT PK_MBSP_ID PRIMARY KEY (MBSP_ID);

-- SNS 로그인 타입
ALTER TABLE MBSP_TBL
ADD SNS_LOGIN_TYPE VARCHAR2(10);


-- 1.1. SNS테이블 추가
DROP TABLE SNS_USER_TBL;
CREATE TABLE SNS_USER_TBL (
    SNS_TYPE    VARCHAR2(10)    NOT NULL,
    ID          VARCHAR2(100),
    NAME        VARCHAR2(50)    NOT NULL,
    EMAIL       VARCHAR2(100)   NOT NULL
);

ALTER TABLE SNS_USER_TBL
ADD CONSTRAINT PK_SNS_ID PRIMARY KEY (ID);

COMMIT;


--2.카테고리 테이블
DROP TABLE CATEGORY_TBL;
CREATE TABLE CATEGORY_TBL(
        CATE_CODE            NUMBER,    -- 카테고리 코드
        CATE_PRECODE         NUMBER    NULL,           -- 상위카테고리 코드
        CATE_NAME            VARCHAR2(50)    NOT NULL
);

ALTER TABLE CATEGORY_TBL
ADD CONSTRAINT PK_CATE_CODE PRIMARY KEY (CATE_CODE);

ALTER TABLE CATEGORY_TBL
ADD CONSTRAINTS FK_CATEGORY_PCODE
FOREIGN KEY(CATE_PRICODE)
REFERENCES CATEGORY_TBL(CATE_CODE);


-- 2.1. 1차 카테고리
INSERT INTO category_tbl (cate_code, cate_precode, cate_name) 
    VALUES (1, NULL, '원피스');
INSERT INTO category_tbl (cate_code, cate_precode, cate_name) 
    VALUES (2, NULL, '아우터');
INSERT INTO category_tbl (cate_code, cate_precode, cate_name) 
    VALUES (3, NULL, '탑');
INSERT INTO category_tbl (cate_code, cate_precode, cate_name) 
    VALUES (4, NULL, '니트');
INSERT INTO category_tbl (cate_code, cate_precode, cate_name) 
    VALUES (5, NULL, '셔츠');
INSERT INTO category_tbl (cate_code, cate_precode, cate_name) 
    VALUES (6, NULL, '하의');
INSERT INTO category_tbl (cate_code, cate_precode, cate_name) 
    VALUES (7, NULL, '스커트');
-- 2.2. 2차 카테고리
INSERT INTO category_tbl (cate_code, cate_precode, cate_name) 
    VALUES (8, 1, '롱원피스');
INSERT INTO category_tbl (cate_code, cate_precode, cate_name) 
    VALUES (9, 1, '미니원피스');
INSERT INTO category_tbl (cate_code, cate_precode, cate_name) 
    VALUES (10, 1, '패턴원피스');
INSERT INTO category_tbl (cate_code, cate_precode, cate_name) 
    VALUES (11, 1, '뷔스티에');
INSERT INTO category_tbl (cate_code, cate_precode, cate_name) 
    VALUES (12, 2, '점퍼');
INSERT INTO category_tbl (cate_code, cate_precode, cate_name) 
    VALUES (13, 2, '코트');
INSERT INTO category_tbl (cate_code, cate_precode, cate_name) 
    VALUES (14, 2, '자켓');
INSERT INTO category_tbl (cate_code, cate_precode, cate_name) 
    VALUES (15, 2, '가디건');
INSERT INTO category_tbl (cate_code, cate_precode, cate_name) 
    VALUES (16, 3, '티셔츠');
INSERT INTO category_tbl (cate_code, cate_precode, cate_name) 
    VALUES (17, 3, '맨투맨&#38;후드');
INSERT INTO category_tbl (cate_code, cate_precode, cate_name) 
    VALUES (18, 3, '반팔티');
INSERT INTO category_tbl (cate_code, cate_precode, cate_name) 
    VALUES (19, 4, '니트티');
INSERT INTO category_tbl (cate_code, cate_precode, cate_name) 
    VALUES (20, 4, '니트가디건');
INSERT INTO category_tbl (cate_code, cate_precode, cate_name) 
    VALUES (21, 5, '블라우스');
INSERT INTO category_tbl (cate_code, cate_precode, cate_name) 
    VALUES (22, 5, '셔츠');
INSERT INTO category_tbl (cate_code, cate_precode, cate_name) 
    VALUES (23, 6, '데님');
INSERT INTO category_tbl (cate_code, cate_precode, cate_name) 
    VALUES (24, 6, '코튼');
INSERT INTO category_tbl (cate_code, cate_precode, cate_name) 
    VALUES (25, 6, '슬랙스');
INSERT INTO category_tbl (cate_code, cate_precode, cate_name) 
    VALUES (26, 6, '레깅스');
INSERT INTO category_tbl (cate_code, cate_precode, cate_name) 
    VALUES (27, 7, '롱스커트');
INSERT INTO category_tbl (cate_code, cate_precode, cate_name) 
    VALUES (28, 7, '미니스커트');
    
CREATE SEQUENCE SEQ_CATE_NUM;
DROP SEQUENCE SEQ_CATE_NUM;

COMMIT;

--3.상품 테이블
DROP TABLE PRODUCT_TBL;
CREATE TABLE PRODUCT_TBL(
        PRO_NUM             NUMBER,
        CATE_CODE           NUMBER                  NULL,
        PRO_NAME            VARCHAR2(100)            NOT NULL,
        PRO_PRICE           NUMBER                  NOT NULL,
        PRO_DISCOUNT        NUMBER                  NOT NULL,
        PRO_PUBLISHER       VARCHAR2(50)            NOT NULL,
        PRO_CONTENT         VARCHAR2(4000)  /* CLOB */NOT NULL,-- 내용이 4000BYTE 초과여부판단?
        PRO_UP_FOLDER       VARCHAR(50)             NOT NULL, -- 날짜폴더경로 예>2024\06\11
        PRO_IMG             VARCHAR(100)             NOT NULL,  -- 파일이름
        PRO_AMOUNT          NUMBER                  NOT NULL,
        PRO_BUY             CHAR(1)                 NOT NULL, -- 'Y' or 'N'
        PRO_DATE            DATE DEFAULT SYSDATE    NOT NULL,
        PRO_UPDATEDATE      DATE DEFAULT SYSDATE    NOT NULL
);

SELECT
    pro_num,
    cate_code,
    pro_name,
    pro_price,
    pro_discount,
    pro_publisher,
    pro_content,
    pro_up_folder,
    pro_img,
    pro_amount,
    pro_buy,
    pro_date,
    pro_updatedate
FROM
    product_tbl;


INSERT INTO PRODUCT_TBL (pro_num, CATE_CODE, PRO_NAME, PRO_PRICE, PRO_DISCOUNT, PRO_PUBLISHER, PRO_CONTENT, PRO_UP_FOLDER, PRO_IMG, PRO_AMOUNT, PRO_BUY)
VALUES (seq_pro_num.nextval, 15, '미니언', 300, 30, '미니언기획', '<img alt="" src="/admin/product/display/minion.jpg" style="height:168px; width:300px" />', '2024\07\07', 'b56b1da8-4022-46bf-a60b-b972ed188cc5_minion.jpg', 1, 'N');
commit;
SELECT
    cate_code,
    cate_precode,
    cate_name
FROM
    category_tbl;
    
INSERT INTO PRODUCT_TBL(pro_num, CATE_CODE, PRO_NAME, PRO_PRICE, PRO_DISCOUNT, PRO_PUBLISHER, PRO_CONTENT, PRO_UP_FOLDER, PRO_IMG, PRO_AMOUNT, PRO_BUY)
VALUES (seq_pro_num.nextval, 8, '원피스2', 35123, 38, '영쓰상상', '<img alt="" src="/admin/product/display/d751788979b54148834c3882ee0ee6d5_20230919153227.jpg" style="height:700px; width:700px" /><br /><br />
안녕? 이것은 원피스야~! 반가워!!!!', '2024\07\11', '1b613fca-7488-4553-a2e5-1eb9ec0f63f2_d751788979b54148834c3882ee0ee6d5_20230919153227.jpg', 30, 'N');

INSERT INTO PRODUCT_TBL(pro_num, CATE_CODE, PRO_NAME, PRO_PRICE, PRO_DISCOUNT, PRO_PUBLISHER, PRO_CONTENT, PRO_UP_FOLDER, PRO_IMG, PRO_AMOUNT, PRO_BUY)
VALUES (seq_pro_num.nextval, 8, '롱원피스1', 98765, 60, '영쓰상상', '<img alt="" src="/admin/product/display/d751788979b54148834c3882ee0ee6d5_20230919153227.jpg" style="height:700px; width:700px" /><br /><br />
안녕? 이것은 원피스야~! 반가워!!!!', '2024\07\11', 'b56b1da8-4022-46bf-a60b-b972ed188cc5_minion.jpg', 50, 'Y');

COMMIT;

SELECT /*+ INDEX_DESC(product_tbl PK_PRO_NUM)  */ 
    ROWNUM AS RN,
    pro_num,
    cate_code, 
    pro_name, 
    pro_price, 
    pro_discount, 
    pro_publisher, 
    pro_content, 
    pro_up_folder, 
    pro_img, 
    pro_amount, 
    pro_buy
FROM
    product_tbl
WHERE 
    pro_buy = 'Y';


    
SELECT
    pro_num,
    cate_code,
    pro_name,
    pro_price,
    pro_discount,
    pro_publisher,
    pro_content,
    pro_up_folder,
    pro_img,
    pro_amount,
    pro_buy,
    pro_date,
    pro_updatedate
FROM
    product_tbl;



-- 상품마다 이미지가 여러개인 경우 이미지테이블 별도 생성
CREATE SEQUENCE SEQ_PRO_NUM;

ALTER TABLE PRODUCT_TBL
ADD CONSTRAINTS PK_PRO_NUM
PRIMARY KEY(PRO_NUM);

ALTER TABLE PRODUCT_TBL
ADD CONSTRAINTS FK_PRODUCT_PCODE
FOREIGN KEY(CATE_CODE)
REFERENCES CATEGORY_TBL(CATE_CODE);

-- 상품마다 이미지의 개수가 다를 경우 별도의 테이블을 구성(권장)
-- 상품설명 컬럼에 웹에디터를 이용한 태그코드 내용이 저장된다.

--4.장바구니 테이블
CREATE TABLE CART_TBL(
        CART_CODE       NUMBER,
        PRO_NUM         NUMBER          NOT NULL,
        MBSP_ID         VARCHAR2(15)    NOT NULL,
        CART_AMOUNT     NUMBER          NOT NULL,
        CART_DATE       DATE            DEFAULT SYSDATE
);

CREATE SEQUENCE SEQ_CART_CODE;

ALTER TABLE CART_TBL
ADD CONSTRAINTS PK_CART_CODE
PRIMARY KEY(CART_CODE);
pk_cart_code
seq_cart_code

SELECT
    cart_code,
    pro_num,
    mbsp_id,
    cart_amount,
    cart_date
FROM
    cart_tbl;
COMMIT;

ALTER TABLE CART_TBL
ADD CONSTRAINTS FK_CART_PRO_NUM
FOREIGN KEY(PRO_NUM)
REFERENCES PRODUCT_TBL(PRO_NUM);

ALTER TABLE CART_TBL
ADD CONSTRAINTS FK_CART_MBSP_ID
FOREIGN KEY(MBSP_ID)
REFERENCES MBSP_TBL(MBSP_ID);


SELECT
    cart_code,
    pro_num,
    mbsp_id,
    cart_amount,
    cart_date
FROM
    cart_tbl;

SELECT
    pro_num,
    cate_code,
    pro_name,
    pro_price,
    pro_discount,
    pro_publisher,
    pro_content,
    pro_up_folder,
    pro_img,
    pro_amount,
    pro_buy,
    pro_date,
    pro_updatedate
FROM
    product_tbl;
    

SELECT
    c.cart_code,
    c.pro_num,
    c.mbsp_id,
    c.cart_amount,
    p.pro_up_folder,
    p.pro_img,
    p.pro_price,
    p.pro_name
FROM
    cart_tbl c
INNER JOIN
    product_tbl p
ON
    c.pro_num = p.pro_num
WHERE
    c.mbsp_id = #{mbsp_id}



--5.주문테이블 : 주문자에 대한 내용
CREATE TABLE ORDER_TBL(
        ORD_CODE            NUMBER,
        MBSP_ID             VARCHAR2(15)            NOT NULL,
        ORD_NAME            VARCHAR2(30)            NOT NULL,
        ORD_ADDR_ZIPCODE    CHAR(5)                 NOT NULL,
        ORD_ADDR_BASIC      VARCHAR2(50)            NOT NULL,
        ORD_ADDR_DETAIL     VARCHAR2(50)            NOT NULL,
        ORD_TEL             VARCHAR2(20)            NOT NULL,
        ORD_PRICE           NUMBER                  NOT NULL, -- 총주문금액. 선택
        ORD_REGDATE         DATE DEFAULT SYSDATE    NOT NULL
);

CREATE SEQUENCE SEQ_ORD_CODE;

ALTER TABLE ORDER_TBL
ADD CONSTRAINTS PK_ORD_CODE
PRIMARY KEY(ORD_CODE);

ALTER TABLE ORDER_TBL
ADD CONSTRAINTS FK_ORDER_MBSP_ID
FOREIGN KEY(MBSP_ID)
REFERENCES MBSP_TBL(MBSP_ID);

--6. 주문상세 테이블 : 주문상품에 관한 내용
CREATE TABLE ORDETAIL_TBL(
        ORD_CODE        NUMBER      NOT NULL,
        PRO_NUM         NUMBER      NOT NULL,
        DT_AMOUNT       NUMBER      NOT NULL,
        DT_PRICE        NUMBER      NOT NULL  -- 단위별 가격
);

ALTER TABLE ORDETAIL_TBL
ADD CONSTRAINTS PK_ORDETAIL_CODE_NUM
PRIMARY KEY(ORD_CODE ,PRO_NUM);

ALTER TABLE ORDETAIL_TBL
ADD CONSTRAINTS FK_ORDETAIL_CODE
FOREIGN KEY(ORD_CODE)
REFERENCES ORDER_TBL(ORD_CODE);

ALTER TABLE ORDETAIL_TBL
ADD CONSTRAINTS FK_ORDETAIL_NUM
FOREIGN KEY(PRO_NUM)
REFERENCES PRODUCT_TBL(PRO_NUM));

--7.리뷰 테이블
CREATE TABLE REVIEW_TBL(
        RE_CODE        NUMBER,
        MBSP_ID        VARCHAR2(15)               NOT NULL,
        PRO_NUM        NUMBER                     NOT NULL,
        RE_TITLE       VARCHAR2(50)               NOT NULL,
        RE_CONTENT     VARCHAR2(200)              NOT NULL,
        RE_RATE        NUMBER                     NOT NULL,
        RE_REGDATE     DATE DEFAULT SYSDATE
);

-- 시퀀스 생성
CREATE SEQUENCE SEQ_RE_CODE;

ALTER TABLE REVIEW_TBL
ADD CONSTRAINTS PK_REVIEW_CODE
PRIMARY KEY(RE_CODE);

ALTER TABLE REVIEW_TBL
ADD CONSTRAINTS FK_RE_CODE
FOREIGN KEY(MBSP_ID)
REFERENCES MBSP_TBL(MBSP_ID);

ALTER TABLE REVIEW_TBL
ADD CONSTRAINTS FK_RE_NUM
FOREIGN KEY(PRO_NUM)
REFERENCES PRODUCT_TBL(PRO_NUM);

--8.게시판 테이블
CREATE TABLE BOARD_TBL(
        BRD_NUM         NUMBER,
        MBSP_ID         VARCHAR2(15)            NOT NULL,
        BRD_TITLE       VARCHAR2(100)           NOT NULL,
        BRD_CONTENT     VARCHAR2(4000)          NOT NULL,
        BRD_REGDATE     DATE DEFAULT SYSDATE    NOT NULL
);

ALTER TABLE BOARD_TBL
ADD CONSTRAINTS PK_BOARD_BRD_NUM
PRIMARY KEY(BRD_NUM);

COMMIT;
--9.관리자(ADMIN)테이블
DROP TABLE ADMIN_TBL;
CREATE TABLE ADMIN_TBL (
    ADMIN_ID    VARCHAR2(15),
    ADMIN_PW    CHAR(60)        NOT NULL,
    ADMIN_VISIT_DATE    DATE    DEFAULT SYSDATE NOT NULL
);

ALTER TABLE ADMIN_TBL
ADD CONSTRAINTS PK_ADMIN_ID
PRIMARY KEY(ADMIN_ID);

INSERT INTO ADMIN_TBL
    (ADMIN_ID, ADMIN_PW)
VALUES
    ('admin', '$2a$10$zdD7K/srVWKaMkmTda/Bv.1nhkacaU05oNdyU3zO8ocvFtQiKS0fq');

-- 10. 공지사항 테이블
CREATE TABLE NOTICE(
    IDX         NUMBER,
    TITLE       VARCHAR2(50)    NOT NULL,
    CONTENT     VARCHAR2(1000)  NOT NULL,
    WRITER      VARCHAR2(15)    NOT NULL,
    READCOUNT   NUMBER  DEFAULT 0 NOT NULL,
    REGDATE     DATE    DEFAULT SYSDATE
);

ALTER TABLE NOTICE
ADD CONSTRAINTS PK_NOTICE_IDX
PRIMARY KEY (IDX);

ALTER TABLE NOTICE ADD CONSTRAINT FK_NOTICE_WRITER
FOREIGN KEY (MBSP_ID) REFERENCES ADMIN_TBL(ADMIN_ID);

-- 11. Q&A 테이블
CREATE TABLE QNA_TBL (
    QNO         NUMBER,
    MBSP_ID     VARCHAR2(15),
    PRO_NUM     NUMBER          NOT NULL,
    QCONTENT    VARCHAR2(1000),
    REGDATE     DATE            DEFAULT SYSDATE
);

CREATE SEQUENCE SEQ_QNO;

ALTER TABLE QNA_TBL
ADD CONSTRAINTS PK_QNA_QNO
PRIMARY KEY (QNO);

ALTER TABLE QNA_TBL
ADD CONSTRAINTS FK_QNA_ID
FOREIGN KEY(MBSP_ID)
REFERENCES MBSP_TBL(MBSP_ID);

ALTER TABLE QNA_TBL
ADD CONSTRAINTS FK_QNA_PRO_NUM
FOREIGN KEY(PRO_NUM)
REFERENCES PRODUCT_TBL(PRO_NUM);

-- 예치금 테이블



COMMIT;