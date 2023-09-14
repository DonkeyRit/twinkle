package com.github.donkeyrit.twinkle.bll.services.contracts;

import java.util.Optional;

public interface UserInfoService {
	Optional<String> updatePassword(String oldPassword, String newPassword, String repeatPassword);
}
