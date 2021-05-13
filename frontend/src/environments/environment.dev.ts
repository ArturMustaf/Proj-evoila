
export const environment = {
  production: true,
  keycloak: {
    // Url of the Identity Provider
    issuer: 'https://cloak.projectmbe.de/auth/',

    // Realm
    realm: 'TH-Bingen',

    // The SPA's id.
    // The SPA is registerd with this id at the auth-serverß
    clientId: 'darvinClient',
  },
  backend: {
    address: 'https://backend-dev.projectmbe.de'
  },
  atlassian: {
    jira: {
      customerPortalURL: 'https://thb3t.atlassian.net/servicedesk/customer/portal/1',
    }
  }
};
