import { Customer } from './customer.model';

export enum AccountStatus {
  CREATED = 'CREATED',
  ACTIVATED = 'ACTIVATED',
  SUSPENDED = 'SUSPENDED'
}

export enum OperationType {
  DEBIT = 'DEBIT',
  CREDIT = 'CREDIT'
}

export interface AccountOperation {
  id: number;
  operationDate: Date;
  amount: number;
  type: OperationType;
  description: string;
  operatedBy: string;
}

export interface AccountHistory {
  accountId: string;
  balance: number;
  currentPage: number;
  totalPages: number;
  pageSize: number;
  accountOperationDTOS: AccountOperation[];
}

// Interface de base pour tous les comptes
export interface BankAccount {
  type: string;
  id: string;
  balance: number;
  createdAt: Date;
  status: AccountStatus;
  customerDTO: Customer;
}

export interface CurrentAccount extends BankAccount {
  overDraft: number;
}

export interface SavingAccount extends BankAccount {
  interestRate: number;
}
