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
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Service
public class SyncUserService {
    @Autowired
    OrgUserRepository orgUserRepository;
    @Autowired
    ModelMapper modelMapper;
    Keycloak keycloak;
    RealmResource realmResource;

    void initKeycloak() throws IOException {
        var propFileName = "logindata.properties";
        var prop = new Properties();
        var inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);

        if (inputStream != null) {
            prop.load(inputStream);
        } else {
            throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath / resources. Please create the file and set granttype, clientid and clientsecret.");
        }

        keycloak = KeycloakBuilder.builder()
                .serverUrl(prop.getProperty("keycloak.URI"))
                .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
                .realm(prop.getProperty("keycloak.realm"))
                .clientId(prop.getProperty("keycloak.clientID"))
                .clientSecret(prop.getProperty("keycloak.clientSecret"))
                .resteasyClient(
                        new ResteasyClientBuilder()
                                .connectionPoolSize(10).build()
                ).build();
        keycloak.tokenManager().getAccessToken();
        realmResource = keycloak.realm(prop.getProperty("keycloak.realm"));
    }

    void syncAll() {
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

    void syncUserKeycloak(OrgUser user) {
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

    boolean keycloakUserExists(OrgUser user) {
        var list = getAllKeycloak();
        for(UserRepresentation userRepresentation : list){
            if(userRepresentation.getUsername().equals(user.getName()) || userRepresentation.getId().equals(user.getId())){
                return true;
            }
        }
        return false;
    }

    void setOrgUserID(OrgUser user){
        var list = getAllKeycloak();
        for(UserRepresentation userRepresentation : list){
            if(user.getName().equals(userRepresentation.getUsername())){
                OrgUserDTO newUser = modelMapper.map(user, OrgUserDTO.class);
                newUser.setId(userRepresentation.getId());
                if(orgUserRepository.findById(user.getId()) != null){
                    orgUserRepository.deleteById(user.getId());
                }
                orgUserRepository.save(modelMapper.map(newUser, OrgUser.class));
            }
        }
    }
}
