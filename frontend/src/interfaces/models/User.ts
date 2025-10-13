import { DateTime } from 'luxon';

import { Role } from './Role';

type User = {
  id: number;
  username: string;
  role: Role;
  createdAt: DateTime;
};

export default User;