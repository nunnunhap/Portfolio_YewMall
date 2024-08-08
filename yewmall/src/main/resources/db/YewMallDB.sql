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
        MBSP_UPDATEDATE     DATE DEFAULT SYSDATE   NOT NULL,
        SNS_LOGIN_TYPE      VARCHAR2(10), -- SNS �α��� Ÿ��
        
        CONSTRAINT PK_MBSP_ID PRIMARY KEY (MBSP_ID)
);
mbsp_id, mbsp_name, mbsp_email, mbsp_password, mbsp_zipcode, mbsp_addr, mbsp_deaddr, mbsp_phone, mbsp_nick, mbsp_receive, mbsp_point, mbsp_lastlogin, mbsp_createdate, mbsp_updatedate, sns_login_type
mbsp_tbl
pk_mbsp_id
-- 1.1. SNS���̺� �߰�
DROP TABLE SNS_USER_TBL;
CREATE TABLE SNS_USER_TBL (
    SNS_TYPE    VARCHAR2(10)    NOT NULL,
    ID          VARCHAR2(100),
    NAME        VARCHAR2(50)    NOT NULL,
    EMAIL       VARCHAR2(100)   NOT NULL,
    
    CONSTRAINT PK_SNS_ID PRIMARY KEY (ID)
);


--2.ī�װ� ���̺�
DROP TABLE CATEGORY_TBL;
CREATE TABLE CATEGORY_TBL(
        CATE_CODE            NUMBER,    -- ī�װ� �ڵ�
        CATE_PRECODE         NUMBER    NULL,           -- ����ī�װ� �ڵ�
        CATE_NAME            VARCHAR2(50)    NOT NULL,
        
        CONSTRAINT PK_CATE_CODE PRIMARY KEY (CATE_CODE)
);


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
        PRO_UPDATEDATE      DATE DEFAULT SYSDATE    NOT NULL,
        revcount NUMBER DEFAULT 0,
        
        CONSTRAINTS PK_PRO_NUM PRIMARY KEY(PRO_NUM)
);

ALTER TABLE product_tbl DROP COLUMN revcount;

CREATE SEQUENCE SEQ_PRO_NUM;

COMMIT;


INSERT INTO PRODUCT_TBL (pro_num, CATE_CODE, PRO_NAME, PRO_PRICE, PRO_DISCOUNT, PRO_PUBLISHER, PRO_CONTENT, PRO_UP_FOLDER, PRO_IMG, PRO_AMOUNT, PRO_BUY)
VALUES (seq_pro_num.nextval, 15, '�̴Ͼ�', 300, 30, '�̴Ͼ��ȹ', '<img alt="" src="/admin/product/display/minion.jpg" style="height:168px; width:300px" />', '2024\07\07', 'b56b1da8-4022-46bf-a60b-b972ed188cc5_minion.jpg', 1, 'N');
    
INSERT INTO PRODUCT_TBL(pro_num, CATE_CODE, PRO_NAME, PRO_PRICE, PRO_DISCOUNT, PRO_PUBLISHER, PRO_CONTENT, PRO_UP_FOLDER, PRO_IMG, PRO_AMOUNT, PRO_BUY)
VALUES (seq_pro_num.nextval, 8, '���ǽ�2', 35123, 38, '�������', '<img alt="" src="/admin/product/display/d751788979b54148834c3882ee0ee6d5_20230919153227.jpg" style="height:700px; width:700px" /><br /><br />
�ȳ�? �̰��� ���ǽ���~! �ݰ���!!!!', '2024\07\11', '1b613fca-7488-4553-a2e5-1eb9ec0f63f2_d751788979b54148834c3882ee0ee6d5_20230919153227.jpg', 30, 'N');

INSERT INTO PRODUCT_TBL(pro_num, CATE_CODE, PRO_NAME, PRO_PRICE, PRO_DISCOUNT, PRO_PUBLISHER, PRO_CONTENT, PRO_UP_FOLDER, PRO_IMG, PRO_AMOUNT, PRO_BUY)
VALUES (seq_pro_num.nextval, 8, '�տ��ǽ�1', 98765, 60, '�������', '<img alt="" src="/admin/product/display/d751788979b54148834c3882ee0ee6d5_20230919153227.jpg" style="height:700px; width:700px" /><br /><br />
�ȳ�? �̰��� ���ǽ���~! �ݰ���!!!!', '2024\07\11', 'b56b1da8-4022-46bf-a60b-b972ed188cc5_minion.jpg', 50, 'Y');

-- ��ǰ���� �̹����� �������� ��� �̹������̺� ���� ����


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
        ORD_DESC            VARCHAR2(300)           NULL, -- �ֹ� �� ��û����
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

--6. �ֹ��� ���̺� : �ֹ���ǰ�� ���� ����
CREATE TABLE ORDETAIL_TBL(
        ORD_CODE        NUMBER      NOT NULL,
        PRO_NUM         NUMBER      NOT NULL,
        DT_AMOUNT       NUMBER      NOT NULL,
        DT_PRICE        NUMBER      NOT NULL,  -- ������ ����
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

--7.���� ���̺�
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


-- ������ ����
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
    '��ǰ�ı�1',
    '�̰��� �ı��! �ı⳻��1',
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
    ADMIN_VISIT_DATE    DATE    DEFAULT SYSDATE NOT NULL,
    
    CONSTRAINTS PK_ADMIN_ID PRIMARY KEY(ADMIN_ID)
);

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

-- 11. Q&A ���̺�(resultmap ���)
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

-- ��ġ�� ���̺�

-- 12. ���� ���̺�
DROP TABLE PAYINFO;
CREATE TABLE PAYINFO (
    P_ID        NUMBER  NOT NULL,
    ORD_CODE    NUMBER  NOT NULL,
    MBSP_ID     VARCHAR2(15)    NOT NULL,
    PAYMETHOD   VARCHAR2(50)    NOT NULL,
    PAYINFO     VARCHAR2(100)   NULL, -- ����/ ���¹�ȣ/ ������
    P_PRICE     NUMBER  NOT NULL,
    P_STATUS    VARCHAR2(10)    NOT NULL, -- �ϳ�/�̳�
    P_DATE      DATE    DEFAULT SYSDATE,
    
    CONSTRAINTS PK_PAYINFO_IDX PRIMARY KEY (P_ID)
);

CREATE SEQUENCE seq_payinfo_id;

ALTER TABLE PAYINFO ADD CONSTRAINT FK_PAYINFO_ORD_CODE
FOREIGN KEY (ORD_CODE) REFERENCES ORDER_TBL(ORD_CODE);

-- 13. ���Ϲ߼�
DROP TABLE mailmng_tbl;
CREATE TABLE mailmng_tbl (
    m_idx          NUMBER,
    m_title         VARCHAR2(200)      NOT NULL,
    m_explan     VARCHAR2(300),
    m_content    VARCHAR2(4000)      NOT NULL,
    m_gubun     VARCHAR2(30)      NOT NULL, -- ����/�̺�Ʈ OR �Ϲ�
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





-- �ε��� ��Ʈ ��ġ�� �ٲ�� ���ı����� �ٲ�µ�, �̹� ���� ���̺� �������� 1: �� ����� �ڹٲ��� �� �۵����� ����.
-- 1) �̰ɷ� �������. ���۽����� �� �̰� ���� �� �ð� �ʰ� ª��.
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
-- �� �ֹ��ݾ� ������ join ���� ���� �Ʒ� ���
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