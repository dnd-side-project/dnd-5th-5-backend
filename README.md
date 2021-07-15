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

