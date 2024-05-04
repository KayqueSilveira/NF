package com.alpe.nf.adapter.service;

import com.alpe.nf.adapter.repository.UserRepository;
import com.alpe.nf.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserDetailsServiceImplTest {

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("quando o usuario existir no banco de dados deve encontrar o objeto no banco")
    public void testLoadUserByUsername() {
        User user = userBuilder();
        when(this.userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));
        UserDetails result = userDetailsService.loadUserByUsername(user.getUsername());

        assertNotNull(result);
        assertEquals(user.getUsername(), result.getUsername());
    }

    @Test
    @DisplayName("quando o usuario nao existir no banco de dados deve retornar uma exception")
    public void testLoadUserByUsernameNotFound() {
        User user = userBuilder();
        when(this.userRepository.findByUsername(user.getUsername())).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> userDetailsService.loadUserByUsername(user.getUsername()));
    }

    private User userBuilder() {
        return User.builder()
                .username("testeUser")
                .password("123456")
                .build();
    }
}
