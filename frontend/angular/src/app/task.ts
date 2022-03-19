import { Category } from "./category";

export interface Task {
    id: number;
    name: string;
    description: string;
    due_date: Date;
    finished: boolean;
    category: Category;
}
