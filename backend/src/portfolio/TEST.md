# Stub을 이용한 단위 테스트 적용기

태그: PORTFOLIO
날짜: 2024년 3월 14일
참여자: 진주원, 김승진

## 문제상황

---

> ‘TWTW’의 테스트는 크게 Controller, Service, Repository Layer에서 진행되었다. 우리의 목표는 단위 테스트 적용이었다. 하지만 Service 테스트 코드 내에서 Repository를 통한 실제 DB 접근이 이루어져 완벽한 단위 테스트를 수행할 수 없었다.
>

## 접근 방식

---

- **Repository Test**
    - DB를 통한 접근이 수행되는가에 초점을 맞추어 테스트 코드 작성
- **Service Test**
    - Stub을 활용하여 DB 접근을 하지 않고 서비스 로직에만 초점을 맞추어 테스트 코드 작성
- **Controller Test**
    - mock을 활용하여 Service 로직을 타지 않고 테스트 수행
    - 테스트를 수행하면서 자동으로 rest docs 생성

### 테스트용 Repository 분리

---

**Repository의 추상화**

```java
@Repository
public interface MemberRepository {
    List<Member> findAllByNickname(final String nickname);

    List<Member> findAllByNicknameContainingIgnoreCase(final String nickname);

    Optional<Member> findByOAuthIdAndAuthType(final String oAuthId, final AuthType authType);

    boolean existsByNickname(final String nickname);

    Member save(final Member member);

    Optional<Member> findById(final UUID id);

    List<Member> findAllByIds(final List<UUID> friendMemberIds);

    void deleteById(final UUID memberId);
}

```

**실제 서비스용 JpaRepository**

```java
@Repository
public interface JpaMemberRepository extends JpaRepository<Member, UUID>, MemberRepository {

    @Query(
            value =
                    "SELECT * FROM member m WHERE MATCH (m.nickname) AGAINST(:nickname IN BOOLEAN"
                        + " MODE)",
            nativeQuery = true)
    List<Member> findAllByNickname(@Param("nickname") String nickname);

    @Query(
            "SELECT m FROM Member m WHERE m.oauthInfo.clientId = :oAuthId AND"
                    + " m.oauthInfo.authType = :authType")
    Optional<Member> findByOAuthIdAndAuthType(
            @Param("oAuthId") String oAuthId, @Param("authType") AuthType authType);

    @Query("SELECT m FROM Member m WHERE m.id in :friendMemberIds")
    List<Member> findAllByIds(@Param("friendMemberIds") final List<UUID> friendMemberIds);
}

```

**테스트용 StubRepository**

```java
public class StubMemberRepository implements MemberRepository {

    private final Map<UUID, Member> map = new HashMap<>();

    @Override
    public List<Member> findAllByNickname(final String nickname) {
        return map.values().stream()
                .filter(
                        member ->
                                member.getNickname().toUpperCase().contains(nickname.toUpperCase()))
                .toList();
    }

    @Override
    public List<Member> findAllByNicknameContainingIgnoreCase(final String nickname) {
        return map.values().stream()
                .filter(
                        member ->
                                member.getNickname().toUpperCase().contains(nickname.toUpperCase()))
                .toList();
    }

    @Override
    public Optional<Member> findByOAuthIdAndAuthType(
            final String oAuthId, final AuthType authType) {
        return map.values().stream()
                .filter(
                        member -> {
                            final OAuth2Info oauthInfo = member.getOauthInfo();
                            return oauthInfo.getClientId().equals(oAuthId)
                                    && oauthInfo.getAuthType().equals(authType);
                        })
                .findFirst();
    }

    @Override
    public boolean existsByNickname(final String nickname) {
        return map.values().stream().anyMatch(member -> member.getNickname().equals(nickname));
    }

    @Override
    public Member save(final Member member) {
        map.put(member.getId(), member);
        return member;
    }

    @Override
    public Optional<Member> findById(final UUID id) {
        return Optional.ofNullable(map.get(id));
    }

    @Override
    public List<Member> findAllByIds(final List<UUID> friendMemberIds) {
        return map.values().stream()
                .filter(member -> friendMemberIds.contains(member.getId()))
                .toList();
    }

    @Override
    public void deleteById(final UUID memberId) {
        map.remove(memberId);
    }
}
```

> 각 기능별 StubRepository를 만든 후 StubConfig를 통해 테스트 시 빈으로 주입되도록 설정
>

```java
@TestConfiguration
public class StubConfig {

    private final Map<UUID, Friend> map = new HashMap<>();

    @Bean
    @Primary
    public FriendQueryRepository stubFriendQueryRepository() {
        return new StubFriendQueryRepository(map);
    }

    @Bean
    @Primary
    public FriendCommandRepository stubFriendCommandRepository() {
        return new StubFriendCommandRepository(map);
    }

    @Bean
    @Primary
    public RefreshTokenRepository refreshTokenRepository() {
        return new StubRefreshTokenRepository();
    }

    @Bean
    @Primary
    public GroupRepository groupRepository() {
        return new StubGroupRepository();
    }

    @Bean
    @Primary
    public MemberRepository memberRepository() {
        return new StubMemberRepository();
    }

    @Bean
    @Primary
    public PlanRepository planRepository() {
        return new StubPlanRepository();
    }
}
```

### Repository Test

---

