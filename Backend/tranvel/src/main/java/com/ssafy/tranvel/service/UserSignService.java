package com.ssafy.tranvel.service;


import com.ssafy.tranvel.dto.UserSignUpDto;
import com.ssafy.tranvel.entity.User;
import com.ssafy.tranvel.repository.NickNameDao;
import com.ssafy.tranvel.repository.UserRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Service
@Getter @Setter
@RequiredArgsConstructor
public class UserSignService {

    private final UserRepository userRepository;
    private final NickNameDao nickNameDao;

    public void createNickNameInRedis(String nickName, String email) {
        nickNameDao.nickNameStash(nickName, email);
    }


    // 회원 정보에서도 조회하여 중복확인 필요
    // 현재는 Redis 에서 회원가입 시 동시 확인 중복체크만 적용
    // 회원가입 시 Redis 의 nickName key 삭제
    public boolean nickNameDuplicationCheck(String nickName, String email) {
        if (userRepository.findByNickName(nickName) != null) {
            // "이미 존재하는 닉네임 입니다."
            return false;
        }
        if (nickNameDao.hasKey(nickName) &&
                !nickNameDao.getStashedNickName(nickName)
                        .equals(email)) {
            // "이미 존재하는 닉네임 입니다."
            return false;
        }
        // 인증 코드 유효성 통과 시, 해당 인증 코드가 사라지기 때문에 재접근 시 새로운 코드 발급 필요
        createNickNameInRedis(nickName, email);
        // "사용 가능한 닉네임 입니다."
        return true;
    }

//    private boolean isVerify(String nickName, String email) {
//        return !(nickNameDao.hasKey(nickName) &&
//                nickNameDao.getStashedNickName(nickName)
//                        .equals(email));
//    }

    public void saveUserInfo(UserSignUpDto userSignUpDto) {
//        if (nickNameDao.getStashedNickName(nickName).equals(email)) {

        User user = User.builder()
                .email(userSignUpDto.getEmail())
                .nickName(userSignUpDto.getNickName())
                // 암호화 필요
                .password(userSignUpDto.getPassword())
                .profileImage((userSignUpDto.getProfileImage() == null) ? userSignUpDto.getProfileImage() : null)
                .balance(0)
                .build();

        userRepository.save(user);
        }

    }
