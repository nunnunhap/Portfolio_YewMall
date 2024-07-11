/*
�ۼ��� : ����
������ : 2024-06-17
������Ʈ�� : ���� �Ƿ� ���θ�
*/

-- 0. �ѱ۵����� ũ��
SELECT LENGTHB('��') FROM DUAL;

--1.ȸ������ ���̺�
CREATE TABLE MBSP_TBL(
        MBSP_ID             VARCHAR2(15),
        MBSP_NAME           VARCHAR2(30)           NOT NULL,
        MBSP_EMAIL          VARCHAR2(50)           NOT NULL,
        MBSP_PASSWORD       CHAR(60)               NOT NULL,        -- ��й�ȣ ��ȣȭ ó��.
        MBSP_ZIPCODE        CHAR(5)                NOT NULL,
        MBSP_ADDR           VARCHAR2(100)          NOT NULL,
        MBSP_DEADDR         VARCHAR2(100)          NOT NULL,
        MBSP_PHONE          VARCHAR2(15)           NOT NULL,
        MBSP_NICK           VARCHAR2(30)           NOT NULL UNIQUE, -- �г���
        MBSP_RECEIVE        CHAR(1)                NOT NULL, -- Y/N
        MBSP_POINT          NUMBER DEFAULT 2000    NOT NULL,
        MBSP_LASTLOGIN      DATE                   NULL,
        MBSP_CREATEDATE     DATE DEFAULT SYSDATE   NOT NULL,
        MBSP_UPDATEDATE     DATE DEFAULT SYSDATE   NOT NULL
);

ALTER TABLE MBSP_TBL
ADD CONSTRAINT PK_MBSP_ID PRIMARY KEY (MBSP_ID);

-- SNS �α��� Ÿ��
ALTER TABLE MBSP_TBL
ADD SNS_LOGIN_TYPE VARCHAR2(10);


-- 1.1. SNS���̺� �߰�
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


--2.ī�װ� ���̺�
DROP TABLE CATEGORY_TBL;
CREATE TABLE CATEGORY_TBL(
        CATE_CODE            NUMBER,    -- ī�װ� �ڵ�
        CATE_PRECODE         NUMBER    NULL,           -- ����ī�װ� �ڵ�
        CATE_NAME            VARCHAR2(50)    NOT NULL
);

ALTER TABLE CATEGORY_TBL
ADD CONSTRAINT PK_CATE_CODE PRIMARY KEY (CATE_CODE);

ALTER TABLE CATEGORY_TBL
ADD CONSTRAINTS FK_CATEGORY_PCODE
FOREIGN KEY(CATE_PRICODE)
REFERENCES CATEGORY_TBL(CATE_CODE);


-- 2.1. 1�� ī�װ�
INSERT INTO category_tbl (cate_code, cate_precode, cate_name) 
    VALUES (1, NULL, '���ǽ�');
INSERT INTO category_tbl (cate_code, cate_precode, cate_name) 
    VALUES (2, NULL, '�ƿ���');
INSERT INTO category_tbl (cate_code, cate_precode, cate_name) 
    VALUES (3, NULL, 'ž');
INSERT INTO category_tbl (cate_code, cate_precode, cate_name) 
    VALUES (4, NULL, '��Ʈ');
INSERT INTO category_tbl (cate_code, cate_precode, cate_name) 
    VALUES (5, NULL, '����');
INSERT INTO category_tbl (cate_code, cate_precode, cate_name) 
    VALUES (6, NULL, '����');
INSERT INTO category_tbl (cate_code, cate_precode, cate_name) 
    VALUES (7, NULL, '��ĿƮ');
-- 2.2. 2�� ī�װ�
INSERT INTO category_tbl (cate_code, cate_precode, cate_name) 
    VALUES (8, 1, '�տ��ǽ�');
INSERT INTO category_tbl (cate_code, cate_precode, cate_name) 
    VALUES (9, 1, '�̴Ͽ��ǽ�');
INSERT INTO category_tbl (cate_code, cate_precode, cate_name) 
    VALUES (10, 1, '���Ͽ��ǽ�');
INSERT INTO category_tbl (cate_code, cate_precode, cate_name) 
    VALUES (11, 1, '�߽�Ƽ��');
INSERT INTO category_tbl (cate_code, cate_precode, cate_name) 
    VALUES (12, 2, '����');
