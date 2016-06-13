/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socketfinalservidor;

import java.util.List;

/**
 *
 * @author Unisistemas
 */
public class TableQueryBrowser {
    
    private String table;
    private List<String> columns;

    public TableQueryBrowser(String table, List<String> columns) {
        this.table = table;
        this.columns = columns;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public List<String> getColumns() {
        return columns;
    }

    public void setColumns(List<String> columns) {
        this.columns = columns;
    }
    
    
}
