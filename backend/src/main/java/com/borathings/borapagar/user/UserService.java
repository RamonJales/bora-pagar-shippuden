package com.borathings.borapagar.user;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

/** UserService */
@Service
public class UserService {
    @Autowired UserRepository userRepository;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public void upsertFromOidcUser(OidcUser oidcUser) {
        String oidcUserEmail = oidcUser.getEmail();
        String oidcUserImageUrl = oidcUser.getPicture();
        String oidcUserGoogleId = oidcUser.getSubject();
        Optional<UserEntity> user = userRepository.findByGoogleId(oidcUserEmail);
        if (!user.isPresent()) {
            logger.info(
                    "Novo usuário logado, criando conta para o email {} com googleId {}",
                    oidcUserEmail,
                    oidcUserGoogleId);
            UserEntity userEntity =
                    new UserEntity(
                            oidcUserEmail,
                            oidcUser.getFullName(),
                            oidcUserGoogleId,
                            oidcUserImageUrl);
            userRepository.save(userEntity);
            return;
        }
        logger.info(
                "Usuário com googleId {} de email {} já existe no sistema, atualizando dados",
                oidcUserGoogleId,
                oidcUserEmail);
        UserEntity existingUser = user.get();
        existingUser.setImageUrl(oidcUserImageUrl);
        userRepository.save(existingUser);
    }
}
