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
        MBSP_UPDATEDATE     DATE DEFAULT SYSDATE   NOT NULL,
        SNS_LOGIN_TYPE      VARCHAR2(10), -- SNS 로그인 타입
        
        CONSTRAINT PK_MBSP_ID PRIMARY KEY (MBSP_ID)
);
mbsp_id, mbsp_name, mbsp_email, mbsp_password, mbsp_zipcode, mbsp_addr, mbsp_deaddr, mbsp_phone, mbsp_nick, mbsp_receive, mbsp_point, mbsp_lastlogin, mbsp_createdate, mbsp_updatedate, sns_login_type
mbsp_tbl
pk_mbsp_id
-- 1.1. SNS테이블 추가
DROP TABLE SNS_USER_TBL;
CREATE TABLE SNS_USER_TBL (
    SNS_TYPE    VARCHAR2(10)    NOT NULL,
    ID          VARCHAR2(100),
    NAME        VARCHAR2(50)    NOT NULL,
    EMAIL       VARCHAR2(100)   NOT NULL,
    
    CONSTRAINT PK_SNS_ID PRIMARY KEY (ID)
);


--2.카테고리 테이블
DROP TABLE CATEGORY_TBL;
CREATE TABLE CATEGORY_TBL(
        CATE_CODE            NUMBER,    -- 카테고리 코드
        CATE_PRECODE         NUMBER    NULL,           -- 상위카테고리 코드
        CATE_NAME            VARCHAR2(50)    NOT NULL,
        
        CONSTRAINT PK_CATE_CODE PRIMARY KEY (CATE_CODE)
);


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
        PRO_UPDATEDATE      DATE DEFAULT SYSDATE    NOT NULL,
        revcount NUMBER DEFAULT 0,
        
        CONSTRAINTS PK_PRO_NUM PRIMARY KEY(PRO_NUM)
);

ALTER TABLE product_tbl DROP COLUMN revcount;

CREATE SEQUENCE SEQ_PRO_NUM;

COMMIT;


INSERT INTO PRODUCT_TBL (pro_num, CATE_CODE, PRO_NAME, PRO_PRICE, PRO_DISCOUNT, PRO_PUBLISHER, PRO_CONTENT, PRO_UP_FOLDER, PRO_IMG, PRO_AMOUNT, PRO_BUY)
VALUES (seq_pro_num.nextval, 15, '미니언', 300, 30, '미니언기획', '<img alt="" src="/admin/product/display/minion.jpg" style="height:168px; width:300px" />', '2024\07\07', 'b56b1da8-4022-46bf-a60b-b972ed188cc5_minion.jpg', 1, 'N');
    
INSERT INTO PRODUCT_TBL(pro_num, CATE_CODE, PRO_NAME, PRO_PRICE, PRO_DISCOUNT, PRO_PUBLISHER, PRO_CONTENT, PRO_UP_FOLDER, PRO_IMG, PRO_AMOUNT, PRO_BUY)
VALUES (seq_pro_num.nextval, 8, '원피스2', 35123, 38, '영쓰상상', '<img alt="" src="/admin/product/display/d751788979b54148834c3882ee0ee6d5_20230919153227.jpg" style="height:700px; width:700px" /><br /><br />
안녕? 이것은 원피스야~! 반가워!!!!', '2024\07\11', '1b613fca-7488-4553-a2e5-1eb9ec0f63f2_d751788979b54148834c3882ee0ee6d5_20230919153227.jpg', 30, 'N');

INSERT INTO PRODUCT_TBL(pro_num, CATE_CODE, PRO_NAME, PRO_PRICE, PRO_DISCOUNT, PRO_PUBLISHER, PRO_CONTENT, PRO_UP_FOLDER, PRO_IMG, PRO_AMOUNT, PRO_BUY)
VALUES (seq_pro_num.nextval, 8, '롱원피스1', 98765, 60, '영쓰상상', '<img alt="" src="/admin/product/display/d751788979b54148834c3882ee0ee6d5_20230919153227.jpg" style="height:700px; width:700px" /><br /><br />
안녕? 이것은 원피스야~! 반가워!!!!', '2024\07\11', 'b56b1da8-4022-46bf-a60b-b972ed188cc5_minion.jpg', 50, 'Y');

-- 상품마다 이미지가 여러개인 경우 이미지테이블 별도 생성


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
        CART_DATE       DATE            DEFAULT SYSDATE,
        
        CONSTRAINTS PK_CART_CODE PRIMARY KEY(CART_CODE)
);

CREATE SEQUENCE SEQ_CART_CODE;


ALTER TABLE CART_TBL
ADD CONSTRAINTS FK_CART_PRO_NUM
FOREIGN KEY(PRO_NUM)
REFERENCES PRODUCT_TBL(PRO_NUM);

ALTER TABLE CART_TBL
ADD CONSTRAINTS FK_CART_MBSP_ID
FOREIGN KEY(MBSP_ID)
REFERENCES MBSP_TBL(MBSP_ID);



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
        ORD_DESC            VARCHAR2(300)           NULL, -- 주문 시 요청사항
        ORD_REGDATE         DATE DEFAULT SYSDATE    NOT NULL,
        ORD_ADMIN_MEMO VARCHAR2(100),
        
        CONSTRAINTS PK_ORD_CODE PRIMARY KEY(ORD_CODE)
);

