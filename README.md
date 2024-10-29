# YewMall 쇼핑몰 Project
<p align="center">
  <img src="https://github.com/user-attachments/assets/5cba816e-90c1-4843-9db1-41792a476389"  width="70%"/>
</p>

## 프로젝트 소개
Spring Boot를 활용하여 개발한 쇼핑몰 프로젝트입니다. 다양한 상품을 카테고리별로 조회하고, 장바구니를 통해 주문할 수 있는 기능을 제공하며, 관리자 페이지를 통해 상품 및 주문, 회원 관리 기능을 지원합니다.


## 개발기간
2024.06.17. ~ 2024.08.18. (지속적인 유지보수 예정)


## 개발자
개인 프로젝트 : 🍋유영(백엔드 엔지니어 지망)


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
<img src="https://github.com/user-attachments/assets/b2e9c3b8-948a-4acf-913b-158039c4a6b9" width="20%">
<img src="https://github.com/user-attachments/assets/0172c8c6-f8b6-4d2a-8a58-54563ffdb99a" width="20%">
<img src="https://github.com/user-attachments/assets/d9cdc041-0d11-4eda-b362-07f36e8924ea" width="20%">
<img src="https://github.com/user-attachments/assets/efb0abfc-489b-4c20-81c6-28f03b2d876b" width="20%">


## 주요 기능
### 사용자 측 페이지

