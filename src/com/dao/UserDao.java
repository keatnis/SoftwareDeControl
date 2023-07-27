package com.dao;

import com.controller.UserJpaController;
import com.controller.exceptions.NonexistentEntityException;
import com.model.User;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.JOptionPane;
import raven.toast.Notifications;

/**
 *
 * @author keatnis
 */
public class UserDao {

    private final UserJpaController userController;
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("ControlSystemPU");

    public UserDao() {
        this.userController = new UserJpaController(emf);

    }

    /*
    metodos para el crud
     */
    public void addUser(User usr) {

        userController.create(usr);

    }

    public List<User> getData() {
        return userController.findUserEntities();
    }

    public void update(User user) {
        if (userController.userExists(user.getId())) {
            userController.edit(user);
        } else {
            Notifications.getInstance().show(Notifications.Type.WARNING,
                    "el id del usario no existe en la base de datos");
        }

    }

    public List<User> loginByUser(String user) {
        return userController.findByUsername(user);

    }

    public void delete(Integer id) {
        try {
            if (userController.userExists(id)) {
                userController.destroy(id);
            }
        } catch (NonexistentEntityException ex) {
            JOptionPane.showMessageDialog(null, "El usuario con el ID: " + id + " no existe" + "Mensage: " + ex.getLocalizedMessage());
        }
    }
}
