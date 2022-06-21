package com.daniel.sipos.demoproject;

import static com.daniel.sipos.demoproject.BusinessTestConstants.FULL_NAMES;
import static com.daniel.sipos.demoproject.BusinessTestConstants.NEW_EMAIL_ADDRESS;
import static com.daniel.sipos.demoproject.BusinessTestConstants.NEW_NAME;
import static com.daniel.sipos.demoproject.BusinessTestConstants.SIPOS_HERR_DANI_GMAIL_COM;
import static org.assertj.core.api.Assertions.assertThat;

import com.daniel.sipos.demoproject.domain.UserDomain;
import com.daniel.sipos.demoproject.service.UserService;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = {SpringTestConfiguration.class})
@ActiveProfiles("test")
public class UserServiceTest {
  @Autowired
  UserService userService;

  @Test
  public void saveUserIfNotExist() {
    int size = userService.findAllUsers().size();
    UserDomain createdUser = getUser();
    userService.saveUserIfNotExist(createdUser);
    int newSize = userService.findAllUsers().size();
    assertThat(newSize).isEqualTo(size + 1);
  }

  @Test
  public void saveUserIfExist() {
    int size = userService.findAllUsers().size();
    UserDomain createdUser = getAlreadyPersistedUser();
    userService.saveUserIfNotExist(createdUser);
    int newSize = userService.findAllUsers().size();
    assertThat(size).isEqualTo(newSize);
  }

  @Test
  public void getUserByEmail() {
    UserDomain user = userService.findUserByEmail(SIPOS_HERR_DANI_GMAIL_COM);
    assertThat(user.getId()).isEqualTo(1);
    assertThat(user.getFullName()).isEqualTo("Sipos Daniel");
  }

  @Test
  public void getUsers() {
    List<UserDomain> users = userService.findAllUsers();
    List<String> fullNames =
        users.stream().map(UserDomain::getFullName).collect(Collectors.toList());
    assertThat(fullNames).contains(FULL_NAMES);
  }

  private UserDomain getUser() {
    return UserDomain.builder()
        .emailAddress(NEW_EMAIL_ADDRESS)
        .fullName(NEW_NAME)
        .build();
  }

  private UserDomain getAlreadyPersistedUser() {
    return UserDomain.builder()
        .id(1L)
        .emailAddress(SIPOS_HERR_DANI_GMAIL_COM)
        .fullName("Sipos Daniel")
        .build();
  }
}