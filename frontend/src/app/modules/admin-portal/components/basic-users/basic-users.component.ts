import {Component, OnInit} from '@angular/core';
import {UserModel} from '../../../../shared/models/user-models/user-model';
import {UserCommunicatorService} from '../../../../core/services/user_communicator/user-communicator.service';
import {ClrDatagridStateInterface} from '@clr/angular';
import {NavService} from '../../../../core/services/nav/nav.service';

@Component({
  selector: 'app-basic-users',
  templateUrl: './basic-users.component.html',
  styleUrls: ['./basic-users.component.scss']
})
/**
 * deprecated, replaced with dynamic generation
 */

export class BasicUsersComponent implements OnInit {
  users: UserModel[] = [];
  randoms: number[] = [];
  totalItems: number;
  pageSize = 10;
  page = 0;
  loading = true;


  constructor(private communicator: UserCommunicatorService, nav: NavService) {
    this.loadUser();
    for (let i = 0; i < 100; i++) {
      this.randoms.push(this.randomInt());
    }
    nav.isAdmin = true;
  }

  refresh(state: ClrDatagridStateInterface): void {
    this.page = state.page.current;
    this.loadUser();
  }

  async loadUser(): Promise<void> {
    console.log(this.page);
    this.loading = true;
    const response = await this.communicator.loadUsers(this.pageSize, this.page);
    this.users = response._embedded.customers;
    this.totalItems = response.page.totalElements;
    console.log(this.users);
    this.loading = false;
  }

  randomInt(): number {
    return Math.floor(Math.random() * Math.floor(5));
  }

  ngOnInit(): void {
  }

}