> Repository 테스트 시 실제 DB와의 상호작용을 테스트하도록 코드 작성
>

```java
@DisplayName("MemberRepository의")
class MemberRepositoryTest extends RepositoryTest {

    @Autowired private MemberRepository memberRepository;

    @Test
    @DisplayName("PK를 통한 저장/조회가 성공하는가?")
    void saveAndFindId() {
        // given
        final Member member = memberRepository.save(MemberEntityFixture.FIRST_MEMBER.toEntity());

        // when
        final UUID expected = member.getId();
        final Member result = memberRepository.findById(expected).orElseThrow();

        // then
        assertThat(result.getId()).isEqualTo(member.getId());
    }

    @Test
    @DisplayName("soft delete가 수행되는가?")
    void softDelete() {
        // given
        final Member member = MemberEntityFixture.FIRST_MEMBER.toEntity();
        final UUID memberId = memberRepository.save(member).getId();

        // when
        memberRepository.deleteById(memberId);

        // then
        assertThat(memberRepository.findById(memberId)).isEmpty();
    }

    @Test
    @DisplayName("DeviceToken이 정상적으로 저장되는가")
    void saveDeivceToken() {
        // given
        final Member member = memberRepository.save(MemberEntityFixture.LOGIN_MEMBER.toEntity());
        final DeviceTokenRequest deviceTokenRequest = new DeviceTokenRequest("THIS_IS_TEST_TOKEN");

        // when
        DeviceToken deviceToken = new DeviceToken(deviceTokenRequest.getDeviceToken());
        member.updateDeviceToken(deviceToken);

        // then
        assertThat(member.getDeviceToken().getDeviceToken().equals("THIS_IS_TEST_TOKEN"));
    }
}

```

### Service Test

---

> 서비스 로직 테스트를 위해 StubRepository를 이용하여 테스트 작성
>
- MemberServiceTest의 경우 주입받는 memberRepository는 StubRepository

```java
@DisplayName("MemberService의")
class MemberServiceTest extends LoginTest {
    @Autowired private MemberService memberService;
    @Autowired private MemberRepository memberRepository;

    @Test
    @DisplayName("닉네임 중복 체크가 제대로 동작하는가")
    void checkNickname() {
        // given
        final Member member = memberRepository.save(MemberEntityFixture.FIRST_MEMBER.toEntity());
        // when
        DuplicateNicknameResponse response = memberService.duplicateNickname(member.getNickname());
        // then
        assertTrue(response.getIsPresent());
    }

    @Test
    @DisplayName("UUID를 통해 Member 조회가 되는가")
    void getMemberById() {
        // given
        final Member member = memberRepository.save(MemberEntityFixture.FIRST_MEMBER.toEntity());

        // when
        Member response = memberService.getMemberById(member.getId());

        // then
        assertThat(response.getId()).isEqualTo(member.getId());
    }

    @Test
    @DisplayName("Member가 MemberResponse로 변환이 되는가")
    void getResponseByMember() {
        // given
        final Member member = memberRepository.save(MemberEntityFixture.FIRST_MEMBER.toEntity());

        // when
        MemberResponse memberResponse = memberService.getResponseByMember(member);

        // then
        assertThat(memberResponse.getMemberId()).isEqualTo(member.getId());
    }

    @Test
    @DisplayName("Nickname을 통한 Member 검색이 수행되는가")
    void searchMemberByNickname() {
        // given
        final Member member = memberRepository.save(MemberEntityFixture.FIRST_MEMBER.toEntity());

        // when
        final List<MemberResponse> responses =
                memberService.getMemberByNickname(member.getNickname().substring(0, 1));

        // then
        assertThat(responses).isNotEmpty();
    }
}
```

### Controller Test

---

> 컨트롤러 Layer에서의 Request & Response 테스트를 위해 Service를 mock으로 만들어 테스트 작성
>

```
    @Test
    @DisplayName("닉네임이 중복되었는가")
    void duplicate() throws Exception {
        final DuplicateNicknameResponse expected = new DuplicateNicknameResponse(false);
        given(memberService.duplicateNickname(any())).willReturn(expected);

        final ResultActions perform =
                mockMvc.perform(
                        get("/member/duplicate/{name}", "JinJooOne")
                                .contentType(MediaType.APPLICATION_JSON));

        // then
        perform.andExpect(status().isOk()).andExpect(jsonPath("$.isPresent").exists());
        // docs

        perform.andDo(print())
                .andDo(
                        document(
                                "get duplicate nickname",
                                getDocumentRequest(),
                                getDocumentResponse()));
    }
```

### 분석

---

- **Stub을 사용하여 유연한 처리**
    - Repository Layer가 **JPA에 종속적이지 않고** 테스트에 용이한 **유연한 구조** 가져감
    - Controller 테스트의 경우 하나의 메서드만 mocking하면 되었지만, Service 테스트에서는 많은 의존성 때문에 모두 mock으로 처리하기에 부담, 같은 메서드도 매번 mock 처리하기에도 어려움

- **TestContainer 도입**
    - MySQL에서 제공하는 기능을 기존에 사용하던 테스트용 H2 DB에서 지원하지 않음(FULL TEXT INDEX)
    - Redis, RabbitMQ와 같은 외부 시스템과 연동되는 부분을 원활히 테스트

### 테스트 커버리지

---

- Jacoco 도입으로 테스트시와 PR시 커버리지 확인 가능
