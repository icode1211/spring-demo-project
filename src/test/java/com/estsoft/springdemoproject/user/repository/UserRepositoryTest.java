package com.estsoft.springdemoproject.user.repository;

import com.estsoft.springdemoproject.user.domain.Users;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

// 실제 데이터 저장해서 검증
@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    // 사용자 이메일로 조회 기능 검증 테스트
    @Test
    public void testFindByEmail() {
        // given : (when절 에서 조회하려는) 사용자 정보 저장
        Users user = getUser();
        userRepository.save(user);

        // when
        Users returnUser = userRepository.findByEmail(user.getEmail()).orElseThrow();

        // then
        assertThat(returnUser.getEmail(), is(user.getEmail()));
        assertThat(returnUser.getPassword(), is(user.getPassword()));
    }

    // 사용자 정보 저장 기능
    @Test
    public void testSave() {
        // given
        Users user = getUser();

        // when
        Users saved = userRepository.save(user);

        // then
        assertThat(saved.getEmail(), is(user.getEmail()));
    }

    // 사용자 전체 조회 기능
    @Test
    public void testFindAll() {
        // g
        userRepository.save(getUser());
        userRepository.save(getUser());
        userRepository.save(getUser());

        // w : findAll()
        List<Users> list = userRepository.findAll();

        // t : given절에서 저장한 사용자 개수와 when절에서 실제로 호출한 list와 갯수가 같은지
        assertThat(list.size(), is(3));
    }

    private Users getUser() {
        String email = "test@test.com";
        String password = "pw1234567";
        return new Users(email, password);
    }
}