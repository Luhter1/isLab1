import api from './api';

import { AxiosResponse } from 'axios';
import { AuthenticationDto } from '../interfaces/dto/auth/AuthenticationDto';
import { SingInUpDto } from '../interfaces/dto/auth/SingInUpDto';

export default class AuthController {
  /**
   * Login user
   * @param {Credentials} credentials Username & Password
   * @returns {Promise<AxiosResponse<AuthenticationDto>>} User's data and token
   */
  static async login(credentials: SingInUpDto): Promise<AxiosResponse<AuthenticationDto>> {
    return api.post<AuthenticationDto>('/auth/sign-in', credentials);
  }

  /**
   * Create user
   * @param {Credentials} credentials Username & Password
   * @returns {Promise<AxiosResponse<AuthenticationDto>>} User's data and token
   */
  static async register(credentials: SingInUpDto): Promise<AxiosResponse<AuthenticationDto>> {
    return api.post<AuthenticationDto>('/auth/sign-up', credentials);
  }
}