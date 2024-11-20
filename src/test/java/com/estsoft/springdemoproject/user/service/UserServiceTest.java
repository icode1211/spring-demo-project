package com.estsoft.springdemoproject.user.service;

import com.estsoft.springdemoproject.user.domain.Users;
import com.estsoft.springdemoproject.user.domain.dto.AddUserRequest;
import com.estsoft.springdemoproject.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    UserService userService;

    @Mock
    UserRepository repository;

    @Spy
    BCryptPasswordEncoder encoder;

    @Test
    public void testSave() {
        // given
        String email = "mock_email";
        BCryptPasswordEncoder pwEncoder = new BCryptPasswordEncoder();
        String password = pwEncoder.encode("mock_password");
        AddUserRequest user = new AddUserRequest(email, password);

        // userRepository.save -> stub
        Mockito.doReturn(new Users(email, password))
                .when(repository).save(any(Users.class));

        // when : 회원가입 기능 - 사용자 정보 저장
        Users returnSaved = userService.save(user);

        // then :
        assertThat(returnSaved.getEmail(), is(email));

        verify(repository, times(1)).save(any());
        verify(encoder, times(1)).encode(any());
    }

}