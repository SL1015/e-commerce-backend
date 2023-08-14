package com.petcove.userservice.service.impl;

import com.petcove.userservice.dto.UserCreateRequest;
import com.petcove.userservice.dto.UserDto;
import com.petcove.userservice.model.adapter.UserAdapter;
import com.petcove.userservice.repository.UserRepository;
import com.petcove.userservice.service.UserService;
import com.petcove.userservice.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.*;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Repository
@RequiredArgsConstructor
@Slf4j
@Transactional
@EnableCaching
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Cacheable(value = "allUsers")
    public List<UserDto> getAllUsers(){
        doSleepTest();// test if redis caching is functioning
        return userRepository.findAll().stream()
                .map(UserAdapter::EntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    @CacheEvict(value = "allUsers", allEntries = true)
    public UserDto createUser(UserCreateRequest userCreateRequest) {
        log.info("Creating user: {}", userCreateRequest);
        log.debug("Cache evicted for all customers");
        return UserAdapter.EntityToDto(userRepository.save(UserAdapter.RequestToEntity(userCreateRequest)));
    }

    @Override
    @Cacheable(value = "users", key = "#id", unless = "#result == null")
    public UserDto getUserInfo(Long id) {
        doSleepTest();
        log.debug("Getting user info with id: {}", id);
        return userRepository.findById(id).map(UserAdapter::EntityToDto).orElseThrow(UserNotFoundException::new);
    }

    @Override
    @CachePut(value = "users", key = "#id")
    @CacheEvict(value = "allUsers", allEntries = true)
    public UserDto updateUserInfo(Long id, UserCreateRequest  userCreateRequest) {
        log.info("Updating user info with id: {}", id);
        return UserAdapter.EntityToDto(
                userRepository.findById(id)
                        .map(user -> {
                            user.setName(userCreateRequest.getName());
                            user.setPassword(userCreateRequest.getPassword());
                            user.setPhone(userCreateRequest.getPhone());
                            user.setEmail(userCreateRequest.getEmail());
                            user.setAddress(userCreateRequest.getAddresses());
                            return user;
                        })
                        .map(userRepository::save)
                        .orElseThrow(UserNotFoundException::new));
    }

    @Override
    @Caching(
            evict = {
                    @CacheEvict(value = "allUsers", allEntries = true),
                    @CacheEvict(value = "users", key = "#id")
            }
    )
    public void deleteUser (Long id) {
        log.info("Deleting user with id: {}", id);
        userRepository.deleteById(id);
    }

    @Override
    public void doSleepTest() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