CREATE SEQUENCE seq_ord_code;


COMMIT;



ALTER TABLE ORDER_TBL
ADD CONSTRAINTS FK_ORDER_MBSP_ID
FOREIGN KEY(MBSP_ID)
REFERENCES MBSP_TBL(MBSP_ID);

--6. 주문상세 테이블 : 주문상품에 관한 내용
CREATE TABLE ORDETAIL_TBL(
        ORD_CODE        NUMBER      NOT NULL,
        PRO_NUM         NUMBER      NOT NULL,
        DT_AMOUNT       NUMBER      NOT NULL,
        DT_PRICE        NUMBER      NOT NULL,  -- 단위별 가격
        CONSTRAINTS PK_ORDETAIL_CODE_NUM PRIMARY KEY(ORD_CODE ,PRO_NUM)
);


ALTER TABLE ORDETAIL_TBL
ADD CONSTRAINTS FK_ORDETAIL_CODE
FOREIGN KEY(ORD_CODE)
REFERENCES ORDER_TBL(ORD_CODE);

ALTER TABLE ORDETAIL_TBL
ADD CONSTRAINTS FK_ORDETAIL_NUM
FOREIGN KEY(PRO_NUM)
REFERENCES PRODUCT_TBL(PRO_NUM));

--7.리뷰 테이블
DROP TABLE REVIEW_TBL;
CREATE TABLE REVIEW_TBL(
        REV_CODE        NUMBER,
        MBSP_ID        VARCHAR2(15)               NOT NULL,
        PRO_NUM        NUMBER                     NOT NULL,
        REV_TITLE       VARCHAR2(50)               NOT NULL,
        REV_CONTENT     VARCHAR2(200)              NOT NULL,
        REV_RATE        NUMBER                     NOT NULL,
        REV_REGDATE     DATE DEFAULT SYSDATE,
        
        CONSTRAINTS PK_REVIEW_CODE PRIMARY KEY(REV_CODE)
);


-- 시퀀스 생성
DROP SEQUENCE seq_rev_code;
CREATE SEQUENCE seq_rev_code;


INSERT INTO review_tbl(
    rev_code,
    mbsp_id,
    pro_num,
    rev_title,
    rev_content,
    rev_rate
)
VALUES(
    SEQ_REV_CODE.NEXTVAL,
    'user01',
    266,
    '상품후기1',
    '이것은 후기다! 후기내용1',
    3
);

COMMIT;



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
    ADMIN_VISIT_DATE    DATE    DEFAULT SYSDATE NOT NULL,
    
    CONSTRAINTS PK_ADMIN_ID PRIMARY KEY(ADMIN_ID)
);

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

-- 11. Q&A 테이블(resultmap 사용)
DROP TABLE qna_tbl;
DROP TABLE qnaboard_tbl;
CREATE TABLE qnaboard_tbl(
    qna_idx     NUMBER,
    mbsp_id     VARCHAR2(15)    NOT NULL,
    admin_id    VARCHAR2(15),
    pro_num     NUMBER          NOT NULL,
    question    VARCHAR2(1000)  NOT NULL,
    answer      VARCHAR2(1000)  NULL,
    anscheck    CHAR(1)         DEFAULT 'N',
    question_date   DATE DEFAULT SYSDATE    NOT NULL,
    answer_date DATE,
    
    CONSTRAINTS pk_qna_idx PRIMARY KEY (qna_idx)
);

DROP SEQUENCE seq_qno;
CREATE SEQUENCE seq_qna_idx;

COMMIT;



ALTER TABLE QNA_TBL
ADD CONSTRAINTS FK_QNA_ID
FOREIGN KEY(MBSP_ID)
REFERENCES MBSP_TBL(MBSP_ID);

ALTER TABLE QNA_TBL
ADD CONSTRAINTS FK_QNA_PRO_NUM
FOREIGN KEY(PRO_NUM)
REFERENCES PRODUCT_TBL(PRO_NUM);

-- 예치금 테이블

-- 12. 결제 테이블
DROP TABLE PAYINFO;
CREATE TABLE PAYINFO (
    P_ID        NUMBER  NOT NULL,
    ORD_CODE    NUMBER  NOT NULL,
    MBSP_ID     VARCHAR2(15)    NOT NULL,
    PAYMETHOD   VARCHAR2(50)    NOT NULL,
    PAYINFO     VARCHAR2(100)   NULL, -- 은행/ 계좌번호/ 예금주
    P_PRICE     NUMBER  NOT NULL,
    P_STATUS    VARCHAR2(10)    NOT NULL, -- 완납/미납
    P_DATE      DATE    DEFAULT SYSDATE,
    
    CONSTRAINTS PK_PAYINFO_IDX PRIMARY KEY (P_ID)
);

CREATE SEQUENCE seq_payinfo_id;

