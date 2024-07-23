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

    /**
     * upsert vem de update or insert, este método verifica se um OidcUser possui registro no banco
     * de dados, caso tenha, ele chama o método <code>updateExistingOidcUser</code>, senão, chama o
     * método <code>insertNewOidcUser</code>
     */
    public void upsertFromOidcUser(OidcUser oidcUser) {
        String oidcUserGoogleId = oidcUser.getSubject();
        Optional<UserEntity> user = userRepository.findByGoogleId(oidcUserGoogleId);
        user.ifPresentOrElse(
                existingUser -> this.updateExistingOidcUser(existingUser, oidcUser),
                () -> insertNewOidcUser(oidcUser));
    }

    /**
     * Método invocado quando o OidcUser não possui registro equivalente no Banco de Dados, neste
     * caso é criado um registro novo no banco de dados utilizando as informações do perfil do
     * google
     */
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

    /** Atualiza as informações que podem ter mudado do OidcUser */
    private void updateExistingOidcUser(UserEntity existingUser, OidcUser oidcUser) {
        logger.info(
                "Usuário com googleId {} de email {} já existe no sistema, atualizando dados",
                existingUser.getId(),
                existingUser.getEmail());

        existingUser.setImageUrl(oidcUser.getPicture());
        userRepository.save(existingUser);
    }
}
