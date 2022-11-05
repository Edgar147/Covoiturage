package com.covoiturage.covoiturage.dao;

import com.covoiturage.covoiturage.entity.User;

public interface UserDAO {

    public void saveUser(User theUser);

    public User findByUserName(String userName);

}
