package com.github.donkeyrit.twinkle.bll.services.interfaces;

import java.util.Optional;

public interface UserInfoService {
	Optional<String> updatePassword(String oldPassword, String newPassword, String repeatPassword);
}
