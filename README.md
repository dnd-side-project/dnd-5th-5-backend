# [ALA] dnd-5th-5-backend

## "ALA" 프로젝트 소개
```내가 모르던 나를 알아가는, 나를 표현하는 한 줄 SNS```

## 아키텍처
<p align="center">
	<img src="https://user-images.githubusercontent.com/46064193/125152173-e42d3500-e185-11eb-9161-4c8dc69f53ac.png" alt="architecture"/>
</p>

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
            </ul>
        </li>
        <li><code>feature</code> : 기능을 개발하는 브랜치<ul>
                <li>기능을 완성할 때 까지 유지하며, 완성시 <code>develop</code>브랜치로 merge</li>
                <li><code>feature</code>는 이슈번호를 기준으로 생성</li>
            </ul>
        </li>
        <li><code>release</code> : 릴리즈를 준비하는 브랜치(QA)</li>
        <li><code>hotfix</code> : 배포 버전에서 생긴 문제로 긴급한 트러블 슈팅이 필요할 때 개발이 진행되는 브랜치</li>
    </ul>
    <div style="text-align: center;">
        <img src="https://user-images.githubusercontent.com/46064193/124911385-a74b2c00-e027-11eb-982d-a96e6c40d5b3.png" alt="Branch Strategy" width="500">
    </div>
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
    </ul>
    ex) Feat: jwt 토큰 발행 기능
</div>
</details>