package org.example.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.entities.UserInfo;
import org.example.eventProducer.UserInfoProducer;
import org.example.model.UserInfoDto;
import org.example.model.UserInfoEvent;
import org.example.repository.RefreshTokenRepository;
import org.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Objects;
import java.util.UUID;

@Component
@AllArgsConstructor
@Data
public class UserDetailsServiceImpl implements UserDetailsService {

//    @Autowired
//    RefreshTokenRepository refreshTokenRepository;
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private final UserInfoProducer userInfoProducer;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserInfo user = userRepository.findByUsername(username);

        if(user == null){
            throw  new UsernameNotFoundException("Could not find user....!");
        }

        return new CustomerUserDetails(user);
    }

    public UserInfo checkIfUserAlreadyExists(UserInfo user){
        return userRepository.findByUsername(user.getUsername());
    }

    public Boolean signUpUser(UserInfoDto userInfoDto){
        userInfoDto.setPassword(passwordEncoder.encode(userInfoDto.getPassword()));
        if(Objects.nonNull(checkIfUserAlreadyExists(userInfoDto))){
            return false;
        }

        String userId = UUID.randomUUID().toString();
        userInfoDto.setUserId(userId);
        UserInfo userInfo = new  UserInfo(userId,userInfoDto.getUsername(),
                userInfoDto.getPassword(), new HashSet<>());
        userRepository.save(userInfo);

        System.out.println("User saved successfully .... " + userInfo);
        System.out.println("User saved successfully .... " + userInfo
                + "-------------------> " + userInfo.getUserId());

        userInfoProducer.senEventToKafka(userInfoToBePublished(userInfoDto));
        return true;
    }

    private UserInfoEvent userInfoToBePublished(UserInfoDto userInfoDto){
        return UserInfoEvent.builder()
                .userId(userInfoDto.getUserId())
                .firstName(userInfoDto.getFirstName())
                .lastName(userInfoDto.getLastName())
                .email(userInfoDto.getEmail())
                .phoneNumber(userInfoDto.getPhoneNumber())
                .build();

    }




}