#### [초기화면] ####
  * 서비스 접속 초기화면으로 카테고리, 캐러셀, 최신 등록 상품, 리뷰 많은 상품 순으로 화면이 나타납니다.
    * 로그인이 되어 있지 않은 경우 : Sign up, Login, Cart 출력
    * 로그인이 되어 있는 경우 : Logout, MyPage, Cart 출력
    ![main화면-min](https://github.com/user-attachments/assets/482abbe5-56ce-4875-9730-c07965d215d0)

#### [회원가입] ####
  * 아이디, 비밀번호, 이메일 등의 기초정보를 입력하여 회원가입이 가능합니다.
    * 아이디 입력 후, 우측의 'ID Check' 버튼 클릭 시 유효성 검사가 진행되고 팝업 및 경고문구가 우측에 표시됩니다.
      * 아이디가 빈칸인 경우 : '아이디를 입력하세요.' 출력
      * 아이디가 중복되는 경우 : '아이디 사용불가' 출력 후 초기화
      * 아이디를 사용할 수 있는 경우 : '아이디 사용가능' 출력
    * 이메일 주소 입력 후 '인증요청' 버튼 클릭 시 '메일로 인증코드가 발송되었습니다.' 팝업이 출력됩니다.
     작성한 이메일 주소로 이동 시, 서버로부터 발송된 인증코드 6자리를 확인할 수 있습니다.
      * 인증코드 일치 시 : '메일로 인증코드가 발송되었습니다.' 출력
      * 인증코드 미일치 시 : '인증코드 값을 재확인바랍니다.' 출력
      * 인증완료 후 동일한 인증코드로 재인증 시도 시 : '인증코드가 만료되었습니다. 재발급바랍니다.' 출력
      ![회원가입_메일인증](https://github.com/user-attachments/assets/84d93f23-06f6-462f-ad92-5b475d821b83)
    
    * 기본주소는 '우편번호찾기' 버튼 클릭 시 검색하여 사용할 수 있습니다. 해당 기능은 Kakao(Daum) 우편주소 API를 활용하여 개발하였습니다.
      ![회원가입_카카오맵](https://github.com/user-attachments/assets/6b9ef5ce-7d7e-4a26-8a7a-a61849c0a8fc)

#### [로그인/로그아웃] ####
  * 로그인은 일반사용자 로그인과 SNS로그인 두 가지 기능을 지원합니다.
    * 일반 사용자 로그인
      * 미리 회원가입된 ID 및 비밀번호를 입력한 후 'Log In'버튼을 클릭합니다.

    * SNS 로그인
      * 별도의 회원가입 절차 없이 Kakao 및 Naver 로그인을 지원하며 해당 기능은 Kakao Login API 및 Naver Login API를 사용하여 개발하였습니다.
      <p align="center">
        <img src="https://github.com/user-attachments/assets/18d19c94-c4fe-44ad-8094-8d0a9e996bf6" width="10%">
        <img src="https://github.com/user-attachments/assets/b2ae818a-efd6-41c7-b71d-2dca8b2ec5ed" width="5%">
      </p>

#### [아이디/비밀번호 찾기] ####
  * 사용자가 아이디/비밀번호를 잊어버렸을 경우 이메일 인증을 통하여 해당 정보를 찾을 수 있습니다.
    * 아이디 찾기 : 기 등록된 이름과 이메일 입력 후 우측의 '인증번호 발송'을 클릭하면 해당 메일로 인증코드 6자리가 발송됩니다.
     인증코드란에 인증코드 입력 후 'ID 찾기'버튼 클릭 시 임시 저장된 인증코드와의 일치여부를 비교하게 됩니다.
     인증코드가 일치할 경우 사용자의 ID가 이메일로 발송됩니다.
      * 인증코드 미일치 시 : '인증코드를 제대로 입력해주세요.' 출력
      * 인증코드 일치 시 : '아이디가 메일로 발송되었습니다.' 출력
      ![로그인_아이디찾기](https://github.com/user-attachments/assets/c4fb737d-b648-4743-a057-90024ff6f3df)

    * 비밀번호 찾기 : 기 등록된 ID, 이름, 이메일 입력 후 우측의 '인증번호 발송'을 클릭하면 해당 메일로 인증코드 6자리가 발송됩니다.
     인증코드란에 인증코드 입력 후 '비밀번호 재설정' 버튼 클릭 시 
      * 인증코드 미일치 시 : '인증코드를 확인하세요.' 출력
      * 인증코드 일치 시 : '임시 비밀번호가 메일로 발송되었습니다.' 출력 및 무작위 생성된 임시 비밀번호로 DB에 저장
      ![로그인_비번찾기](https://github.com/user-attachments/assets/ab167e09-524a-4b28-9f0f-95af96f6118b)

#### [마이페이지] ####
  * 마이페이지는 '활동관리'와 '정보관리' 페이지로 나뉘며, 각각의 페이지는 아래와 같습니다.
    * 활동관리
      * 주문조회
        * 사용자가 주문한 기록을 조회할 수 있습니다.
        * 주문번호, 주문가격, 주문일자, 주문상세보기가 지원하며 주문상세보기의 '상세보기' 버튼 클릭 시 모달창이 나타나며 사용자의 주문상세를 확인할 수 있습니다.
        ![마이페이지_주문조회](https://github.com/user-attachments/assets/b1f57fd5-254a-476e-9f59-8fb4eeb688f9)

      * 위시리스트
        * 사용자가 설정한 위시리스트 상품들을 조회할 수 있습니다.
        * 상품번호, 상품정보, 상품가격, 관리(위시리스트 삭제)가 지원하며 빨간 하트를 클릭할 시 '위시리스트에서 삭제되었습니다.' 팝업과 함께 위시리스트에서 해당 상품이 삭제됩니다.
        ![마이페이지_위시](https://github.com/user-attachments/assets/80edf32a-8e3b-4161-91e6-1e8a44220cc7)
      
      * 상품리뷰
        * 사용자가 작성한 구매후기를 조회할 수 있습니다.
        * 상품정보, 제목, 내용, 별점, 작성일, 관리(수정, 삭제)를 지원하며 관리의 '수정'버튼 클릭 시 리뷰 내용을 수정할 수 있습니다.
        * 또한, '삭제' 버튼 클릭 시 '선택된 후기를 삭제하시겠습니까?' 팝업을 띄운 후 '확인' 클릭 시 선택한 리뷰를 삭제할 수 있습니다.
        ![마이페이지_구매후기](https://github.com/user-attachments/assets/cdd0a528-503a-43c0-a3a4-1afb6be24889)
      
      * 상품 Q&A
        * 사용자가 작성한 상품에 대한 질의를 확인할 수 있으며 쇼핑몰 관리자의 답변여부를 체크할 수 있습니다.
          * 관리자 답변 전 : 답변여부란에 '답변대기' 표시가 뜨며 '수정', '삭제'가 자유롭습니다.
          * 관리자 답변 후 : 답변여부란에 '답변완료' 표시와 함께 답변내용을 확인할 수 있습니다.
           '수정'버튼 클릭 시 '답변이 달린 Q&A는 삭제할 수 없습니다. 관리자에게 문의바랍니다.' 팝업과 함께 수정기능을 지원하지 않으며 삭제만 가능합니다.
        ![마이페이지_qna](https://github.com/user-attachments/assets/e44a0143-1ae3-4278-b5b3-ab802b62a2ce)

    * 정보관리
      * 회원정보 변경
        * ID, 가입일, 이름을 제외한 회원정보를 자유롭게 변경할 수 있습니다.
        * 이메일 변경 시 회원가입과 동일하게 이메일 인증을 받아야 합니다.
        * 우편번호 및 기본주소는 Kakao(Daum)우편주소 API를 활용하여 정확한 주소를 수집할 수 있도록 하였습니다.
        * 모든 절차가 완료된 후, '수정' 버튼 클릭 시 '회원정보가 수정되었습니다.' 팝업과 함께 수정된 정보를 확인할 수 있습니다.
        ![image](https://github.com/user-attachments/assets/6c04e014-96a0-43b5-a101-3834b7979bcd)

      * 비밀번호 변경
        * 현재 비밀번호, 신규 비밀번호, 신규 비밀번호 확인란을 모두 채운 후 '비밀번호 변경' 버튼 클릭 시 새로운 비밀번호로 변경됩니다.
         각 항목 중 일치하지 않는 항목이 있거나 공란이 있을 시 입력란이 초기화되며 focusing 됩니다.
         ![마이페이지_비밀번호변경](https://github.com/user-attachments/assets/00aaace2-11b3-46d4-874c-7cfc04499407)

      * 회원 탈퇴
        * 현재 비밀번호 입력 후 '계정삭제' 버튼 클릭 시 계정이 삭제됩니다.
        ![image](https://github.com/user-attachments/assets/29e18581-6b78-4bf8-92e4-6855045bd587)



#### [상품목록] ####
  * 모든 상품은 **관리자 페이지**에서 상품등록 당시 설정한 카테고리별로 필터링하여 조회할 수 있습니다.
  ![front_category](https://github.com/user-attachments/assets/6c7a25c7-644c-48f9-bd54-a3a9ff63c6c2)
  * 상품목록 페이지에서 '즉시구매', '장바구니' 기능을 지원합니다.
    * 즉시구매 : 버튼 클릭 시, 수량 체크 후 '즉시구매'버튼을 누를 시 결제화면으로 넘어갑니다.
     최소주문수량 1개 미만일 시('0'개 입력 시) '최소주문수량은 1개입니다.' 팝업이 출력됩니다.
     ![목록_즉시구매](https://github.com/user-attachments/assets/411c5862-6f02-4094-8179-cf9a7fd78cc5)

    * 장바구니 : 버튼 클릭 시, 수량 체크 후 '장바구니 추가'버튼을 누를 시 '상품이 장바구니에 담겼습니다.' 및 '장바구니로 이동하시겠습니까?' 팝업이 출력됩니다.
      * '확인' 클릭 시 : 장바구니 화면으로 이동
      * '취소' 클릭 시 : 본래의 상품목록으로 이동
      ![목록_장바구니](https://github.com/user-attachments/assets/6d9cfbe3-1cc5-4ff2-9999-33a98c64fd0a)

#### [상품상세] ####
  * 상품상세페이지는 위시리스트 추가, 상품구매후기, Q&A 등의 기능을 지원합니다.
  ![상세_전체페이지](https://github.com/user-attachments/assets/e90aac89-f5b7-4a2c-a5f6-b15831ad46ea)
    * 위시리스트
      * 상품이 위시리스트에 있을 경우 : 빨간 하트와 함께 '위시리스트 삭제' 표시
      * 상품이 위시리스트에 없을 경우 : 빈 하트와 함께 '위시리스트 추가' 표시
      ![상세_위시](https://github.com/user-attachments/assets/1949cef4-1044-4bd6-96cb-2566a3865250)

    * 상품구매후기
      * '상품후기 작성' 버튼 클릭 시 작성창이 모달형식으로 화면에 띄워집니다.
      * 제목, 내용작성 및 별 평점 클릭 후 '저장'버튼을 누를 시 '상품후기가 등록되었습니다.' 팝업과 함께 상품상세페이지 상에서 해당 후기를 확인할 수 있습니다.
        * 로그인한 사용자와 후기를 작성한 사용자가 일치하는 경우 : 비고란에 '수정', '삭제'버튼이 활성화
        * 로그인한 사용자와 후기를 작성한 사용자가 일치하지 않는 경우 : '수정', '삭제'버튼 미노출
        ![상세_구매후기](https://github.com/user-attachments/assets/0663a9bc-fe0c-4ade-8685-6b836b0b0ddd)

    * Q&A
      * '상품문의 작성' 버튼 클릭 시 작성창이 모달형식으로 화면에 띄워집니다.
        * 문의내용 작성 후 '저장'버튼을 누를 시 '상품문의가 등록되었습니다.' 팝업과 함께 상품상세페이지 상에서 해당 문의를 확인할 수 있습니다.
          * 로그인한 사용자와 문의를 작성한 사용자가 일치하는 경우 : 비고란에 '수정', '삭제' 버튼이 활성화
          * 로그인한 사용자와 문의를 작성한 사용자가 일치하지 않는 경우 : '수정', '삭제' 버튼 미노출
          ![상세_질의](https://github.com/user-attachments/assets/dfe63d61-8820-4297-a380-c40e4c9290bc)

#### [장바구니] ####
  * 장바구니 페이지는 주문상품수량 변경, 상품삭제, 장바구니 비우기, 주문하기 기능을 지원합니다.
  * 하단의 총액란을 통하여 전체 주문 금액을 확인할 수 있습니다.
  ![image](https://github.com/user-attachments/assets/55c12314-4afa-48a6-9c06-5e62a11b998a)
    * 수량변경 및 삭제
      * 수량 란의 숫자 변경 후 '수량변경'버튼 클릭 시 해당 상품의 주문수량이 변경됩니다.
      * '삭제'버튼 클릭 시 나타나는 '선택상품을 삭제하시겠습니까?' 팝업에 '확인' 클릭 시 선택상품이 수량과 상관없이 삭제됩니다.
      ![카트_수량](https://github.com/user-attachments/assets/e4e4c3f0-9133-4531-949e-8ab2f0b2de07)

    * 장바구니 비우기
      * 최하단의 '장바구니 비우기'버튼 클릭 시 나타나는 '장바구니를 비우시겠습니까?' 팝업에 '확인' 클릭 시 장바구니의 모든 상품이 삭제됩니다.
      ![카트_전체비우기](https://github.com/user-attachments/assets/2ae1d80f-f99a-46f1-8ca5-01a5468236e4)

#### [결제] ####
  * 장바구니 페이지의 '주문하기'버튼 클릭 시 주문페이지로 이동합니다.
  * 주문페이지는 주문상품목록, 주문정보입력으로 구성되어 있습니다.
  ![결제_전체](https://github.com/user-attachments/assets/4ec37e32-ebfa-4b48-9e4e-607ce37268c4)

  * 주문정보 내 '주문자와 동일' 체크박스를 클릭 시 기 저장된 회원정보를 불러옵니다.
  ![결제_체크박스](https://github.com/user-attachments/assets/b0bb7968-5cae-4433-b044-027815d303ae)

  * 무통장 결제 및 KakaoPay 두 가지 방식을 지원합니다.
    * 무통장 결제
      * 무통장 결제 라디오 버튼 클릭 시 하단에 무통장 입금정보가 출력됩니다.
      * 최하단의 '결제하기'버튼 클릭 시 주문이 완료됩니다.
      ![결제_무통장](https://github.com/user-attachments/assets/f4d70556-03f2-46c0-a371-21b28ec2b015)

    * KakaoPay
      * 카카오페이 라디오 버튼 클릭 후 최하단의 '결제하기'버튼 클릭 시 카카오페이 결제로 이동합니다.
      * 화면에 출력되는 QR코드를 핸드폰 카메라로 스캔하여 결제를 진행합니다.
      * 카카오페이는 '단건결제'API로 구현하였습니다.
      ![결제_카페1](https://github.com/user-attachments/assets/64e6deeb-91e9-44db-8da7-4e4ef0e48142)
        <p align="center">
          <img src="https://github.com/user-attachments/assets/49bfc02f-24ee-48c5-9300-86b2579e6955" width="30%">
        </p>






### 관리자 측 페이지
<img src="https://github.com/user-attachments/assets/af9b0ecb-18ee-4d46-bee2-69bcc300bd55"  width="75%"/>
<img src="https://github.com/user-attachments/assets/5f89dc9e-5091-4102-805d-92d3fe97dcc7"  width="15%"/>


#### [상품관리] ####
  * 상품관리 탭은 '상품목록'과 '판매상품 등록'페이지로 구성되어 있습니다.
  * 쇼핑몰에 노출되는 상품을 자유롭게 등록/수정/삭제/검색할 수 있습니다.
    * 상품목록
      * 상품등록
        * '상품등록'버튼 클릭 시 '판매상품 등록'페이지로 이동합니다.
        ![insertProduct](https://github.com/user-attachments/assets/34034c4a-27ca-4eed-85b0-70b27215e658)

      * 상품수정
        * 상품목록 페이지의 '수정'버튼 클릭 시 상품 수정 페이지로 이동합니다.
        * 혹은 목록에서 상품 썸네일 클릭 시 상품 상세조회가 가능하며 해당 페이지에서 '상품수정'버튼 클릭 시 상품 수정 페이지로 이동합니다.
        * 수정이 필요한 사항을 모두 수정한 후 '상품수정'버튼 클릭 시 해당 내용으로 저장되며 상품목록페이지로 이동합니다.
        * 정보 수정 중 기 등록된 정보로 되돌아가고 싶다면 '초기화'버튼 클릭 시 기존 정보로 초기화됩니다.
        ![상품_수정](https://github.com/user-attachments/assets/2848cd51-4d84-4215-b4d3-e4dc648d475c)
      
      * 개별삭제/일괄삭제
        * 개별삭제 : 상품 목록 우측의 '삭제'버튼 클릭 시 해당 상품이 삭제됩니다.
        * 일괄삭제 : 목록 좌측의 체크박스 클릭 후 '일괄삭제'버튼 클릭 시 선택된 상품이 삭제됩니다.
        ![상품_삭제](https://github.com/user-attachments/assets/ca18c25e-30f1-42d0-a1e4-65c404e07895)

      * 상품검색
        * 상품명, 상품코드, 제조사, 상품명 or 제조사 네 가지 형태의 항목에 대하여 검색할 수 있습니다.
        ![filterProduct](https://github.com/user-attachments/assets/e3699f6b-ceb0-406c-bf34-bdd249d9d5cd)

    * 판매상품 등록
      * 상품목록의 '+상품등록'버튼 및 좌측 사이드바의 '판매상품 등록'클릭 시 이용할 수 있습니다.
      * 카테고리 : 1차 카테고리 클릭 시 그에 맞는 2차 카테고리가 출력되어 선택할 수 있습니다.
      * 상품설명 : ckeditor.js를 활용하여 WYSIWYG 편집기를 제공하였습니다.
      * 그 외 상품명, 할인율, 상품가격, 제조사 등의 데이터 입력 및 썸네일 이미지를 등록할 수 있습니다.

#### [주문관리] ####
  * 주문관리는 주문목록과 위시리스트로 나뉩니다.
    * 주문목록
      * 상단의 필터기능을 통하여 주문자, 주문번호, 아이디, 주문기간별로 필터링하여 주문 데이터를 조회할 수 있습니다.
      ![주문_필터링](https://github.com/user-attachments/assets/4f337ab0-680b-428f-b674-fec1482356fa)

      * 우측의 상세보기 클릭 시 고객의 주문에 관한 상세정보(주문상품정보, 결제정보, 주문자(수령인) 정보, 관리자 메모)를 조회할 수 있습니다.
      * 모달창 최하단의 '수정하기' 버튼 클릭 시, 배송지, 배송메모, 관리자메모를 수정할 수 있습니다.
      ![주문_상세수정](https://github.com/user-attachments/assets/77a86052-09a1-4b6f-a5e8-a6eb6c3e0bb4)

      * 전체 상품 건에 대한 주문 삭제는 주문목록에서 지원합니다.
      * 주문 한 건에 대한 개별 상품 삭제는 '상세보기' 버튼 클릭 후 상품 우측의 '삭제' 버튼 클릭 시 삭제할 수 있습니다.
      ![주문_개별삭제](https://github.com/user-attachments/assets/b667587e-fd0d-46ee-a691-833339c7cd93)

    * 위시리스트
      * 고객이 설정한 위시리스트를 조회할 수 있으며 삭제 가능합니다.
      ![image](https://github.com/user-attachments/assets/e4f29284-ea27-4d97-9932-2c259bbf43bc)

#### [회원관리] ####
  * 회원관리는 회원목록, 이메일 발송목록, 이메일 발송 폼으로 나뉩니다.
    * 회원목록
      * 전체 회원리스트를 확인할 수 있으며 회원의 아이디, 이름을 검색하여 특정 회원의 정보도 조회할 수 있습니다.
      ![image](https://github.com/user-attachments/assets/ecd45a93-e1dc-4c7e-8012-386d89776a90)
      
    * 이메일 발송 폼
      * 메일 폼 등록은 이메일 발송목록 우측 상단의 '+메일 폼 등록' 버튼 클릭 및 좌측의 사이드바에서 '이메일 발송 폼' 선택 시 이동가능합니다.
      * 회원을 대상으로 광고/이벤트, 일반 메일을 발송할 수 있습니다.
      * 메일 폼 작성 후 '메일저장' 버튼 클릭 시 메일이 저장되며 저장된 메일을 바로 확인할 수 있습니다.
      ![회원_메일등록](https://github.com/user-attachments/assets/94a14a44-f392-4d84-8537-46b6a2fb7b5f)

    * 이메일 발송목록
      * 회원을 대상으로 발송했거나 발송예정인 메일목록을 조회할 수 있습니다.
      * 제목, 메일 설명의 내용을 검색하여 빠르고 쉽게 관리자가 원하는 메일을 조회하여 발송할 수 있습니다.
      * 작성된 메일 폼은 회원을 대상으로 몇 차례 발송되었는지 조회 가능합니다.
      * 각 메일 폼은 상시 수정 및 삭제 가능합니다.
      ![회원_메일검색](https://github.com/user-attachments/assets/81ad214d-b067-49e5-8400-600b96f1c055)

      * 발송을 희망하는 메일 우측의 '발송' 버튼 클릭 시
        * 메일 발송과 관련된 페이지로 넘어갑니다.
        * 발송하려는 '메일 미리보기', '메일 발송 고객 목록'을 지원합니다
        * 메일 발송고객의 경우, SNS로그인 여부, 광고수신동의 여부 두 가지로 필터링하여 전체 회원목록을 조회할 수 있습니다.
        * 최하단의 '메일발송' 버튼 클릭 시 필터링된 회원을 대상으로 지정한 메일이 발송된 후 이메일 발송목록으로 되돌아옵니다.
        ![회원_메일발송](https://github.com/user-attachments/assets/715aa2f8-f2b2-427a-aa00-c5fb0161bab2)

#### [후기/문의관리] ####
  * 후기/문의관리는 상품구매후기 목록, 상품문의 목록으로 나뉩니다.
    * 상품구매후기 목록
      * 고객이 남긴 상품구매후기를 조회할 수 있습니다.
      * 우측의 '수정' 및 '삭제' 버튼 클릭 시 구매후기를 직접 수정이 가능하여 부적절한 단어가 포함된 후기를 관리할 수 있습니다.
      ![image](https://github.com/user-attachments/assets/755fea2b-690c-4a71-83b6-ba5eca973807)
    
    * 상품문의 목록
      * 고객이 남긴 상품에 대한 Q&A를 조회할 수 있습니다.
        * 관리자 답변 완료 시 : 답변여부란에 '답변완료' 뱃지
        * 관리자 답변 미완료 시 : 답변여부란에 '답변대기' 뱃지
      * 답변하기
        * 우측의 '답변하기' 버튼 클릭 시 답변을 달 수 있습니다.
        * 답변 작성이 종료되었을 시 '답변저장' 버튼을 클릭 시 답변이 바로 저장됩니다.
        * 작성한 답변 수정을 희망할 경우 우측의 '수정' 버튼을 클릭 시 가능합니다.
      * 답변삭제
        * 답변이 달린 Q&A는 수정 및 삭제가 가능합니다.
        * 답변 삭제를 해야 할 경우 우측의 '답변삭제' 버튼 클릭 시 '답변이 삭제되었습니다.' 팝업이 출력된 후 삭제된 화면을 확인할 수 있습니다.
        ![문의_답변및삭제](https://github.com/user-attachments/assets/28472b18-9106-4f02-ae00-c56432a337f4)

#### [통계관리] ####
  * 1차 카테고리 별 매출현황
    * 월별 경쟁사와 YewMall의 1차 카테고리 별 상품 매출현황 통계를 지원합니다.
    * 해당 기능은 Chart.js를 사용하여 개발되었습니다.
    ![통계_1차](https://github.com/user-attachments/assets/f253fb02-eaf9-4e52-af7a-bab4f4bb2111)




### 프로젝트 후기
🍋 YewMall 쇼핑몰 프로젝트를 두 달간 개인으로 진행하며 많은 것을 경험할 수 있었던 시간이었습니다. 팀 프로젝트와는 달리 쇼핑몰의 모든 기능을 처음부터 끝까지 스스로 개발해야 했기 때문에, 백엔드부터 프론트엔드까지 다양한 분야를 직접 다루어야 했습니다. 단순한 클론 코딩이 아니라, 스스로 로직을 설계하고 구현하는 과정에서 실제 개발의 재미와 어려움을 모두 체감할 수 있었습니다.

주차별로 목표를 설정하고 그에 맞는 기능을 기획하여 일정 내에 완료하려고 노력하였습니다. 이러한 방식은 작업의 우선순위를 명확히 하고, 꾸준히 진행 상황을 점검하면서 일정을 지킬 수 있도록 도와주었습니다. 이를 통해 스스로에게 책임감을 부여하며, 계획적으로 프로젝트를 완성하는 경험을 할 수 있었습니다.

앞으로도 개발자로 성장하기 위해 다양한 프로젝트를 수행하며 기술을 깊이 있게 배워나가고 싶습니다.
