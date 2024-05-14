import { Injectable } from "@angular/core";

export interface Menu{
    state:string;
    name:string;
    type:string;
    icon:string;
    role:string;

}

const MENUITEMS = [
    { state: 'dashboard', name: 'Dashboard', type: 'link', icon: 'dashboard', role: '' },
    { state: 'redeem', name: 'Redeem Rewards', type: 'link', icon: 'shopping_cart', role: 'user' },
    { state: 'purchasehistory', name: 'Purchase History', type: 'link', icon: 'history', role: 'user' },
    { state: 'recognitionHistory', name: 'Recognition History', type: 'link', icon: 'emoji_events', role: 'user' },
    { state: 'category', name: 'Manage Category', type: 'link', icon: 'category', role: 'admin' },
    { state: 'product', name: 'Rewards Catalogue', type: 'link', icon: 'inventory_2', role: 'admin' },
    { state: 'user', name: 'Manage Employee', type: 'link', icon: 'people', role: 'admin' },
    { state: 'recognitions', name: 'Assign Recognitions', type: 'link', icon: 'emoji_events', role: 'admin' },
    { state: 'recognitionHandler', name: 'Add Recognitions', type: 'link', icon: 'emoji_events', role: 'admin' },
    { state: 'allocationHistory', name: 'Allocation History', type: 'link', icon: 'history', role: 'admin' }
   
    
    
]

@Injectable({
    providedIn: 'root'
})
export class MenuItems {
    getMenuItems(role: string): Menu[] {
        return MENUITEMS.filter(menuItem => menuItem.role === '' || menuItem.role === role);
    }
}