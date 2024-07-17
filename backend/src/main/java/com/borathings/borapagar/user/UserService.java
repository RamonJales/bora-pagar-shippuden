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
        String oidcUserGoogleId = oidcUser.getSubject();
        Optional<UserEntity> user = userRepository.findByGoogleId(oidcUserGoogleId);
        user.ifPresentOrElse(
                existingUser -> this.updateExistingOidcUser(existingUser, oidcUser),
                () -> insertNewOidcUser(oidcUser));
    }

    private void insertNewOidcUser(OidcUser oidcUser) {
        String oidcUserEmail = oidcUser.getEmail();
        String oidcUserGoogleId = oidcUser.getSubject();

        logger.info(
                "Novo usuário logado, criando conta para o email {} com googleId {}",
                oidcUserEmail,
                oidcUserGoogleId);
        UserEntity userEntity =
                new UserEntity(
                        oidcUserEmail,
                        oidcUser.getFullName(),
                        oidcUserGoogleId,
                        oidcUser.getPicture());
        userRepository.save(userEntity);
    }

    private void updateExistingOidcUser(UserEntity existingUser, OidcUser oidcUser) {
        logger.info(
                "Usuário com googleId {} de email {} já existe no sistema, atualizando dados",
                existingUser.getId(),
                existingUser.getEmail());

        existingUser.setImageUrl(oidcUser.getPicture());
        userRepository.save(existingUser);
    }
}
