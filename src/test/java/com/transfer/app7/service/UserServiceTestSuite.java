package com.transfer.app7.service;

import com.transfer.app7.domain.User;
import com.transfer.app7.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTestSuite {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Test
    public  void testGetAllUsers() {
        //Given
        User user1 = new User(1L, "sebek", "sebek", "919191919", new ArrayList<>());
        User user2 = new User(2L, "krzys", "krzys", "919191919", new ArrayList<>());
        List<User> list = new ArrayList<>();
        list.add(user1);
        list.add(user2);

        when(userRepository.findAll()).thenReturn(list);
        //When
        List<User> listGet = userService.getAllUsers();
        //Then
        assertEquals(2, listGet.size());
    }

    @Test
    public void testSave() {
        //Given
        User user1 = new User(1L, "sebek", "sebek", "919191919", new ArrayList<>());

        when(userRepository.save(any())).thenReturn(user1);
        //When
        User userSave = userService.save(user1);
        //Then
        assertEquals("sebek", userSave.getEmail());

    }

    @Test
    public void testGetUser() {
        //Given
        User user1 = new User(1L, "sebek", "sebek", "919191919", new ArrayList<>());

        when(userRepository.findById(1L)).thenReturn(java.util.Optional.of(user1));
        //When
        Optional<User> userGet = userService.getUser(1L);
        //Then
        assertEquals("919191919", userGet.get().getPesel());
    }

    @Test
    public void testDeleteUser() {
        //Given
        doNothing().when(userRepository).deleteById(1L);
        //When
        userService.deleteUser(1L);
        //Then
        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testCountTransactions() {
        //Given
        when(userRepository.count()).thenReturn(10L);
        //When
        long amount = userService.countUsers();
        //Then
        assertEquals(10L, amount);
    }
}
