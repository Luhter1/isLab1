import { useUserStore } from '@/stores/userStore'
import { SingInUpDto } from '@/interfaces/dto/auth/SingInUpDto'
import AuthController from '@/controllers/AuthController'
import { ElMessage } from 'element-plus'

const getErrorMessage = error => error?.response?.data?.message || 'ERROR';

export const login = (credentials: SingInUpDto) => {
  return AuthController.login(credentials).then(
    response => {
      const { data } = response;
      const userStore = useUserStore();
      userStore.setAuthentication(data);
      return Promise.resolve();
    },
    error => {
      ElMessage({
          message: getErrorMessage(error),
          showClose: true,
          grouping: true,
          type: 'error',
      });
      return Promise.reject();
    }
  );
};

export const register = (credentials: SingInUpDto) => {
  return AuthController.register(credentials).then(
    response => {
      const { data } = response;
      const userStore = useUserStore();
      userStore.setAuthentication(data);
      return Promise.resolve();
    },
    error => {
      ElMessage({
          message: getErrorMessage(error),
          showClose: true,
          grouping: true,
          type: 'error',
      });
      return Promise.reject();
    }
  );
};

export const logout = () => {
  const userStore = useUserStore()
  userStore.clearAuthentication()
  ElMessage({
    message: 'Successfully logged out!',
    showClose: true,
    type: 'success',
  });
};