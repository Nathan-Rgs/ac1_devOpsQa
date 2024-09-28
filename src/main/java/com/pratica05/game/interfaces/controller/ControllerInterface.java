package com.pratica05.game.interfaces.controller;

import org.springframework.web.bind.annotation.RequestParam;

public interface ControllerInterface {

    public String participateInForum(String name, String comment);
    public String upgradeToPremium(String name);

}
