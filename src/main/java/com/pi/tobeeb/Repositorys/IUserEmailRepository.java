package com.pi.tobeeb.Repositorys;

import com.pi.tobeeb.Entities.UserMail;

public interface IUserEmailRepository {
    public void sendCodeByMail(UserMail mail);
}
