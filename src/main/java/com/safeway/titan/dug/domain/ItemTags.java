package com.safeway.titan.dug.domain;

import com.poiji.annotation.ExcelCell;

import lombok.Getter;
import lombok.Setter;

public class ItemTags {
	


    @ExcelCell(0)
    @Setter
    @Getter
    private long itemId;     

    @ExcelCell(1)
    @Setter
    @Getter
    private String sku;
    
    @Setter
    @Getter
    private String brcd;

    @ExcelCell(2)
    @Setter
    @Getter
    private String pos_description;

    @ExcelCell(3)
    @Setter
    @Getter
    private int location;

    @ExcelCell(4)
    @Setter
    @Getter
    private String aisle;

    @ExcelCell(5)
    @Setter
    @Getter
    private String section;
    
    @ExcelCell(6)
    @Setter
    @Getter
    private String shelf;
    
    @ExcelCell(7)
    @Setter
    @Getter
    private String from_qty_1;
    
    @ExcelCell(8)
    @Setter
    @Getter
    private String override_qty_1;
    
    @ExcelCell(9)
    @Setter
    @Getter
    private String override_flag_1;

    @Setter
    @Getter
    private String comments;
    
    @Setter
    @Getter
    private String tagSubLoc;
    
    @Override
    public String toString() {
        return "ItemTags{" +
                "ITEM_ID=" + itemId +
                ", SKU='" + sku + '\'' +
                ", POS_DESCRIPTION='" + pos_description + '\'' +
                ", LOCATION=" + location +
                ", AIS=" + aisle +
                ", SEC='" + section + '\'' +
                ", SHE=" + shelf +
                ", FORM_QTY_1=" + from_qty_1 +
                ", OVERRIDE_QTY_1=" + override_qty_1 +
                ", OVERRIDE_FLAG_1=" + override_flag_1 +
                '}';
    }


}
