package org.example.service;

import org.example.dao.UserDao;
import org.example.model.User;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;



import static org.mockito.Mockito.verify;


import org.mockito.ArgumentCaptor;

import static org.mockito.Mockito.*;

class UserServiceTest {

    @Test
    void ДолженВызыватьСохранениеПользователяПриРегистрации() throws Exception {
        //  мок для UserDao
        UserDao dao = mock(UserDao.class);

        // сервис с мокированным DAO
        UserService service = new UserService(dao);

        // Регистрация
        service.register("Dima");

        //  аргумент, переданный в метод save
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);

        //  проверка save
        verify(dao).save(userCaptor.capture());

        // Проверка имя
        assertEquals("Dima", userCaptor.getValue().getName());
    }
}

