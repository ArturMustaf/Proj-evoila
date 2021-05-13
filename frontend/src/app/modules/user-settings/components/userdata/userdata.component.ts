import {Component, OnInit} from '@angular/core';
import {AuthServiceService} from '../../../../core/services/auth-service/auth-service.service';
import {UserinfoService} from '../../../../core/services/userinfo/userinfo.service';


@Component({
  selector: 'app-userdata',
  templateUrl: './userdata.component.html',
  styleUrls: ['./userdata.component.scss']
})
/**
 * Information in the user account settings
 */
export class UserdataComponent implements OnInit {
  name = '';
  lastName = '';
  address = '';
  email = '';
  city = '';
  code = '';
  isValid = true;
  isLoading = true;

  firstNameValue: string;
  lastNameValue: string;
  addressValue: string;
  emailValue: string;
  cityValue: string;
  codeValue: string;

  // added to keep user from accidentally editing data
  public changesDisabled = true;

  constructor(public userinfoService: UserinfoService, private keycloakService: AuthServiceService) {
    this.loadData();
  }

  /**
   * Currently the country is only mocked. And is not saved / used.
   */
  async loadData(): Promise<void> {
    const profile = await this.userinfoService.getUserData();
    const keycloakProfile = await this.keycloakService.getProfile();
    console.dir(profile);
    this.firstNameValue = profile.name;
    this.lastNameValue = profile.surname;
    this.emailValue = profile.contactInformation.email;
    this.isValid = keycloakProfile.emailVerified;
    this.addressValue = profile.contactInformation.address.address;
    this.cityValue = profile.contactInformation.address.city;
    this.codeValue = profile.contactInformation.address.plz;
    this.isLoading = false;
  }

  ngOnInit(): void {
  }

  // to allow changes of contact data
  enableChanges(): void {
    this.changesDisabled = false;
  }

  async send(): Promise<void> {
    this.isLoading = true;

    await this.userinfoService.postData({
      contactInformation: {address: {plz: this.codeValue, address: this.addressValue, city: this.cityValue}, email: this.emailValue},
      surname: this.lastNameValue,
      name: this.firstNameValue,
      userId: ''
    });
    this.loadData();
    this.isLoading = false;
    // to set every input back to disabled
    this.changesDisabled = true;
  }

}
