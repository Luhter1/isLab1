export const sorting = (sortParams) =>
  sortParams.map(el => `&sort=${el}`).join('');

export const createCrudUri = (page, size, sort) =>
  `?page=${page}&size=${size}${sorting(sort)}`;

export const createFilterUri = (filter) =>
  filter.map(el => `&filter=${el}`).join('');