INSERT INTO category_tbl (cate_code, cate_precode, cate_name) 
    VALUES (13, 2, '��Ʈ');
INSERT INTO category_tbl (cate_code, cate_precode, cate_name) 
    VALUES (14, 2, '����');
INSERT INTO category_tbl (cate_code, cate_precode, cate_name) 
    VALUES (15, 2, '�����');
INSERT INTO category_tbl (cate_code, cate_precode, cate_name) 
    VALUES (16, 3, 'Ƽ����');
INSERT INTO category_tbl (cate_code, cate_precode, cate_name) 
    VALUES (17, 3, '������&#38;�ĵ�');
INSERT INTO category_tbl (cate_code, cate_precode, cate_name) 
    VALUES (18, 3, '����Ƽ');
INSERT INTO category_tbl (cate_code, cate_precode, cate_name) 
    VALUES (19, 4, '��ƮƼ');
INSERT INTO category_tbl (cate_code, cate_precode, cate_name) 
    VALUES (20, 4, '��Ʈ�����');
INSERT INTO category_tbl (cate_code, cate_precode, cate_name) 
    VALUES (21, 5, '���콺');
INSERT INTO category_tbl (cate_code, cate_precode, cate_name) 
    VALUES (22, 5, '����');
INSERT INTO category_tbl (cate_code, cate_precode, cate_name) 
    VALUES (23, 6, '����');
INSERT INTO category_tbl (cate_code, cate_precode, cate_name) 
    VALUES (24, 6, '��ư');
INSERT INTO category_tbl (cate_code, cate_precode, cate_name) 
    VALUES (25, 6, '������');
INSERT INTO category_tbl (cate_code, cate_precode, cate_name) 
    VALUES (26, 6, '���뽺');
INSERT INTO category_tbl (cate_code, cate_precode, cate_name) 
    VALUES (27, 7, '�ս�ĿƮ');
INSERT INTO category_tbl (cate_code, cate_precode, cate_name) 
    VALUES (28, 7, '�̴Ͻ�ĿƮ');
    
CREATE SEQUENCE SEQ_CATE_NUM;
DROP SEQUENCE SEQ_CATE_NUM;

COMMIT;

--3.��ǰ ���̺�
DROP TABLE PRODUCT_TBL;
CREATE TABLE PRODUCT_TBL(
        PRO_NUM             NUMBER,
        CATE_CODE           NUMBER                  NULL,
        PRO_NAME            VARCHAR2(100)            NOT NULL,
        PRO_PRICE           NUMBER                  NOT NULL,
        PRO_DISCOUNT        NUMBER                  NOT NULL,
        PRO_PUBLISHER       VARCHAR2(50)            NOT NULL,
        PRO_CONTENT         VARCHAR2(4000)  /* CLOB */NOT NULL,-- ������ 4000BYTE �ʰ������Ǵ�?
        PRO_UP_FOLDER       VARCHAR(50)             NOT NULL, -- ��¥������� ��>2024\06\11
        PRO_IMG             VARCHAR(100)             NOT NULL,  -- �����̸�
        PRO_AMOUNT          NUMBER                  NOT NULL,
        PRO_BUY             CHAR(1)                 NOT NULL, -- 'Y' or 'N'
        PRO_DATE            DATE DEFAULT SYSDATE    NOT NULL,
        PRO_UPDATEDATE      DATE DEFAULT SYSDATE    NOT NULL
);

INSERT INTO PRODUCT_TBL (pro_num, CATE_CODE, PRO_NAME, PRO_PRICE, PRO_DISCOUNT, PRO_PUBLISHER, PRO_CONTENT, PRO_UP_FOLDER, PRO_IMG, PRO_AMOUNT, PRO_BUY)
VALUES (seq_pro_num.nextval, 15, '�̴Ͼ�', 300, 30, '�̴Ͼ��ȹ', '<img alt="" src="/admin/product/display/minion.jpg" style="height:168px; width:300px" />', '2024\07\07', 'b56b1da8-4022-46bf-a60b-b972ed188cc5_minion.jpg', 1, 'N');
commit;
SELECT
    cate_code,
    cate_precode,
    cate_name
FROM
    category_tbl;
    