ALTER TABLE PAYINFO ADD CONSTRAINT FK_PAYINFO_ORD_CODE
FOREIGN KEY (ORD_CODE) REFERENCES ORDER_TBL(ORD_CODE);

-- 13. 메일발송
DROP TABLE mailmng_tbl;
CREATE TABLE mailmng_tbl (
    m_idx          NUMBER,
    m_title         VARCHAR2(200)      NOT NULL,
    m_explan     VARCHAR2(300),
    m_content    VARCHAR2(4000)      NOT NULL,
    m_gubun     VARCHAR2(30)      NOT NULL, -- 광고/이벤트 OR 일반
    reg_date      DATE    DEFAULT SYSDATE,
    admin_id      VARCHAR2(15) NOT NULL,
    
    m_sendcount NUMBER  DEFAULT 0,
    
    CONSTRAINTS pk_mailmng_idx PRIMARY KEY (m_idx)
);

CREATE SEQUENCE seq_mailmng_tbl;

SELECT
    m_idx,
    m_title,
    m_explan,
    m_content,
    m_gubun,
    reg_date,
    admin_id,
    m_sendcount
FROM
    mailmng_tbl;
DELETE FROM mailmng_tbl
WHERE
    m_idx = :v0
    AND m_title = :v1
    AND m_explan = :v2
    AND m_content = :v3
    AND m_gubun = :v4
    AND reg_date = :v5
    AND admin_id = :v6
    AND m_sendcount = :v7;


m_idx, m_title, m_content, m_gubun, reg_date, admin_id,

SELECT
    qna_idx,
    mbsp_id,
    admin_id,
    question,
    answer,
    anscheck,
    question_date,
    answer_date,
    pro_name,
    pro_up_folder,
    pro_img
FROM(
    SELECT /*+ INDEX_DESC(qnaboard_tbl pk_qna_idx) */
        ROWNUM AS rn,
        q.qna_idx,
        q.mbsp_id,
        q.admin_id,
        q.question,
        q.answer,
        q.anscheck,
        q.question_date,
        q.answer_date,
        P.pro_name,
        P.pro_up_folder,
        P.pro_img
    FROM
        qnaboard_tbl q
    INNER JOIN
        product_tbl p
    ON
        q.pro_num = p.pro_num
    WHERE
        ROWNUM <= (1 * 7)
    );

PRO_UP_FOLDER, PRO_IMG




SELECT
    rev_code,
    mbsp_id,
    pro_num,
    rev_title,
    rev_content,
    rev_rate,
    rev_regdate
FROM
    review_tbl;

SELECT /*+ USE_NL(r,p) INDEX_DESC(r pk_review_code) INDEX_DESC(p pk_pro_num) */
    ROWNUM AS rn,
    r.rev_code,
    r.mbsp_id,
    r.pro_num,
    r.rev_title,
    r.rev_content,
    r.rev_rate,
    r.rev_regdate,
    p.pro_name,
    p.pro_up_folder,
    p.pro_img
FROM
    review_tbl r
INNER JOIN
    product_tbl p
ON
    r.pro_num = p.pro_num
WHERE
    mbsp_id = 'user01';

r.REV_CODE,
r.MBSP_ID,
r.PRO_NUM,
r.REV_TITLE,
r.REV_CONTENT,
r.REV_RATE,
r.REV_REGDATE,
p.PRO_NAME,
p.PRO_UP_FOLDER,
p.PRO_IMG





-- 인덱스 힌트 위치가 바뀌면 정렬기준이 바뀌는데, 이번 건은 테이블 기준으로 1: 다 관계라서 뒤바꿨을 때 작동하지 않음.
-- 1) 이걸로 사용하자. 동작시켰을 때 이게 조금 더 시간 초가 짧다.
SELECT /*+ USE_NL(q,p) INDEX_DESC(q pk_qna_idx) INDEX_DESC(p pk_pro_num) */
    ROWNUM AS rn,
    q.qna_idx,
    q.mbsp_id,
    q.admin_id,
    q.question,
    q.answer,
    q.anscheck,
    q.question_date,
    q.answer_date,
    p.pro_name,
    p.pro_up_folder,
    p.pro_img
FROM
    qnaboard_tbl q
INNER JOIN
    product_tbl p
ON
    q.pro_num = p.pro_num;

-- 2)
-- 총 주문금액 같은거 join 쓰지 말고 아래 사용
SELECT /*+ INDEX_DESC(q pk_qna_idx) */
    ROWNUM AS rn,
    q.qna_idx,
    q.mbsp_id,
    q.admin_id,
    q.question,
    q.answer,
    q.anscheck,
    q.question_date,
    q.answer_date,
    (
        SELECT
            p.pro_img
        FROM
            product_tbl p
        WHERE
            q.pro_num = p.pro_num
    ),
        (
        SELECT
            p.pro_name
        FROM
            product_tbl p
        WHERE
            q.pro_num = p.pro_num
    ),
        (
        SELECT
            p.pro_up_folder
        FROM
            product_tbl p
        WHERE
            q.pro_num = p.pro_num
    )
    
FROM
    qnaboard_tbl q;
    
    
    
    
    
    
    

COMMIT;