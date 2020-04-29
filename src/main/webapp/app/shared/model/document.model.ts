import { Moment } from 'moment';

export interface IDocument {
  id?: number;
  documentName?: string;
  fileName?: string;
  startDate?: Moment;
  endDate?: Moment;
  author?: number;
}

export class Document implements IDocument {
  constructor(
    public id?: number,
    public documentName?: string,
    public fileName?: string,
    public startDate?: Moment,
    public endDate?: Moment,
    public author?: number
  ) {}
}
