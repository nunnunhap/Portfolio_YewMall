# YewMall 쇼핑몰 Project


## 프로젝트 소개
Spring Boot를 활용하여 개발한 쇼핑몰 프로젝트입니다. 다양한 상품을 카테고리별로 조회하고, 장바구니를 통해 주문할 수 있는 기능을 제공하며, 관리자 페이지를 통해 상품 및 주문, 회원 관리 기능을 지원합니다.


## 개발기간
2024.06.17. ~ 2024.08.18. (지속적인 유지보수 예정)


## 개발자
개인 프로젝트 : 유영(백엔드 엔지니어 지망)


## 개발환경
### IDE
- Spring Tool Suite 4(STS 4)

### Front-End
- HTML5, CSS3, JavaScript, jQuery
- BootStrap 4.6
- Thymeleaf
- Handlebars

### Back-End
- SpringBoot 3.2.6(JDK 17)
- Lombok 1.18.32
- Oracle Database XE 11g
- Mybatis 1.2.5


## 프로젝트 구조
![프로젝트 구조](https://github.com/user-attachments/assets/41614024-e9d7-458a-9ffb-3e0fe2e3a191)


## 주요 기능
### 사용자 측
- 회원가입/로그인/로그아웃
- 마이페이지 : 아이디/비밀번호 찾기, 계정삭제, 비밀번호 변경, 주문조회, 나의 상품구매후기/Q&A 등
- SNS 로그인 : Kakao Login, Naver Login API 연동
- 상품목록 : 카테고리별 상품 조회, 바로구매, 위시리스트 설정 등
- 장바구니 : 상품추가, 삭제, 수량 수정, 장바구니 비우기
- 결제 : KakaoPay 및 무통장 결제

### 관리자 측
- 상품관리 : 상품목록, 판매상품 등록
- 주문관리 : 주문목록, 위시리스트
- 회원관리 : 회원목록, 이메일 발송목록, 이메일 발송 폼
- 후기/문의관리 : 상품구매후기 목록, 상품문의 목록
- 통계관리 : 1차 카테고리 별 매출현황
