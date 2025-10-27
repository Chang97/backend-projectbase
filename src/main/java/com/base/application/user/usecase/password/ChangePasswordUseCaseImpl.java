package com.base.application.user.usecase.password;

import java.time.OffsetDateTime;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.base.application.user.port.in.ChangePasswordUseCase;
import com.base.application.user.port.out.UserPersistencePort;
import com.base.application.user.usecase.command.ChangePasswordCommand;
import com.base.domain.user.User;
import com.base.exception.NotFoundException;
import com.base.exception.ValidationException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
class ChangePasswordUseCaseImpl implements ChangePasswordUseCase {

    private final UserPersistencePort userPersistencePort;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void handle(ChangePasswordCommand command) {
        if (command == null || !StringUtils.hasText(command.newPassword())) {
            throw new ValidationException("새 비밀번호를 입력해주세요.");
        }

        User user = userPersistencePort.findById(command.userId())
                .orElseThrow(() -> new NotFoundException("User not found"));

        String storedPassword = user.getUserPassword();
        if (StringUtils.hasText(storedPassword)) {
            // 기존 코드에서 현재 비밀번호 검증은 주석 처리되어 있었으므로 동일한 동작을 유지한다.
            // 필요 시 아래 블록을 활성화해 현재 비밀번호와 중복 여부를 검사할 수 있다.
            /*
            if (!StringUtils.hasText(command.currentPassword())
                    || !passwordEncoder.matches(command.currentPassword(), storedPassword)) {
                throw new ValidationException("현재 비밀번호가 일치하지 않습니다.");
            }
            if (passwordEncoder.matches(command.newPassword(), storedPassword)) {
                throw new ValidationException("새 비밀번호가 이전 비밀번호와 동일합니다.");
            }
            */
        }

        user.setOld1UserPassword(storedPassword);
        user.setUserPassword(passwordEncoder.encode(command.newPassword()));
        user.setUserPasswordUpdateDt(OffsetDateTime.now());
        user.setUserPasswordFailCnt(0);

        userPersistencePort.save(user);
    }
}
