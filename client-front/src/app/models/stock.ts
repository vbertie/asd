import {MarketStockItem} from './marketStockItem';

export class Stock {
  public id:string;
  public stockName:string;
  public stockItems:MarketStockItem[];
  public date:string;
}
