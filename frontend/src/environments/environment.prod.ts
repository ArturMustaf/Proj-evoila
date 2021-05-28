
export const environment = {
  production: true,
  keycloak: {
    // Url of the Identity Provider
    issuer: 'http://hatkeinehaende.de:9080/auth/',

    // Realm
    realm: 'TH-Bingen',

    // The SPA's id.
    // The SPA is registerd with this id at the auth-serverß
    clientId: 'frontendClient',
  },
  backend: {
    address: 'http://hatkeinehaende.de:8889'
  },
  atlassian: {
    jira: {
      customerPortalURL: 'https://thb3t.atlassian.net/servicedesk/customer/portal/1',
    }
  }
};
