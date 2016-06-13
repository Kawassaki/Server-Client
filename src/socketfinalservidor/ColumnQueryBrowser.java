/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socketfinalservidor;


public class ColumnQueryBrowser {

    private String header;
    private String property;

    public ColumnQueryBrowser(String header, String property) {
        this.header = header;
        this.property = property;
    }

    public String getHeader() {
        return header;
    }

    public String getProperty() {
        return property;
    }

}
