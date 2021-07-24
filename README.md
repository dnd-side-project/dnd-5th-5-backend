# [ALA] dnd-5th-5-backend

## "ALA" 프로젝트 소개
```내가 모르던 나를 알아가는, 나를 표현하는 한 줄 SNS```

## 아키텍처
<p align="center">
	<img src="https://user-images.githubusercontent.com/46064193/125324764-2bc8e200-e37b-11eb-8d07-9ac29d0d1b1a.png" alt="architecture"/>
</p>
<details>
<summary>[More] AWS DevOps Version</summary>
<div markdown="1">
    * 비용문제로 AWS -> GCP로 변경
    <p>
    <p align="center">
        <img src="https://user-images.githubusercontent.com/46064193/125180311-7d665500-e233-11eb-98c2-fb8ef78b8356.png" alt="architecture"/>
    </p>
</div>
</details>

## 기술스택
|기술|버전|
|:---:|:---:|
|Google kubernetes Engine||
|Google Cloud Registry||
|Google Cloud Load Balancer||
|Docker||
|SpringBoot|2.5.2|
|Spring Rest Docs||
|Gradle|7.1.1|
|JPA||
|Junit5||
|MongoDB||



## Git 브랜치 전략
<details>
<summary>자세히 보기</summary>
<div markdown="1">
    <a href="https://techblog.woowahan.com/2553/">참고) 우아한 형제들 기술 블로그 - 우린 Git-flow를 사용하고 있어요</a>
    <p>
    <ul>
        <li><code>main</code> : 배포시 사용하는 브랜치</li>
        <li><code>develop</code> : 다음 출시 버전을 개발하는 브랜치<ul>
                <li>다음 릴리즈를 위해 언제든 배포될 수 있는 상태</li>
                <li>하나의 기능 구현이 끝나면, develop 브랜치로 병합할 것</li>
            </ul>
        </li>
        <li><code>feature</code> : 기능을 개발하는 브랜치<ul>
                <li>기능을 완성할 때 까지 유지하며, 완성시 <code>develop</code>브랜치로 merge</li>
                <li><code>feature</code>는 이슈번호를 기준으로 생성</li>
                <li>Ex) <code>feature-2/example</code></li>
            </ul>
        </li>
        <li><code>release</code> : 릴리즈를 준비하는 브랜치(QA)</li>
        <li><code>hotfix</code> : 배포 버전에서 생긴 문제로 긴급한 트러블 슈팅이 필요할 때 개발이 진행되는 브랜치</li>
    </ul>
    <p align="center">
        <img src="https://user-images.githubusercontent.com/46064193/124911385-a74b2c00-e027-11eb-982d-a96e6c40d5b3.png" alt="Branch Strategy" width="500">
    </p>
</div>

</details>

## Commit Message 컨벤션
<details>
<summary>자세히 보기</summary>
<div markdown="1">
    <ul>
        <li><code>Add</code> : 클래스, 설정파일 등의 새로운 파일 추가</li>
        <li><code>Feat</code> : 새로운 기능 추가</li>
        <li><code>Docs</code> : 문서 수정</li>
        <li><code>Test</code> : 테스트 코드 작성</li>
        <li><code>Chore</code> : 기타 변경 사항(빌드 스크립트 수정 등)</li>
        <li><code>Fix</code> : 올바르지 않은 코드를 고친 경우</li>
        <li><code>Update</code> : 수정, 추가, 보완(주로 코드가 아닌 버전 업데이트)</li>
        <li><code>Refactor</code> : 코드의 리팩토링</li>
        <li><code>Remove</code> : 코드의 삭제</li>
    </ul>
    ex) Feat: jwt 토큰 발행 기능
</div>
</details>

## 코딩 컨벤션
<details>
<summary>자세히 보기</summary>
<div markdown="1">

### General, Clean Code
1. 메소드의 네이밍은 의도를 분명히 알 수 있도록 작성해야 한다.
2. 변수를 정의할 때에는 의미 있는 이름으로 정의해야 한다.
3. 변수, 메소드는 CamelCase로 작성한다.
4. 클래스의 첫 글자는 영어 대문자로 작성한다.
5. 패키지명은 영어 소문자로 작성한다.
6. 메소드는 동사가 앞에 와서 어떠한 행동을 하는지 명시한다.
7. 컬렉션(List)는 복수형(members) 또는 컬렉션을 명시(memberList)해준다.
8. 최대한 문자열의 하드코딩은 피해야 한다.(static String들을 관리하는 클래스을 사용)
9. 불필요한 주석을 지양하고, 주석 대신 코드로 의도를 표현한다.
10. 한 메소드의 길이가 너무 길어진다면, 내포된 여러 의도들을 각각의 메소드로 분리한다.

### Structure, Testing
11. Controller에서는 최대한 어떤 Service를 호출할지 결정하는 역할과 Exception 처리만을 담당한다.(비즈니스 로직이 포함되지 않도록 한다.)
12. 하나의 메소드와 클래스는 하나의 목적을 두도록 한다.
13. 메소드와 클래스는 최대한 작게 만든다.
14. 서비스는 인터페이스로 작성하고 서비스를 구현하는 Impl 클래스를 정의하여 약한 결합력을 유지한다.
15. Rest API의 반환형은 커스텀하게 정의한 ResponseDto<T>를 사용한다.
16. 데이터를 받고 보내는 객체는 무조건 엔티티가 아닌 Dto 혹은 일반 변수여야 한다.
17. 엔티티와 Dto를 매핑할 때에는 Mapstruct의 Mapper를 이용한다.
18. 컨트롤러에서 dto를 통한 validation을 하도록 한다.
19. Controller와 Mapper는 반드시 개발 전에 테스트 코드를 작성해야 하며, Service 또한 테스트 코드를 작성하는 것을 권장한다.
20. F.I.R.S.T. 규칙을 따르는 테스트코드를 작성한다.

출처: https://jobc.tistory.com/212, Clean Code(책)
</div>
</details>

