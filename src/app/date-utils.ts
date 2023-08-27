// date-utils.ts

import { parseISO } from 'date-fns';

export function parseDate(dateString: string): Date {
  return parseISO(dateString);
}
