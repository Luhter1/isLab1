export const displayValue = (value: any): string => {
  return value !== null && value !== undefined ? String(value) : 'null'
}