export interface UserModel {
  name: string;
  surname: string;
  contactInformation: {
    address: {
      address: string;
      plz: string;
      city: string;
      country: {
        countryName: string;
        shortDescription: string;
      };
      addressType: string;
    };
    type: string;
    email: string;
    numbers: Array<{ number: string; type: string }>;
  };
  permissions: Array<{}>;
}
