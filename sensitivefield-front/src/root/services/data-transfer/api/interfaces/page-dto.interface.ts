export interface PageDTO<T>{
    data: T,
    meta: {
        page: number,
        per_page: number,
        total_items_count: number,
        pages_count: number,
        order_by: string,
        order_direction: string,
        search: string
    }
}