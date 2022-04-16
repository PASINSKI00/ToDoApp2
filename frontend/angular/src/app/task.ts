import { Category } from "./category";

export interface Task {
    id: number;
    name: string;
    description: string;
    dueDate: Date;
    finished: boolean;
    categoryId: number;
}
