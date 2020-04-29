import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { User } from 'app/core/user/user.model';

@Component({
  selector: 'jhi-user-mgmt-detail',
  templateUrl: './user-management-detail.component.html'
})
export class UserManagementDetailComponent implements OnInit {
  user: User | null = null;

  constructor(private route: ActivatedRoute) {}

  ngOnInit(): void {
    this.route.data.subscribe(({ user }) => (this.user = user));
  }

  // @ts-ignore
  getAuthorityTest(authority) {
    if (authority === 'ROLE_USER') {
      return 'Пользователь'
    } else if (authority === 'ROLE_ADMIN') {
      return 'Администратор'
    } else if (authority === 'ROLE_MANAGER') {
      return 'Менеджер'
    } else if (authority === 'ROLE_CANDOWNLOAD') {
      return 'Может скачивать'
    } else if (authority === 'ROLE_CANUPLOAD') {
      return 'Может сохранять'
    } else if (authority === 'ROLE_COMPANYMANAGER') {
      return 'Менеджер компании'
    }
  }
}
