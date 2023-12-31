# 1. 도메인 모델 시작하기

도메인 : 구현해야할 소프트웨어의 대상 (해결해야할 대상)

용어
1. 사용자 인터페이스 or 표현 (presentation) : 사용자 요청 처리및 정보제공
2. 응용 (application) : 사용자가 요청한 기능 실행, 도메인 계층 조합
3. 도메인 : 시스템이 제공할 규칙 구현
4. 인프라스트럭처 (infrastructure) : 데이터베이스나 메시징 시스템과 같은 외부 연동 처리 

도메인 계층 : 도메인의 핵심 규칙 구현

개념모델 : 순수하게 문제를 분석한 결과물

엔티티 : 식별자를 갖음 (엔티티마다 고유 식별자 존재) 
    - 식별자 생성법
        1. 특정 규칙
        2. UUID, 고유식벌자 생성기 사용
        3. 값을 직접 입력
        4. 일련번호 사용(db autoincrement)

밸류 : 개념적으로 완전한 하나를 표현할 때 사용
    - 밸류데이터 변경시 -> 새로운 객체를 생성
    - 불변타입 : 데이터 변경 기능을 제공하지 않음 
    - 두 밸류 객체를 비교할 시 모든 속성이 같은지 비교한다

도메인 모델에 set 넣지 않기
    - 이름있는 메서드로 치환하자 == 도메인 친화적인 네임을 사용하자 
    - set을 하게되는 순간 처음 order 객체를 생성할 떄처럼 완전하지 않다.
    - 불변타입으로 구현하면 set 메서드는 사용하지 않게된다

도메인 용어와 유비쿼터스 언어
    - 도메인 용어를 사용해서 구현하자