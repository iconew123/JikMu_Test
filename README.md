# 💡서비스/과제 프로젝트 소개

> JAVA 벡앤드 직무 수행 과제 프로젝트 입니다.
> Spring Boot 기반 JWT 인증/인가 및 AWS 배포

# 배포 주소

- AWS EC2 : http://13.125.114.182
- 스웨거 UI : http://13.125.114.182/swagger-ui/index.html#

# 요구 사항

1. 기능개발

- 회원가입 : 엔드포인트 -> POST /signup
  
회원가입 성공응답
![회원가입 요청 성공응답](https://github.com/user-attachments/assets/6fa4f298-63ba-4f5d-8f51-9ecc02a87d13)

회원가입 중복 실패 응답
![회원가입 요청 실패응답](https://github.com/user-attachments/assets/c3ef0bdf-3e58-4009-8db3-8b29441813f3)

- 로그인 : 엔드포인트 -> POST /login

로그인 성공응답
![로그인 요청 성공응답](https://github.com/user-attachments/assets/f37d871c-664a-4438-bfed-7ef683df5ca5)  

로그인 실패응답
![로그인 요청 실패응답](https://github.com/user-attachments/assets/ac4ed6fd-6132-4d68-b105-d2979abcc14f)
  
- 관리자 권한 부여 : 엔드포인트 -> PATCH /admin/users/{userId}/roles (가입했던 유저의 이름을 패스에 넣으면 됩니다.)
- 관리자 권한을 가진 유저는 스프링 부트가 시작할때, 자동으로 만들어집니다. (아이디 : TestAdmin1234 , 패스워드 : TestAdmin1234)

관리자 권한 부여 성공응답
![관리자 권한 부여 성공응답](https://github.com/user-attachments/assets/5871d17e-5812-4397-9687-b52dc0df23d8)  

관리자 권한 부족 실패응답 (일반 유저가 해당 엔드포인트로 접근했을때 상황)
![관리자 권한 부족 응답](https://github.com/user-attachments/assets/b565bb79-53f8-48be-868c-b453cea56d66)  

관리자 권한을 줄 유저를 찾지 못했을때 실패응답
![유저를 찾지 못했을 떄 실패응답](https://github.com/user-attachments/assets/7b9b0546-231a-42ce-a589-70f25691669d)

2. 테스트 코드 작성 Junit
- 회원가입
    - 정상적인 사용자 정보와 이미 가입된 사용자 정보에 대해 테스트합니다.
- 로그인
    - 올바른 자격 증명과 잘못된 자격 증명을 테스트하여 성공/실패 시의 응답 구조가 예상과 동일한지 확인합니다.
- 관리자 권한 부여
    - 관리자 권한을 가진 사용자가 요청할 때 정상 처리되는지 테스트합니다.
    - 일반 사용자가 요청할 때 권한 오류가 발생하는지 테스트합니다.
    - 존재하지 않는 사용자에게 권한을 부여하려 할 때 적절한 오류 응답이 반환되는지 테스트합니다.
 
테스트 코드 결과
![image](https://github.com/user-attachments/assets/6b533c4d-a302-4c1b-b998-80dec8848084)  

3. API 명세서 - Swagger 로 문서화

어드민 권한 부여시 Authorize 에 토큰 넣고 실행
![image](https://github.com/user-attachments/assets/9f60c0f6-0d6e-4c87-872a-de6793fabdde)

4. 배포 - AWS EC2로 배포 완료