INSERT INTO PRODUCT_TBL(pro_num, CATE_CODE, PRO_NAME, PRO_PRICE, PRO_DISCOUNT, PRO_PUBLISHER, PRO_CONTENT, PRO_UP_FOLDER, PRO_IMG, PRO_AMOUNT, PRO_BUY)
VALUES (seq_pro_num.nextval, 8, '���ǽ�2', 35123, 38, '�������', '<img alt="" src="/admin/product/display/d751788979b54148834c3882ee0ee6d5_20230919153227.jpg" style="height:700px; width:700px" /><br /><br />
�ȳ�? �̰��� ���ǽ���~! �ݰ���!!!!', '2024\07\11', '1b613fca-7488-4553-a2e5-1eb9ec0f63f2_d751788979b54148834c3882ee0ee6d5_20230919153227.jpg', 30, 'N');

INSERT INTO PRODUCT_TBL(pro_num, CATE_CODE, PRO_NAME, PRO_PRICE, PRO_DISCOUNT, PRO_PUBLISHER, PRO_CONTENT, PRO_UP_FOLDER, PRO_IMG, PRO_AMOUNT, PRO_BUY)
VALUES (seq_pro_num.nextval, 8, '�տ��ǽ�1', 98765, 60, '�������', '<img alt="" src="/admin/product/display/d751788979b54148834c3882ee0ee6d5_20230919153227.jpg" style="height:700px; width:700px" /><br /><br />
�ȳ�? �̰��� ���ǽ���~! �ݰ���!!!!', '2024\07\11', 'b56b1da8-4022-46bf-a60b-b972ed188cc5_minion.jpg', 50, 'Y');

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



-- ��ǰ���� �̹����� �������� ��� �̹������̺� ���� ����
CREATE SEQUENCE SEQ_PRO_NUM;

ALTER TABLE PRODUCT_TBL
ADD CONSTRAINTS PK_PRO_NUM
PRIMARY KEY(PRO_NUM);

ALTER TABLE PRODUCT_TBL
ADD CONSTRAINTS FK_PRODUCT_PCODE
FOREIGN KEY(CATE_CODE)
REFERENCES CATEGORY_TBL(CATE_CODE);

-- ��ǰ���� �̹����� ������ �ٸ� ��� ������ ���̺��� ����(����)
-- ��ǰ���� �÷��� �������͸� �̿��� �±��ڵ� ������ ����ȴ�.

--4.��ٱ��� ���̺�
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

--5.�ֹ����̺� : �ֹ��ڿ� ���� ����
CREATE TABLE ORDER_TBL(
        ORD_CODE            NUMBER,
        MBSP_ID             VARCHAR2(15)            NOT NULL,
        ORD_NAME            VARCHAR2(30)            NOT NULL,
        ORD_ADDR_ZIPCODE    CHAR(5)                 NOT NULL,
        ORD_ADDR_BASIC      VARCHAR2(50)            NOT NULL,
        ORD_ADDR_DETAIL     VARCHAR2(50)            NOT NULL,
        ORD_TEL             VARCHAR2(20)            NOT NULL,
        ORD_PRICE           NUMBER                  NOT NULL, -- ���ֹ��ݾ�. ����
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

--6. �ֹ��� ���̺� : �ֹ���ǰ�� ���� ����
CREATE TABLE ORDETAIL_TBL(
        ORD_CODE        NUMBER      NOT NULL,
        PRO_NUM         NUMBER      NOT NULL,
        DT_AMOUNT       NUMBER      NOT NULL,
        DT_PRICE        NUMBER      NOT NULL  -- ������ ����
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

--7.���� ���̺�
CREATE TABLE REVIEW_TBL(
        RE_CODE        NUMBER,
        MBSP_ID        VARCHAR2(15)               NOT NULL,
        PRO_NUM        NUMBER                     NOT NULL,
        RE_TITLE       VARCHAR2(50)               NOT NULL,
        RE_CONTENT     VARCHAR2(200)              NOT NULL,
        RE_RATE        NUMBER                     NOT NULL,
        RE_REGDATE     DATE DEFAULT SYSDATE
);

-- ������ ����
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

--8.�Խ��� ���̺�
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
--9.������(ADMIN)���̺�
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

-- 10. �������� ���̺�
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

-- 11. Q&A ���̺�
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

-- ��ġ�� ���̺�



COMMIT;