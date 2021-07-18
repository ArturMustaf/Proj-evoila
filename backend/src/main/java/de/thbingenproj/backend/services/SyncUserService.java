package de.thbingenproj.backend.services;

import de.thbingenproj.backend.entity.OrgUser;
import de.thbingenproj.backend.model.OrgUserDTO;
import de.thbingenproj.backend.repository.OrgUserRepository;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@PropertySource("classpath:logindata.properties")
@Service
public class SyncUserService {
    @Autowired
    OrgUserRepository orgUserRepository;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    Environment env;
    Keycloak keycloak;
    RealmResource realmResource;

    @PostConstruct
    public void initKeycloak()  {
        keycloak = KeycloakBuilder.builder()
                .serverUrl(env.getProperty("kc.URI")+"auth")
                .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
                .realm(env.getProperty("kc.realm"))
                .clientId(env.getProperty("kc.clientID"))
                .clientSecret(env.getProperty("kc.clientSecret"))
                .resteasyClient(
                        new ResteasyClientBuilder()
                                .connectionPoolSize(10).build()
                ).build();
        keycloak.tokenManager().getAccessToken();
        realmResource = keycloak.realm(env.getProperty("kc.realm"));
    }

    public void syncAll() {
        var repoList = getAllRepository();
        for(OrgUser orgUser : repoList){
            syncUserKeycloak(orgUser);
        }
    }

    List<UserRepresentation> getAllKeycloak() {
        return realmResource.users().list();
    }

    List<OrgUser> getAllRepository(){
        return orgUserRepository.findAll();
    }

    public void syncUserKeycloak(OrgUser user) {
        var userRepresentation = new UserRepresentation();
        if(keycloakUserExists(user)){
            var list = getAllKeycloak();
            for(UserRepresentation usRep : list){
                if(usRep.getUsername().equals(user.getName()) || usRep.getId().equals(user.getId())){
                    userRepresentation=usRep;
                }
            }
            userRepresentation.setUsername(user.getName());
            userRepresentation.setEmail(user.getEmailAddress());
            realmResource.users().get(userRepresentation.getId()).update(userRepresentation);
        } else {
            userRepresentation.setEmail(user.getEmailAddress());
            userRepresentation.setUsername(user.getName());
            var credentialRepresentation = new CredentialRepresentation();
            credentialRepresentation.setTemporary(true);
            credentialRepresentation.setType("password");
            credentialRepresentation.setValue(user.getName()+"@"+user.getOrgID());
            var credList = new ArrayList<CredentialRepresentation>();
            credList.add(credentialRepresentation);
            userRepresentation.setCredentials(credList);
            realmResource.users().create(userRepresentation);
        }
        setOrgUserID(user);
    }

    public boolean deleteUserKeycloak(OrgUser user){
        boolean deleted = false;
        if(keycloakUserExists(user)){
            var list = getAllKeycloak();
            for(UserRepresentation usRep : list){
                if(usRep.getUsername().equals(user.getName()) || usRep.getId().equals(user.getId())){
                    realmResource.users().delete(usRep.getId());
                    deleted = true;
                }
            }
        }
        return deleted;
    }

    public boolean keycloakUserExists(OrgUser user) {
        var list = getAllKeycloak();
        for(UserRepresentation userRepresentation : list){
            if(userRepresentation.getUsername().equals(user.getName()) || userRepresentation.getId().equals(user.getId())){
                return true;
            }
        }
        return false;
    }

    public void setOrgUserID(OrgUser user){
        var list = getAllKeycloak();
        for(UserRepresentation userRepresentation : list){
            System.out.println(user);
            System.out.println(user.getName());
            System.out.println(userRepresentation);
            System.out.println(userRepresentation.getUsername());
            if(user.getName().equals(userRepresentation.getUsername())){
                OrgUserDTO newUser = modelMapper.map(user, OrgUserDTO.class);
                newUser.setId(userRepresentation.getId());
                if(orgUserRepository.findById(user.getId()).isPresent()){
                    orgUserRepository.deleteById(user.getId());
                }
                orgUserRepository.save(modelMapper.map(newUser, OrgUser.class));
            }
        }
    }
}
