package com.gribiwe.ua.service;

import com.gribiwe.ua.domain.User;
import com.gribiwe.ua.repository.UserRepository;
import com.gribiwe.ua.service.dto.EmployeeDTO;
import com.gribiwe.ua.service.dto.FullUserDto;
import com.gribiwe.ua.service.dto.UserDTO;
import com.gribiwe.ua.service.mapper.UserMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class FullUserService {

    private final UserService userService;
    private final EmployeeService employeeService;


    public FullUserService(UserService userService, EmployeeService employeeService) {
        this.userService = userService;
        this.employeeService = employeeService;
    }

    public void delete(Long jhiId) {
        this.employeeService.deleteByJhiIId(jhiId);
        this.userService.deleteUser(jhiId);
    }

    @Transactional(readOnly = true)
    public List<FullUserDto> findAll(Pageable pageable) {
        Page<EmployeeDTO> allManagedUsers = employeeService.findAll(pageable);
        List<FullUserDto> result = new ArrayList<>();
        allManagedUsers.stream().forEach(employeeDTO -> {
            result.add(findFullUserDtoByEmployeeDTO(employeeDTO));
        });

        return result;
    }

    public FullUserDto findByLogin(String login) {
        FullUserDto fullUserDto = new FullUserDto();
        Optional<User> userWithAuthoritiesByLogin = userService.getUserWithAuthoritiesByLogin(login);
        if (userWithAuthoritiesByLogin.isPresent()) {
            fullUserDto = findById(userWithAuthoritiesByLogin.get().getId());
        }

        return fullUserDto;
    }

    @Transactional(readOnly = true)
    public FullUserDto findById(Long id) {
        FullUserDto fullUserDto = new FullUserDto();

        EmployeeDTO employeeDTO = employeeService.findByJhiId(id);
        fullUserDto = findFullUserDtoByEmployeeDTO(employeeDTO);

        return fullUserDto;
    }

    @Transactional(readOnly = true)
    public FullUserDto findFullUserDtoByEmployeeDTO(EmployeeDTO employeeDTO) {
        FullUserDto fullUserDto = new FullUserDto();
        fullUserDto.setEmployeeDTO(employeeDTO);

        Optional<User> byId = userService.findById(employeeDTO.getJhiUser());
        if (byId.isPresent()) {
            User user = byId.get();
            fullUserDto.setUserDTO(new UserMapper().userToUserDTO(user));
        }
        return fullUserDto;
    }
}
