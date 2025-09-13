package com.enesderin.easyInvoice.service.impl;

import com.enesderin.easyInvoice.dto.request.UserRequest;
import com.enesderin.easyInvoice.dto.response.UserResponse;
import com.enesderin.easyInvoice.exception.BaseException;
import com.enesderin.easyInvoice.exception.ErrorMessage;
import com.enesderin.easyInvoice.exception.MessageType;
import com.enesderin.easyInvoice.model.Company;
import com.enesderin.easyInvoice.model.User;
import com.enesderin.easyInvoice.repository.CompanyRepository;
import com.enesderin.easyInvoice.repository.UserRepository;
import com.enesderin.easyInvoice.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private CompanyRepository companyRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public List<UserResponse> getUsers(Long companyId) {
        List<User> optional = this.userRepository.findAllByCompanyId(companyId);

        List<UserResponse> userResponses = new ArrayList<>();
        for (User user : optional) {
            UserResponse userResponse = new UserResponse();
            userResponse.setUsername(user.getUsername());
            userResponse.setPassword(user.getPassword());
            userResponse.setEmail(user.getEmail());
            userResponse.setRole(user.getRole());
            userResponses.add(userResponse);
        }
        return userResponses;

    }

    @Override
    public UserResponse getUser(Long id) {
        Optional<User> optionalUser = this.userRepository.findById(id);
        if (optionalUser.isEmpty()) {
            throw new BaseException(new ErrorMessage(MessageType.USERNAME_NOT_FOUND,optionalUser.get().getUsername()));
        }
        User user = optionalUser.get();
        UserResponse userResponse = new UserResponse();
        userResponse.setUsername(user.getUsername());
        userResponse.setPassword(user.getPassword());
        userResponse.setEmail(user.getEmail());
        userResponse.setRole(user.getRole());
        return userResponse;
    }


    @Override
    public UserResponse createUser(UserRequest userRequest) {
        Optional<Company> optionalCompany = this.companyRepository.findById(userRequest.getCompanyId());
        User user = new User();
        user.setUsername(userRequest.getUsername());
        user.setPassword(bCryptPasswordEncoder.encode(userRequest.getPassword()));
        user.setRole(userRequest.getRole());
        user.setCompany(optionalCompany.get());
        user.setEmail(userRequest.getEmail());
        this.userRepository.save(user);
        UserResponse userResponse = new UserResponse();
        userResponse.setUsername(user.getUsername());
        userResponse.setPassword(user.getPassword());
        userResponse.setEmail(user.getEmail());
        userResponse.setRole(user.getRole());
        return userResponse;
    }



    @Override
    public String deleteUser(Long id) {
        this.userRepository.deleteById(id);
        return "User deleted";
    }
}
